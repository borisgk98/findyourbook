<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:mainLayout>
    <div class="page-block">
        <h2 id="user-name" class="title">Username</h2>
        <table>
            <tbody>
            <tr><td></td><td>
                <button class="btn" id="nav-profile-exit">${locale.get("header.nav.exitbtn")}</button>
                <script>
                    $("#nav-profile-exit").click(function () {
                        Cookies.remove("hash");
                        Cookies.remove("login");
                        Cookies.remove("auth");
                        Cookies.remove("id");
                        document.location = "<c:url value="/" />";
                    });
                </script>
            </td></tr>
            <tr>
                <td>
                    <input type="color" id="input-color">
                </td>
                <td>
                    <button class="btn" id="change-color">Сменить цвет темы сайта</button>
                    <script>
                        $("#change-color").click(async function () {
                            var val = $("#input-color").val().substring(1);
                            await $.ajax("<c:url value="/change-style" />" + "?color=" + val);
                            await updatePageColor();
                        });
                    </script>
                </td>
            </tr>
            <tr>
                <form action="#?">
                    <td>
                        <label>Выбрать язык</label>
                        <select name="lang">
                            <option value="ru">Русский</option>
                            <option value="en">Английский</option>
                        </select>
                    </td>
                    <td>
                        <button class="btn">OK</button>
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="comments page-block" id="books">
        <h2 class="title">Любимые книги</h2>
    </div>

    <div class="comments page-block" id="authors">
        <h2 class="title">Любимые авторы</h2>
    </div>

    <script>
        step = 25;
        url = '<c:url value="/api/user/"/>' + ${userId};
        $.ajax( {
            url: url,
            method: 'GET',
            success: async function (data) {
                var user = data.value;
                console.log(user);
                $("#user-name").text(user.login);
                f = async (arr, b, f) => {
                    for (i = 0; i < arr.length; i++) {
                        await f(b, arr[i]);
                    }
                };
                await f(user.favoriteBooks, "books", AppendBookShortInfoToBlock);
                await f(user.favoriteAuthors, "authors", AppendAuthorShortInfoToBlock);
            }
        });
    </script>

</t:mainLayout>
