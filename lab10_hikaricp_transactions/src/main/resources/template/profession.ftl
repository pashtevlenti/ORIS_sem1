<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>lab10 - Поиск профессий</title>
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
    <h1>Поиск профессий</h1>
</div>

<h2>Профессии</h2>

<body>
<form id = "btn1" method="GET" onsubmit="saveParameter();">
    <input type="text" name="search" placeholder="Enter search term" aria-label="Search" required>
    <button type="submit">Search</button>
</form>
</body>


<table>
    <#list professions as profession>
        <tr>
            <td>${profession.id}</td>
            <td>${profession.name}</td>
        </tr>
    </#list>
</table>
<body>

<form method = "get">
    <button id = "btn2" onclick = "handleSecondButtonClick();" type="submit" name="previous">previous</button>
    <button id = "btn3" onclick = "handleThirdButtonClick();" type="submit" name="next">next</button>
</form>
</body>

<script>
    function saveParameter() {
        const form = document.getElementById('btn1');
        const formData = new FormData(form);
        localStorage.setItem('search',formData.get('search'));
    }
    function handleSecondButtonClick() {
        const btn2 = document.getElementById('btn2');
        btn2.value = localStorage.getItem('search');
    }
    function handleThirdButtonClick() {
        const btn3 = document.getElementById('btn3');
        btn3.value = localStorage.getItem('search');
    }
</script>






<#--<div id="new_client" style="display:none;">-->
<#--    <form method="post" action="/lab10/client/save">-->
<#--        <div><input name="name" type="text" placeholder="clientname"></div>-->
<#--        <div><input name="email" type="text" placeholder="email"></div>-->
<#--        <div><input name="phone" type="text" placeholder="phone"></div>-->
<#--        <div><input name="address" type="text" placeholder="address"></div>-->
<#--        <div><input name="passport" type="text" placeholder="passport"></div>-->

<#--        <div><input type="submit" value="Сохранить"></div>-->
<#--    </form>-->
<#--</div>-->

<#--<script>-->
<#--    let state_form = 0;-->
<#--    function show_hide_form() {-->
<#--        let div = document.getElementById("new_client");-->
<#--        let btn = document.getElementById("btn_show_hide");-->

<#--        if (state_form == 0) {-->
<#--            div.style.display='block';-->
<#--            state_form = 1;-->
<#--            btn.innerHTML = 'Добавить клиента<span><img src="static/img/arrow-up.svg" height="24px"/></span>';-->
<#--        } else {-->
<#--            div.style.display='none';-->
<#--            state_form = 0;-->
<#--            btn.innerHTML = 'Добавить клиента<span><img src="static/img/arrow-down.svg" height="24px"/></span>';-->
<#--        }-->
<#--    }-->
<#--</script>-->
</body>
</html>