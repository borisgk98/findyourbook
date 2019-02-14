<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<t:mainLayout>
    <div class="page-block">
        <h2>${locale.get("signin.h1")}</h2>
        <form id="reg-form">
            <table>
                <tr>
                    <td><label for="reg-login">${locale.get("signin.login")}</label></td>
                    <td><input id="reg-login" type="text" name="login" value="${param.login}"></td>
                </tr>
                <tr>
                    <td><label for="reg-email">${locale.get("signin.email")}</label></td>
                    <td><input id="reg-email" type="email" name="email" value="${param.email}"></td>
                </tr>
                <tr>
                    <td><label for="reg-password">${locale.get("signin.password")}</label></td>
                    <td><input id="reg-password" type="password" name="password"></td>
                </tr>
                <tr>
                    <td><label for="reg-password-check">${locale.get("signin.passcheck")}</label></td>
                    <td><input id="reg-password-check" type="password" name="password-check"></td>
                </tr>
                <tr>
                    <td><label for="reg-born">${locale.get("signin.date")}</label></td>
                    <td><input id="reg-born" type="date" name="born" value="${paramborn}"></td>
                </tr>
                <tr>
                    <td>
                        <label for="reg-select-country">${locale.get("signin.country")}</label>
                    </td>
                    <td>
                        <select id="reg-select-country" name="country">
                        </select>
                        <script>
                            // получение списка стран
                            cl = $.ajax({
                                url: "https://restcountries.eu/rest/v2/all",
                                dataType: "json",
                                success: function (data) {
                                    s = $("#reg-select-country");
                                    data.forEach(function (x) {
                                        code = x.alpha2Code.toLowerCase();
                                        selected = "";
                                        if (code == "${paramcountry == null ? "ru" : paramcountry}") {
                                            selected = " selected";
                                        }
                                        s.append("<option value=\"" + code + "\""+ selected + ">" + x.name + "</option>");
                                    });
                                }
                            });
                        </script>
                    </td>
                </tr>
                <tr>
                    <td><label for="reg-gender">${locale.get("signin.gender")}</label></td>
                    <td>
                        <select id="reg-gender" name="gender" value="${paramgender}">
                            <option value="male">${locale.get("signin.gender1")}</option>
                            <option value="female">${locale.get("signin.gender2")}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="reg-news">${locale.get("signin.news")}</label></td>
                    <td><input type="checkbox" name="news" id="reg-news" checked></td>
                </tr>
                <tr>
                    <td><label for="reg-rules">${locale.get("signin.rules1")} <a>${locale.get("signin.rules2")}</a></label></td>
                    <td><input type="checkbox" name="rules" id="reg-rules"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input id="reg-button" class="btn-success btn" type="button" value="${locale.get("signin.btn")}"></td>
                    <script>
                        btn = $("#reg-button");
                        btn.click(function () {
                            // TODO проверить параметры
                            if ($("#reg-password")[0].value.length < 6) {
                                err("${locale.get("signin.mess1")}")
                                return;
                            }

                            // получение хеша
                            hashpass = md5($("#reg-password")[0].value);

                            data = {};
                            data.hashpass = hashpass;
                            data.password = $("#reg-password")[0].value;
                            $("#reg-form input:not(:password), select").each(function () {
                                x = this;
                                if (x.type == "checkbox") {
                                    data[x.name] = {
                                        "on": true,
                                        "off": false
                                    }[x.value];
                                }
                                else {
                                    data[x.name] = x.value;
                                }
                            });

                            $.ajax("<c:url value = "/api/user/" />", {
                                dataType: "json",
                                method: "PUT",
                                data: data,
                                success: function (data, status) {
                                    document.location = "<c:url value="/signin/finish" />";
                                },
                                error: function (jqXHR, error) {
                                    err(jqXHR.responseJSON.messange);
                                    console.log(jqXHR);
                                }
                            })
                        });
                    </script>
                </tr>
            </table>
        </form>
    </div>
</t:mainLayout>

