module.exports = function (str) {
    var i = 0, j = str.length - 1;
    var pred = (x) => { return x != ' ' && x != '\n'; };
    for (; i < str.length; i++) {
        if (pred(str[i])) {
            break;
        }
    }
    for (; ; j--) {
        if (j <= i || pred(str[j])) {
            break;
        }
    }
    return str.substring(i, j + 1);
};