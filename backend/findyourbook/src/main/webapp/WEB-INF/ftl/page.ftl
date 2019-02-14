















<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Find Your Book</title>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-md5/2.10.0/js/md5.min.js"></script>
    <script src="/findyourbook-1.0/js/jquery.min.js?param=1"></script>


    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    <link href="/findyourbook-1.0/styles/bootstrap-grid.css?param=1" rel="stylesheet" type="text/css">
    <style id="page-style"></style>
    <script>
        updatePageColor = async () => {
            var style = await $.ajax("/findyourbook-1.0/get-style ");
            $("#page-style").text(style);
            console.log("Page color is updated!");
        };
        updatePageColor();
    </script>
    <script src="/findyourbook-1.0/js/err.js?param=1" defer></script>
    <script src="/findyourbook-1.0/js/cookie.js?param=1"></script>

    <script>
        GetBookShortInfo = async (id) => {
            var book = (await $.ajax({
                url: '/findyourbook-1.0/api/book/' + id,
                method: 'GET',
                dataType: 'json'
            })).value;
            var poster = { id: -1, url: "https://knigopoisk.org/media/books/po/200x298/poster_200x298.png"};
            try {
                poster = (await $.ajax({
                    url: '/findyourbook-1.0/api/poster/' + book.posterId,
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
            elem.find("h3 a").attr('href', "/findyourbook-1.0/book/" + bookId);
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
            elem.find("h3 a").attr('href', "/findyourbook-1.0/author/" + author.id);
            elem.find("h3 a").text(author.name);
        };

        GetAuthorShortInfo = async (authorId) => {
            var author = (await $.ajax( {
                url: '/findyourbook-1.0/api/author/' + authorId,
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






<nav class="nav page-block row">
    <div class="left-nav col-lg-6">
        <div class="logo mr-5">
            <a href="/findyourbook-1.0/" class="nostyle">
                <h1>
                    Find Your Book
                </h1>
            </a>
        </div>
        <div id="book-search" class="d-flex mr-5 search-box">
            <form action="/findyourbook-1.0/search/book" >
                <input type="search" name="bookName" placeholder="Поиск книги/автора">

                <button class="btn"><i class="fa fa-search" aria-hidden="true"></i></button>
            </form>
        </div>
    </div>
    <div class="right-nav col-lg-6">
        <ul class="mr-5">
            <li><a href="/findyourbook-1.0/book/">Книги</a></li>
            <li><a href="/findyourbook-1.0/author/">Авторы</a></li>
            <li><a href="#">Сборники</a></li>
            <li><a href="#">Жанры</a></li>
        </ul>
        <div class="d-flex" id="nav-btns">
            <a class="m-0 btn" href="/findyourbook-1.0/signin">Регистрация</a>
            <a class="m-0 btn" href="/findyourbook-1.0/signup">Вход</a>
        </div>
        <div class="d-flex" id="nav-profile">

            <button class="btn" id="nav-profile-btn"></button>
        </div>
        <script>
            $("#nav-profile-btn").click(function () {
                document.location = "/findyourbook-1.0/user/" + Cookies.get("id");
            });
        </script>
        <script>
            $("#nav-profile-btn").text(Cookies.get("login"));

            if (Cookies.get("login")) {
                $("#nav-profile").addClass("d-flex");
                $("#nav-profile").show();
                $("#nav-btns").removeClass("d-flex");
                $("#nav-btns").hide();
            }
            else {
                $("#nav-btns").addClass("d-flex");
                $("#nav-profile").hide();
                $("#nav-profile").removeClass("d-flex");
                $("#nav-btns").show();
            }
        </script>
    </div>
    <!--<div class="clear"></div>-->
</nav>


<div class="comments page-block" id="books">
    <h2 class="title">Page</h2>
    <div>
    </div>
</div>


<footer class="page-block footer">
    <h3>
        Find Your Book
    </h3>
    <h3>
        <a href="http://borisgk.space">borisgk</a>
    </h3>
</footer>

</body>
</html>
