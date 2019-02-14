const remES = require('./remov_empty_space.js');

module.exports = function (x) {
  if (x == undefined || x == null) {
      return "null";
  }
  return "'" + remES(x.toString()) + "'";
};