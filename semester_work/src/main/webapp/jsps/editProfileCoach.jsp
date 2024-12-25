<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Изменить профиль спортсмена</title>
    <link rel="stylesheet" href="styles.css">
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

        header button:hover {
            background-color: #003399;
            color: white;
        }

        main {
            padding: 20px;
            max-width: 600px;
            margin: 0 auto;
            background: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .nav-buttons {
            display: flex;
            justify-content: left;
            gap: 10px; /* Пространство между кнопками */
        }

        main h1 {
            text-align: center;
            color: #0044cc;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        form label {
            margin-top: 15px;
            font-weight: bold;
        }

        form input, form select, form button {
            margin-top: 5px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        form button {
            margin-top: 20px;
            background-color: #0044cc;
            color: white;
            cursor: pointer;
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
    <div class="nav-buttons">
        <nav>
            <button onclick="location.href='coach'">Назад</button>
        </nav>
    </div>
    <h1>Изменение профиля</h1>
</header>

<main>
    <h1>Изменить профиль</h1>
    <form action="updateProfile" method="post">
        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" value="${user.name}">

        <label for="age">Возраст:</label>
        <input type="number" id="age" name="age" value="${user.age}">

        <label for="phone">Телефон:</label>
        <input type="text" id="phone" name="phone" value="${user.phone}">

        <label for="address">Адрес проживания:</label>
        <input type="text" id="address" name="address" value="${user.address}">

        <label for="rank">Звание тренера:</label>
        <input type="text" id="rank" name="rank" value="${user.getCoach().rank}">

        <label>Спорт:
            <select name="sport">
                <c:forEach var="sport" items="${sports}">
                    <option value="${sport.get().name}">${sport.get().name}</option>
                </c:forEach>
            </select>
        </label>

        <button type="submit">Сохранить изменения</button>
    </form>
</main>

<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>
</html>
