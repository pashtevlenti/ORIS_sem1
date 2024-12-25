<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить тренировку</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 80vh;
        }

        section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        section h2 {
            color: #0044cc;
            margin-bottom: 20px;
            text-align: center;
        }

        form label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        form select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        form button {
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
            width: 100%;
        }

        form button:hover {
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
    <h1>Добавить тренировку</h1>
    <button onclick="location.href='trainings'">Назад</button>
</header>
<main class="container">
    <section>
        <h2>Заполните данные</h2>
        <form action="addTrainingToGroup" method="post" onsubmit="return validateCheckboxSelection();">
            <h1>Группы</h1>
                <div class="checkbox-group">
                    <div class="athletes-list">
                        <c:forEach var="group" items="${groups}">
                            <label>
                                <input type="checkbox" name="groupIds" value="${group.get().id}">
                                    ${group.get().groupName}
                            </label><br>
                        </c:forEach>
                    </div>
                </div>
            <label for="day">День недели:</label>
            <select id="day" name="day" required>
                <option value="" disabled selected>Выберите день недели</option>
                <option value="Понедельник">Понедельник</option>
                <option value="Вторник">Вторник</option>
                <option value="Среда">Среда</option>
                <option value="Четверг">Четверг</option>
                <option value="Пятница">Пятница</option>
                <option value="Суббота">Суббота</option>
                <option value="Воскресенье">Воскресенье</option>
            </select>

            <label for="time">Время:</label>
            <select id="time" name="time" required>
                <option value="" disabled selected>Выберите время</option>
                <option value="08:00">08:00</option>
                <option value="08:30">08:30</option>
                <option value="09:00">09:00</option>
                <option value="09:30">09:30</option>
                <option value="10:00">10:00</option>
                <option value="10:30">10:30</option>
                <option value="11:00">11:00</option>
                <option value="11:30">11:30</option>
                <option value="12:00">12:00</option>
                <option value="12:30">12:30</option>
                <option value="13:00">13:00</option>
                <option value="13:30">13:30</option>
                <option value="14:00">14:00</option>
                <option value="14:30">14:30</option>
                <option value="15:00">15:00</option>
                <option value="15:30">15:30</option>
                <option value="16:00">16:00</option>
                <option value="16:30">16:30</option>
                <option value="17:00">17:00</option>
                <option value="17:30">17:30</option>
                <option value="18:00">18:00</option>
                <option value="18:30">18:30</option>
                <option value="19:00">19:00</option>
                <option value="19:30">19:30</option>
                <option value="20:00">20:00</option>
            </select>
            <input type="hidden" name="groupId" value="${groupId}">
            <button type="submit">Добавить тренировку</button>
        </form>
    </section>
</main>
<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>

<script>
    function validateCheckboxSelection() {
        const checkboxes = document.querySelectorAll('input[name="groupIds"]');
        for (let checkbox of checkboxes) {
            if (checkbox.checked) {
                return true; // Если хотя бы один чекбокс выбран, форма отправляется
            }
        }
        alert("Пожалуйста, выберите хотя бы одну группу. Если групп нет, сначала создайте их");
        return false; // Если ни один чекбокс не выбран, форма не отправляется
    }
</script>
</html>
