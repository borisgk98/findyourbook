const mongoClient = require("mongodb").MongoClient;
const config = require('./config');

module.exports = {
    write: function(collection_name, obj) {

        mongoClient.connect(config.db.server, { auth: config.db.auth }, function(err, client){

            if(err) {
                throw err;
            }
            try {
                // взаимодействие с базой данных
                const db = client.db("findyourbook");
                var collection;
                collection = db.collection(collection_name);

                collection.findOne({url: obj.url}, function (err, res) {
                    if (err) {
                        // throw err;
                        return;
                    }
                    if (res == null) {
                        collection.insertOne(obj, function (err, res) {
                            if (err) {
                                // throw err;
                                return;
                            }
                            console.log('Object ' + obj.url + ' is written to ' + collection_name);
                        });
                    }
                });
            }
            catch (e) {
                return;
            }
        });

    }
}