<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<t:mainLayout>
    <div class="page-block">
        <h2>Найти книгу</h2>
        <form action="<c:url value="/search/book" />" >
            <input type="search" name="bookName" placeholder="${locale.get("header.search")}">
            <button class="btn">${locale.get("header.search.btn")}</button>
        </form>
    </div>

    <div class="page-block" id="books">
    </div>

    <script>
        $.ajax( {
            url: '<c:url value="/api/search/book" />' + "?bookName=${bookName}",
            method: 'GET',
            success: function (data) {
                data.value.forEach(function (book) {
                    $.ajax({
                        url: '<c:url value="/api/poster/"/>' + book.posterId,
                        method: 'GET',
                        success: function (data) {
                            poster = data.value;
                            $("#books").append("" +
                                "       <div class=\"book-short-info\">\n" +
                                "            <div>\n" +
                                "                <img class=\"book-short-info-img\" src=\"" + poster.url + "\">\n" +
                                "            </div>\n" +
                                "            <div>\n" +
                                "                <h3><a class=\"badge\" href='<c:url value="/book/" />"
                                + book.id + "'>" + book.name + "</a></h3>\n" +
                                "                <h5>" + book.year + "</h5>\n" +
                                "            </div>\n" +
                                "        </div>");
                        }
                    });
                });
            }
        });
    </script>
</t:mainLayout>
