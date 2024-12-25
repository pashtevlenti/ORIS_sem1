<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>Добавление группы</title>
    <style>
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
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #f0f4f8, #d9e8fc);
            color: #333;
        }

        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border: 1px solid #d0d7df;
        }

        h1 {
            text-align: center;
            color: #0044cc;
            font-size: 32px;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: bold;
            margin-bottom: 8px;
            color: #444;
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
            background-color: #f9f9f9;
        }

        input[type="checkbox"] {
            margin-right: 10px;
        }

        .checkbox-group {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin-bottom: 20px;
        }

        .checkbox-group label {
            background-color: #e8f1fc;
            padding: 10px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            display: inline-flex;
            align-items: center;
        }

        .checkbox-group label:hover {
            background-color: #d0e7ff;
        }

        button {
            align-self: flex-end;
            background-color: #0044cc;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #003399;
        }

        footer {
            text-align: center;
            padding: 15px 0;
            background-color: #f0f4f8;
            font-size: 14px;
            color: #666;
            margin-top: 30px;
            border-top: 1px solid #d0d7df;
        }
    </style>
</head>

<header>
    <button onclick="location.href='groups'">Назад</button>
</header>
<body>
<main class="container">
    <section>
        <h1>Добавление группы</h1>
        <form action="addGroup" method="post">

            <label for="groupName">Название группы:</label>
            <input type="text" id="groupName" name="groupName" placeholder="Введите название группы" required>

            <label>Спортсмены:</label>
            <div class="checkbox-group">
                <c:forEach var="athlete" items="${athletes}">
                    <label>
                        <input type="checkbox" name="athleteIds" value="${athlete.getSportsman().id}">
                            ${athlete.name}
                    </label><br>
                </c:forEach>
            </div>
            <button type="submit">Создать группу</button>
        </form>
    </section>
</main>
<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>
</body>
</html>
