<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
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

        .nav-buttons {
            display: flex;
            justify-content: left;
            gap: 10px; /* Пространство между кнопками */
        }

        header button {
            background-color: white;
            color: #0044cc;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s, color 0.3s; /* Добавляет плавный эффект на наведение */
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

        form label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        form input, form select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
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
    <div class="nav-buttons">
        <nav>
            <button onclick="location.href='index'">На главную</button>
            <button onclick="location.href='login'">Назад</button>
        </nav>
    </div>
    <h1>Регистрация</h1>
</header>

<main>
    <section>
        <form action="check-register" method="post">
            <label for="name">Имя:</label>
            <input type="text" id="name" name="name" required>

            <label for="gender">Пол:</label>
            <select id="gender" name="gender">
                <option value="Мужской">Мужской</option>
                <option value="Женский">Женский</option>
            </select>

            <label for="age">Возраст:</label>
            <input type="number" id="age" name="age" min="1">

            <label for="role">Роль:</label>
            <select id="role" name="role" required>
                <option value="sportsman">Спортсмен</option>
                <option value="coach">Тренер</option>
                <option value="worker">Сотрудник</option>
            </select>

            <label for="phone">Телефон:</label>
            <input type="tel" id="phone" name="phone">

            <label for="address">Адрес проживания:</label>
            <input type="text" id="address" name="address">

            <label for="login">Логин:</label>
            <input type="text" id="login" name="login" required>

            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>

            <label for="confirm-password">Подтвердите пароль:</label>
            <input type="password" id="confirm-password" name="confirm_password" required>

            <c:if test="${not empty errorMessage}">
                <p style="color: red;">${errorMessage}</p>
            </c:if>

            <button type="submit">Зарегистрироваться</button>
        </form>
    </section>
</main>

<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>
</html>
