<%--@elvariable id="user" type="javax.xml.stream.util.StreamReaderDelegate"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
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
            padding: 20px;
        }

        .section {
            margin: 20px auto;
            max-width: 600px;
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

        .section button {
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
            width: 100%;
            text-align: center;
        }

        .section button:hover {
            background-color: #003399;
        }

        ul {
            padding: 0;
            list-style: none;
        }

        ul li {
            margin-bottom: 10px;
            font-size: 16px;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 10px 0;
            color: #333;
        }

        input[type="checkbox"] {
            margin-right: 10px;
        }

        select {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 14px;
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
            <button onclick="location.href='logout'">Выйти</button>
        </nav>
    </div>
    <h1>Профиль спортсмена</h1>
</header>

<main>
    <section class="section">
        <h2>Личная информация</h2>
        <p><strong>Имя:</strong>${user.name}</p>
        <p><strong>Возраст:</strong>${user.age}</p>
        <p><strong>Телефон:</strong>${user.phone}</p>
        <p><strong>Адрес:</strong>${user.address}</p>
        <p><strong>Звание:</strong>${user.getSportsman().rank}</p>
        <button onclick="location.href='updateProfile'">Изменить данные</button>
        <button onclick="location.href='coaches'">Посмотреть моих тренеров</button>
        <button onclick="location.href='groups'">Посмотреть мои группы</button>
        <button onclick="location.href='trainings'">Посмотреть все тренировки</button>
    </section>
</main>

<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>
</html>
