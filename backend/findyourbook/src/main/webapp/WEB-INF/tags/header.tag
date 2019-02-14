<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="nav page-block row">
    <div class="left-nav col-lg-6">
        <div class="logo mr-5">
            <a href="<c:url value="/"/>" class="nostyle">
                <h1>
                    ${locale.get("header.logo")}
                </h1>
            </a>
        </div>
        <div id="book-search" class="d-flex mr-5 search-box">
            <form action="<c:url value="/search/book" />" >
                <input type="search" name="bookName" placeholder="${locale.get("header.search")}">
                <%--${locale.get("header.search.btn")}--%>
                <button class="btn"><i class="fa fa-search" aria-hidden="true"></i></button>
            </form>
        </div>
    </div>
    <div class="right-nav col-lg-6">
        <ul class="mr-5">
            <li><a href="<c:url value="/book/"/>">${locale.get("header.nav.book")}</a></li>
            <li><a href="<c:url value="/author/"/>">${locale.get("header.nav.authors")}</a></li>
            <li><a href="#">${locale.get("header.nav.lists")}</a></li>
            <li><a href="#">${locale.get("header.nav.genres")}</a></li>
        </ul>
        <div class="d-flex" id="nav-btns">
            <a class="m-0 btn" href="<c:url value="/signin"/>">${locale.get("header.btn.signin")}</a>
            <a class="m-0 btn" href="<c:url value="/signup"/>">${locale.get("header.btn.signup")}</a>
        </div>
        <div class="d-flex" id="nav-profile">
            <%-- href="<c:url value="/profile"/>"--%>
            <button class="btn" id="nav-profile-btn"></button>
        </div>
        <script>
            $("#nav-profile-btn").click(function () {
                document.location = "<c:url value="/user/" />" + Cookies.get("id");
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