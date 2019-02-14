<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>
    <div class="book-info row page-block">
        <div class="short-info col-lg-3">
            <img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif" id="book-poster">
            <a class="btn" id="add-to-favorites">Добавить в избранное</a>
            <script>
                $("#add-to-favorites").click(function(){
                    $.ajax({
                        url: '<c:url value="/api/user/" />' + Cookies.get("id") + "/favoriteBook/" + ${bookId},
                        method: 'PUT',
                        success: function (data) {
                            $("#add-to-favorites").text("Удалить из избранного");
                        }
                    })}
                )
            </script>
        </div>
        <div class="main-info col-lg-9">
            <div class="title">
                <h1 class="name" id="book-name">Название книги</h1>
                <span class="year" id="book-year">Год</span>
            </div>
            <table>
                <tbody>
                <tr>
                    <th>Авторы:</th>
                    <td id="book-authors"></td>
                </tr>
                <tr>
                    <th>Жанры:</th>
                    <td id="book-genre"></td>
                </tr>
                <tr>
                    <th>Теги:</th>
                    <td id="book-tag">
                    </td>
                </tr>
                <tr>
                    <th>Главные герои:</th>
                    <td id="book-protagonist"></td>
                </tr>
                <tr>
                    <th>Места событий:</th>
                    <td id="book-place-of-event"></td>
                </tr>
                <tr>
                    <th>Время событий:</th>
                    <td id="book-time-of-event"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="description page-block">
        <h2 class="title">Краткое описание</h2>

        <p id="book-description"></p>
    </div>

    <div class="page-block">
        <h2 class="title">Читать онлайн</h2>
    </div>

    <div class="page-block">
        <h2 class="title">Слушать аудиокнигу</h2>
    </div>

    <div class="comments page-block">
        <h2 class="title">Комментарии</h2>
    </div>


    <script>
        $.ajax( {
            url: '<c:url value="/api/book/"/>' + ${bookId},
            method: 'GET',
            dataType: 'json',
            success: function(data, status) {
                var book = data.value;
                console.log(book);
                $('#book-name').text(book.name);
                $('#book-year').text(book.year);
                $('#book-description').text(book.description);
                $.ajax({
                  url: '<c:url value="/api/poster/" />' + book.posterId,
                  success: function (data) {
                      $('#book-poster').attr('src', data.value.url);
                  }
                });
                book.authors.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/author/" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-authors").append("<span class='badge'><a href=\"<c:url value="/author/"/>" + x + "\">"
                                + data.value.name + "</a></span>");
                        }
                    })
                });
                book.tags.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/tag/" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-tag").append("<span class='badge'><a href=\"#\">" + data.value.name + "</a></span>");
                        }
                    })
                });
                book.genres.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/genre/" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-genre").append("<span class='badge'><a href=\"#\">" + data.value.name + "</a></span>");
                        }
                    })
                });
                book.placeOfEvents.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/placeOfEvent/" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-place-of-event").append("<span class='badge'><a href=\"#\">" + data.value.name + "</a></span>");
                        }
                    })
                });
                book.timeOfEvents.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/timeOfEvent/" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-time-of-event").append("<span class='badge'><a href=\"#\">" + data.value.name + "</a></span>");
                        }
                    })
                });
                book.protagonists.forEach(function(x) {
                    $.ajax( {
                        url: '<c:url value="/api/protagonist//" />' + x,
                        method: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#book-protagonist").append("<span class='badge'><a href=\"#\">" + data.value.name + "</a></span>");
                        }
                    })
                });
            },
            error: {
                // TODO
            }
        })
    </script>

</t:mainLayout>
