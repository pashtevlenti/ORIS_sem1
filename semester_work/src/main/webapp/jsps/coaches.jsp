<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Информация о спортсмене</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #f0f4f8, #d9e8fc);
            color: #333;
        }

        header {
            background-color: #0044cc;
            color: white;
            padding: 20px;
            text-align: center;
            position: relative;
        }

        header button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: white;
            color: #0044cc;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s, color 0.3s;
        }

        header button:hover {
            background-color: #003399;
            color: white;
        }

        main {
            padding: 20px;
        }

        .section {
            margin: 20px auto;
            max-width: 800px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .section h2, .section h1 {
            color: #0044cc;
            margin-bottom: 20px;
            text-align: center;
        }

        .section p {
            margin: 10px 0;
            font-size: 16px;
            color: #555;
        }

        .actions {
            display: flex;
            justify-content: flex-start;
            gap: 10px;
            margin-top: 20px;
        }

        .actions button {
            flex: 1;
            padding: 10px 20px;
            background-color: #0044cc;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .actions button:hover {
            background-color: #003399;
        }

        ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        ul li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #f9f9f9;
            margin-bottom: 10px;
            border-radius: 5px;
            font-size: 16px;
            color: #333;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        ul li:last-child {
            margin-bottom: 0;
        }

        ul li input[type="checkbox"] {
            margin-left: 10px;
        }

        #addCoachForm {
            display: none;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        #addCoachForm select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        #addCoachForm button {
            width: 100%;
            padding: 10px 15px;
            background-color: #0044cc;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        #addCoachForm button:hover {
            background-color: #003399;
        }

        footer {
            text-align: center;
            padding: 10px;
            background-color: #f0f4f8;
            font-size: 14px;
            color: #666;
        }
    </style>
</head>

<body>
<header>
    <button onclick="location.href='sportsman'">Назад</button>
    <h1>Список ваших тренеров</h1>
</header>

<main>
    <section class="section">
        <h2>Уже добавленные тренеры</h2>
        <c:choose>
            <c:when test="${empty coaches}">
                <p>Тренеры отсутствуют.</p>
            </c:when>
            <c:otherwise>
                <form action="deleteCoach" method="post">
                    <input type="hidden" name="sportsmanId" value="${user.getSportsman().id}">
                    <ul>
                        <c:forEach var="coach" items="${coaches}">
                            <li>
                                <span>${coach.name} (${coach.getCoach().getSport().name})</span>
                                <input type="checkbox" name="coachIds" value="${coach.getCoach().id}">
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="actions">
                        <button type="submit">Удалить выбранных</button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
        <div class="actions">
            <button type="button" onclick="toggleAddCoachForm()">Добавить тренера</button>
        </div>
    </section>

    <section id="addCoachForm" class="section">
        <h2>Добавление тренера</h2>
        <c:choose>
            <c:when test="${empty availableCoaches}">
                <p>Нет доступных тренеров для добавления.</p>
            </c:when>
            <c:otherwise>
                <form action="assignCoach" method="post">
                    <input type="hidden" name="sportsmanId" value="${user.getSportsman().id}">
                    <label for="coach">Выберите тренера:</label>
                    <select name="coachIds" id="coach" multiple>
                        <c:forEach var="coach" items="${availableCoaches}">
                            <option value="${coach.getCoach().id}">
                                    ${coach.name} (${coach.getCoach().getSport().name})
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit">Сохранить</button>
                </form>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>

<script>
    function toggleAddCoachForm() {
        const form = document.getElementById("addCoachForm");
        form.style.display = form.style.display === "none" || form.style.display === "" ? "block" : "none";
    }
</script>

</html>
