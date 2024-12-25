<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Группы спортсменов</title>
    <style>
        /* Заголовок страницы */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #f0f4f8, #d9e8fc);
            color: #333;
        }
        .page-title {
            text-align: center;
            color: #0044cc;
            margin-top: 20px;
            font-size: 48px;
        }


        .empty-message {
            text-align: center;
            color: #666;
            font-size: 32px;
        }


        .groups-table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            font-family: Arial, sans-serif;
        }

        .groups-table thead {
            background-color: #0044cc;
            color: white;
        }
        header button {
            background-color: white;
            color: #0044cc;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            position: absolute;
            left: 20px;
            top: 20px;
            transition: background-color 0.3s, color 0.3s;
        }

        .groups-table th, .groups-table td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }

        .groups-table tbody tr {
            background-color: #f9f9f9;
        }

        .groups-table tbody tr:nth-child(even) {
            background-color: #e8f1fc;
        }

        /* Список спортсменов */
        .groups-table ul {
            padding-left: 20px;
            margin: 0;
            list-style-type: disc;
        }

        .groups-table ul li {
            margin: 5px 0;
            color: #333;
        }

        .action-buttons {
            display: inline-flex;
            gap: 10px;
        }

        .action-buttons button {
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 12px;
            cursor: pointer;
        }

        .action-buttons button:hover {
            background-color: #003399;
        }

        .add-button {
            margin-left: auto;
            margin-bottom: auto;
            display: block;
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        .add-button:hover {
            background-color: #003399;
        }
        .delete-btn {
            margin-left: auto;
            display: block;
            background-color: #cc0000;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .delete-btn:hover {
            background-color: #990000;
        }
        .add-group-container {
            display: flex;
            justify-content: flex-end; /* Выровнять кнопку по правому краю */
            margin-top: 20px; /* Отступ от таблицы */
            margin-right: 10%; /* Дополнительный отступ от края страницы */
        }

        /* Стиль кнопки "Добавить группу" */
        .add-group-btn {
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .add-group-btn:hover {
            background-color: #003399;
        }
    </style>
</head>
<body>
<h1 class="page-title">Группы спортсменов</h1>

<c:set var="hasCoach" value="false"/>

<c:if test="${not empty user.getCoach()}">
    <c:set var="hasCoach" value="true"/>
</c:if>

<header>
    <c:if test="${hasCoach}">
        <button onclick="location.href='coach'">Назад</button>
    </c:if>
    <c:if test="${!hasCoach}">
        <button onclick="location.href='sportsman'">Назад</button>
    </c:if>
</header>


<c:if test="${empty groups}">
    <c:if test="${hasCoach}">
        <p class="empty-message">Не найдено групп у тренера.</p>
    </c:if>
    <c:if test="${!hasCoach}">
        <p class="empty-message">Не найдено групп у спортсмена.</p>
    </c:if>
</c:if>

<c:if test="${not empty groups}">
    <table class="groups-table">
        <thead>
        <tr>
            <c:if test="${!hasCoach}">
                <th>Тренер</th>
            </c:if>
            <th>Название группы</th>
            <th>Спортсмены</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="group" items="${groups}">
            <tr>
                <c:if test="${!hasCoach}">
                    <td>${group.get().getCoach().name}</td>
                </c:if>
                <td>${group.get().groupName}</td>
                <td>
                    <ul>
                        <c:forEach var="sportsman" items="${group.get().sportsmen}">
                            <li>${sportsman.name}, звание - ${sportsman.getSportsman().rank}
                                <c:if test="${hasCoach}">
                                    <div class="action-buttons">
                                        <form action="deleteSportsmanFromGroup" method="post" style="display: inline;">
                                            <input type="hidden" name="groupId" value="${group.get().id}">
                                            <input type="hidden" name="sportsmanId" value="${sportsman.getSportsman().id}">
                                            <button type="submit">Удалить</button>
                                        </form>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:if test="${hasCoach}">
                        <form action="addSportsmanToGroup" method="get">
                            <input type="hidden" name="groupId" value="${group.get().id}">
                            <input type="hidden" name="groupName" value="${group.get().groupName}">
                            <button class="add-button">Добавить спортсменов</button>
                        </form>
                    </c:if>

                    <c:if test="${hasCoach}">
                        <form action="deleteGroup" method="post">
                            <input type="hidden" name="groupId" value="${group.get().id}">
                            <button type="submit" class="delete-btn">Удалить группу</button>
                        </form>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${hasCoach}">
    <div class="add-group-container">
        <form action="addGroup" method="get">
            <button type="submit" class="add-group-btn">Добавить группу</button>
        </form>
    </div>
</c:if>

</body>
</html>
