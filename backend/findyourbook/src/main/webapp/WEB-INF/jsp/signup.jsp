<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<t:mainLayout>
<div class="page-block">
    <h2>${locale.get("signup.h1")}</h2>
    <form id="signup-form">
        <table>
            <tr>
                <td><label for="signup-login">${locale.get("signin.login")}</label></td>
                <td><input id="signup-login" type="text" name="login" value="${param.login}"></td>
            <tr>
                <td><label for="signup-password">${locale.get("signin.password")}</label></td>
                <td><input id="signup-password" type="password" name="password"></td>
            </tr>
            <tr>
                <td></td>
                <td><input id="signup-button" class="btn-success btn" type="button" value="${locale.get("signup.btn")}"></td>
                <script>
                    btn = $("#signup-button");
                    btn.click(function () {
                        // TODO проверить параметры

                        // получение хеша
                        hash = md5($("#signup-password")[0].value);

                        data = {};
                        data.hashpass = hash;
                        data.login = $("#signup-login")[0].value;
                        data.password = $("#signup-password")[0].value;
                        console.log(data);

                        $.ajax("<c:url value = "/api/auth/" />", {
                            dataType: "json",
                            method: "POST",
                            data: data,
                            success: function (data, status) {
                                document.location = "<c:url value="/" />";
                            },
                            error: function (data, status) {
                                err("${locale.get("signup.mess")}");
                            }
                        })
                    });
                </script>
            </tr>
        </table>
    </form>
</div>
</t:mainLayout>