<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>

    <div class="book-info row page-block">
        <div class="short-info col-lg-3">
            <img src="http://ashberry-english.ru/wp-content/uploads/2015/10/LOTR-800x1036.jpg" id="author-img">
            <a class="btn" id="add-to-favorites">Добавить в избранное</a>
            <script>
                $("#add-to-favorites").click(function(){
                    $.ajax({
                        url: '<c:url value="/api/user/" />' + Cookies.get("id") + "/favoriteAuthor/" + ${authorId},
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
                <h1 class="name" id="author-name">Автор</h1>
                <span id="author-year-begin">Неизвестно</span>
                <span> &mdash; </span>
                <span id="author-year-end">Настоящие время</span>
            </div>
        </div>
    </div>

    <div class="description page-block">
        <h2 class="title">Биография</h2>

        <p id="author-bigraphy"></p>
    </div>

    <div class="comments page-block" id="author-books">
        <h2 class="title">Книги</h2>
    </div>

    <div class="comments page-block">
        <h2 class="title">Комментарии</h2>
    </div>


    <script>
        f = async () => {
            var author = (await $.ajax( {
                url: '<c:url value="/api/author/"/>' + ${authorId},
                method: 'GET',
                dataType: 'json',
            })).value;
            $('#author-name').text(author.name);
            if (author.year_begin) {
                $('#author-year-begin').text(author.year_begin);
            }
            if (author.year_end) {
                $('#author-year-end').text(author.year_end);
            }
            $('#author-bigraphy').text(author.biography);
            if (author.img_url) {
                $('#author-img').attr('src', author.img_url);
            }
            // TODO сделать лимит на количество книг
            await AsyncEach(author.books, async (id) => AppendBookShortInfoToBlock("author-books", id));
        };
        f();
    </script>

</t:mainLayout>
