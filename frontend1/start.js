fs = require("fs");

//build section
fs.copyFileSync("./node_modules/bootstrap/dist/css/bootstrap-grid.css", "./public/stylesheets/bootstrap-grid.css");
fs.copyFileSync("./node_modules/jquery/dist/jquery.min.js", "./public/js/jquery.min.js");

app = require("./app");
app.listen(8000);