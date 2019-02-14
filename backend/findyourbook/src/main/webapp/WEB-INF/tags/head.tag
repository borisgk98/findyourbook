<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Find Your Book</title>

    <%-- MD5 hash --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-md5/2.10.0/js/md5.min.js"></script>
    <script src="<c:url value="/js/jquery.min.js?param=1"/>"></script>

    <%--FontAwesome--%>
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    <link href="<c:url value="/styles/bootstrap-grid.css?param=1"/>" rel="stylesheet" type="text/css">
    <style id="page-style"></style>
    <script>
        updatePageColor = async () => {
            var style = await $.ajax("<c:url value="/get-style" /> ");
            $("#page-style").text(style);
            console.log("Page color is updated!");
        };
        updatePageColor();
    </script>
    <script src="<c:url value="/js/err.js?param=1"/>" defer></script>
    <script src="<c:url value="/js/cookie.js?param=1"/>"></script>

    <script>
        GetBookShortInfo = async (id) => {
            var book = (await $.ajax({
                url: '<c:url value="/api/book/"/>' + id,
                method: 'GET',
                dataType: 'json'
            })).value;
            var poster = { id: -1, url: "https://knigopoisk.org/media/books/po/200x298/poster_200x298.png"};
            try {
                poster = (await $.ajax({
                    url: '<c:url value="/api/poster/"/>' + book.posterId,
                    method: 'GET'
                })).value;
            }
            catch (e) { }
            return { book, poster };
        };

        GetBookFullInfo = async (id) => {
            // TODO
        };

        AppendBookShortInfoToBlock = async (elemId, bookId) => {
            $("#" + elemId).append("" +
                "       <div class=\"book-short-info\" id='book-" + bookId + "'>\n" +
                "            <div>\n" +
                "                <img class=\"book-short-info-img\" src=\"https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif\">\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <h3><a class=\"badge\" href>Loading</a></h3>\n" +
                "                <h5></h5>\n" +
                "            </div>\n" +
                "        </div>");
            var { book, poster } = await GetBookShortInfo(bookId);
            var elem = $("#book-" + bookId);
            elem.find("img").attr('src', poster.url);
            elem.find("h3 a").attr('href', "<c:url value="/book/" />" + bookId);
            elem.find("h3 a").text(book.name);
            elem.find("h5").text(book.year);
        };

        AppendAuthorShortInfoToBlock = async (elemId, authorId) => {
            $("#" + elemId).append("" +
                "       <div class=\"book-short-info\" id='book-" + authorId + "'>\n" +
                "            <div>\n" +
                "                <img class=\"book-short-info-img\" src=\"https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif\">\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <h3><a class=\"badge\" href>Loading</a></h3>\n" +
                "            </div>\n" +
                "        </div>");
            var author = await GetAuthorShortInfo(authorId);
            var elem = $("#book-" + authorId);
            elem.find("img").attr('src', author.img_url);
            elem.find("h3 a").attr('href', "<c:url value="/author/" />" + author.id);
            elem.find("h3 a").text(author.name);
        };

        GetAuthorShortInfo = async (authorId) => {
            var author = (await $.ajax( {
                url: '<c:url value="/api/author/"/>' + authorId,
                method: 'GET',
                dataType: 'json'
            })).value;
            return author;
        };

        AsyncEach = async (arr, f) => {
          for (i = 0; i < arr.length; i++) {
              await f(arr[i]);
          }
        };
    </script>

</head>
<body>