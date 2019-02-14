err = function (mess) {
    $("#error").remove();
    if (mess == false) {
        return;
    }
    $("nav.nav.page-block").after('\n' +
        '\n' +
        '<div class="page-block error" id="error">\n' +
        mess +
        '</div>');
    console.log(mess);
};