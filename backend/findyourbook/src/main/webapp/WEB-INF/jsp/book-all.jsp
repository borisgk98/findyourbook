<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>
<div class="comments page-block" id="books">
    <h2 class="title">Книги. Страница ${page}</h2>
    <div>
        <a class="btn" href="<c:url value="/book/"/>" id="next-page">Следующая страница</a>
        <script>
            if ((${page}) != 1) {
                $("<a class=\"btn\" href=\"<c:url value="/book/"/>\" id=\"pref-page\">Предидущая страница</a>").insertBefore("#next-page");
            }
        </script>
        <script>
            $("#pref-page").attr('href', "<c:url value="/book/"/>" + "?page=" + (${page} - 1));
            $("#next-page").attr('href', "<c:url value="/book/"/>" + "?page=" + (${page} + 1));
        </script>
    </div>
</div>

<script>
    step = 25;
    url = '<c:url value="/api/book/"/>' + "?start=" + (${page} - 1) * step + "&stop=" + ${page} * step;
    console.log(url);
    f = async () => {
        var books = (await $.ajax( {
            url: url,
            method: 'GET'
        })).value;
        for (i = 0; i < books.length; i++) {
            await AppendBookShortInfoToBlock("books", books[i].id);
        }
    };
    f();
</script>

</t:mainLayout>