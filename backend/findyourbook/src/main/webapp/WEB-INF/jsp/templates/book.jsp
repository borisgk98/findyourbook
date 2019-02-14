<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>

    <div class="book-info row page-block">
        <div class="short-info col-lg-3">
            <img src="http://ashberry-english.ru/wp-content/uploads/2015/10/LOTR-800x1036.jpg">
        </div>
        <div class="main-info col-lg-9">
            <div class="title">
                <h1 class="name" id="book-name">Название книги</h1>
                <span class="author" id="book-author">Автор</span>
                <span class="year" id="book-year">Год</span>
            </div>
            <table>
                <tbody>
                <tr>
                    <th>Жанры:</th>
                    <td><span><a href="https://knigopoisk.org/filter/genres/19">Эпическое фэнтези</a></span> <span><a
                            href="https://knigopoisk.org/filter/genres/18">Фэнтези</a></span></td>
                </tr>
                <tr>
                    <th>Теги:</th>
                    <td><span><a href="https://knigopoisk.org/filter/tags/122">Волшебный мир</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/123">Волшебство</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/109">Военная жизнь</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/115">Воин</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/116">Война</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/148">Героизм</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/151">Гномы</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/72">Борьба за справедливость</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/226">Завоевание</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/256">Клятва</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/281">Лук и стрелы</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/294">Маг</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/301">Магия</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/355">Общение с мертвыми</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/429">Призраки</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/432">Проклятие</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/456">Рыцари</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/491">Честь</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/496">Эльфы</a></span> <span><a
                            href="https://knigopoisk.org/filter/tags/121">Волшебники</a></span></td>
                </tr>
                <tr>
                    <th>Характеристики:</th>
                    <td><span><a href="https://knigopoisk.org/filter/features/2">Героическое</a></span> <span><a
                            href="https://knigopoisk.org/filter/features/12">Приключенческое</a></span> <span><a
                            href="https://knigopoisk.org/filter/features/20">Эпическое</a></span></td>
                </tr>
                <tr>
                    <th>Главные герои:</th>
                    <td><span><a href="https://knigopoisk.org/filter/character/1">Мужчина</a></span> <span><a
                            href="https://knigopoisk.org/filter/character/2">Женщина</a></span></td>
                </tr>
                <tr>
                    <th>Места событий:</th>
                    <td><span><a href="https://knigopoisk.org/filter/scene/194">Фэнтезийный мир</a></span></td>
                </tr>
                <tr>
                    <th>Время событий:</th>
                    <td><span><a href="https://knigopoisk.org/filter/event/12">Вневременная эпоха</a></span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="description page-block">
        <h2 class="title">Краткое описание</h2>

        <p id="book-desctiption"></p>
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
                $('#book-name').text(book.name);
                $('#book-year').text(book.year);
                $('#book-description').text(book.description);
            },
            error: {
                // TODO
            }
        })
    </script>

</t:mainLayout>
