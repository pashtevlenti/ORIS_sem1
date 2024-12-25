<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>lab10 - Управление клиентами</title>
    <meta charset="utf-8"/>

    <style type="text/css">
        .header {
            display:flex;
            justify-content:center;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Управление клиентами</h1>
</div>

<h2>Список клиентов</h2>
<table>
    <#list clients as client>
    <tr>
        <td>${client.id}</td>
        <td>${client.name}</td>
    </tr>
    </#list>

</table>

<button id="btn_show_hide" onclick="show_hide_form();">Добавить клиента<span><img src="static/img/arrow-down.svg" height="24px"/></span></button>

<div id="new_client" style="display:none;">
<form method="post" action="/lab10/client/save">
    <div><input name="name" type="text" placeholder="clientname"></div>
    <div><input name="email" type="text" placeholder="email"></div>
    <div><input name="phone" type="text" placeholder="phone"></div>
    <div><input name="address" type="text" placeholder="address"></div>
    <div><input name="passport" type="text" placeholder="passport"></div>

    <div><input type="submit" value="Сохранить"></div>
</form>
</div>

<script>
    let state_form = 0;

    function show_hide_form() {
       let div = document.getElementById("new_client");
       let btn = document.getElementById("btn_show_hide");

       if (state_form == 0) {
            div.style.display='block';
            state_form = 1;
            btn.innerHTML = 'Добавить клиента<span><img src="static/img/arrow-up.svg" height="24px"/></span>';
       } else {
            div.style.display='none';
            state_form = 0;
            btn.innerHTML = 'Добавить клиента<span><img src="static/img/arrow-down.svg" height="24px"/></span>';
       }
    }
</script>
</body>
</html>