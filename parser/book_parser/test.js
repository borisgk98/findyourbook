const mongoClient = require("mongodb").MongoClient;



mongoClient.connect("mongodb://192.168.2.166:27017/", { auth: { user: 'boris', password: 'passb' }}, function(err, client){

    if(err){
        return console.log(err);
    }
    // взаимодействие с базой данных
    const db = client.db("usersdb");
    const collection = db.collection("users");
    let user = {name: "Tom", age: 23};
    collection.dropAllIndexes(function (err, res) {
        if (err) {
            console.log('ERR!!');
        }
        console.log(res);
    });

    client.close();
});