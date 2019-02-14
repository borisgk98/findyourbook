<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>
    <div class="comments page-block" id="authors">
        <h2 class="title">Авторы. Страница ${page}</h2>
        <div>
            <a class="btn" href="<c:url value="/author/"/>" id="next-page">Следующая страница</a>
            <script>
                if ((${page}) != 1) {
                    $("<a class=\"btn\" href=\"<c:url value="/author/"/>\" id=\"pref-page\">Предидущая страница</a>").insertBefore("#next-page");
                }
            </script>
            <script>
                $("#pref-page").attr('href', "<c:url value="/author/"/>" + "?page=" + (${page} - 1));
                $("#next-page").attr('href', "<c:url value="/author/"/>" + "?page=" + (${page} + 1));
            </script>
        </div>
    </div>

    <script>
        step = 25;
        url = '<c:url value="/api/author/"/>' + "?start=" + (${page} - 1) * step + "&stop=" + ${page} * step;
        console.log(url);
        f = async () => {
            var authors = (await $.ajax( {
                url: url,
                method: 'GET'
            })).value;
            for (i = 0; i < authors.length; i++) {
                await AppendAuthorShortInfoToBlock("authors", authors[i].id);
            }
        };
        f();
    </script>

</t:mainLayout>