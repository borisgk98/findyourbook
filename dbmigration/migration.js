const fs = require('fs');
const URL = require('url');
const mongoClient = require("mongodb").MongoClient;
const log4js = require("log4js");
const pgp = require("pg-promise")(/*options*/);

const fv = require('./format_value.js');
const config = require('./config');

const format = require('string-format');
format.extend(String.prototype, {});

var logger = log4js.getLogger();
logger.level = 'debug';


const run = async () => {
    const client = await mongoClient.connect(config.mongo.server, {auth: config.mongo.auth});
    const pgClient = await pgp(config.postgres);

    // взаимодействие с базой данных
    const db = client.db("findyourbook");
    var collection;
    collection = db.collection('books');

    // обработчик автора
    const authorHandler = async (url, bookId) => {

        var authorCollection = db.collection('authors');
        var author = await authorCollection.findOne({url: url});
        var insertAuthor = "INSERT INTO library.author (name, url, year_begin, year_end, biography, img_url) " +
            "VALUES ('{0}', '{1}', {2}, {3}, {4}, {5}) RETURNING *;"
                .format(author.name, author.url, fv(author.years.begin), fv(author.years.end), fv(author.biography), fv(author.img.url));
        var selectAuthor = "SELECT * FROM library.author WHERE url = '{0}'".format(url);
        author = await pgClient.query(selectAuthor);
        if (author.length == 0) {
            author = await pgClient.query(insertAuthor);
        }
        var id = author[0].id;
        var insertSub = "INSERT INTO library.book__author (book, author) VALUES ({0}, {1});".format(bookId, id);
        pgClient.query(insertSub);
    };

    // обработчик книги
    const bookHandler = async (book) => {
        if ((await pgClient
            .query("SELECT * FROM library.book WHERE originalurl='{0}'"
                .format(book.url))).length != 0) {
            logger.info("Book already exist");
            return;
        }

        // poster
        var selectPoster = "SELECT * FROM library.poster WHERE url = '{0}';".format(book.poster.url);
        var insertPoster = "INSERT INTO library.poster (url) VALUES ('{0}') RETURNING *;".format(book.poster.url);
        var poster = await pgClient.query(selectPoster);
        if (poster.length == 0) {
            poster = await pgClient.query(insertPoster)
        }
        poster = poster[0];

        // book
        var insertBook = "INSERT INTO library.book (name, originalurl, year, description, poster) " +
            "VALUES ('{0}', '{1}', {2}, {3}, {4}) RETURNING *;".format(book.name, book.url, fv(book.year), fv(book.description), fv(poster.id));
        var bookId = (await pgClient.query(insertBook))[0].id;

        // обработка авторов книги
        for (i = 0; i < book.authors.length; i++) {
            authorHandler(book.authors[i].url, bookId);
        }

        // обработчик тегов жанров и т.д. (связь многие ко многим)
        f = async (table, value) => {
            var selectQuery = "SELECT * FROM library.{0} WHERE name = '{1}';".format(table, value);
            var insertQuery = "INSERT INTO library.{0} (name) VALUES ('{1}') RETURNING *;".format(table, value);
            var e = await pgClient.query(selectQuery);
            if (e.length == 0) {
                e = await pgClient.query(insertQuery);
            }
            e = e[0];
            var insertQuerySub = "INSERT INTO library.book__{0} (book, {0}) VALUES ({2}, {1}) RETURNING *;".format(table, e.id, bookId)
            await pgClient.query(insertQuerySub);
        };
        ff = async (arr, table) => {
            if (arr == null) {
                return;
            }
            for (i = 0; i < arr.length(); i++) {
                f(table, arr[i]);
            }
            ;
        };
        ff(book.genres, 'genre');
        ff(book.characteristics, 'tag');
        ff(book.protagonists, 'protagonist');
        ff(book.place_of_events, 'place_of_event');
        ff(book.time_of_events, 'time_of_event');
        logger.info("Book (id: {0}) is written".format(book._id));
    };

    var books = await collection.find().limit(10).toArray();

    for (i = 0; i < books.length; i++) {
        var book = books[i];
        try {
            await bookHandler(book);
        }
        catch (e) {
            logger.error("Error while writing a book (id: {0})".format(book._id));
        }
    }

    await client.close();
    await pgp.end();
};

run();