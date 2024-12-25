<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Спортивная школа</title>
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

        header h1 {
            text-align: center;
        }

        main {
            padding: 20px;
        }

        #gallery {
            text-align: center;
            margin-bottom: 40px;
        }

        #gallery h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #0044cc;
        }

        .carousel {
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            position: relative;
            max-width: 600px;
            margin: 0 auto;
        }

        .slide {
            display: none;
            text-align: center;
        }

        .slide img {
            width: 100%;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .slide p {
            margin-top: 10px;
            font-size: 16px;
            color: #555;
        }

        section {
            margin-bottom: 40px;
        }

        section h2 {
            font-size: 22px;
            color: #0044cc;
            margin-bottom: 20px;
        }

        .card {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin-right: 20px;
            object-fit: cover;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card p {
            font-size: 16px;
            color: #555;
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
    <button onclick="location.href='login'">Вход в аккаунт</button>
    <h1>Спортивный комплекс "Электрон"</h1>
</header>

<main>
    <!-- Карусель спортивной школы -->
    <section id="gallery">
        <h2>Фотографии спортивной школы</h2>
        <div class="carousel">
            <div class="slide">
                <img src="static/images/football.jpeg" alt="Зал">
                <p>Футбольный зал.</p>
            </div>
            <div class="slide">
                <img src="static/images/acrobatic.jpeg" alt="Зал">
                <p>Зал акробатики.</p>
            </div>
            <div class="slide">
                <img src="static/images/basketball.jpeg" alt="Зал">
                <p>Баскетбольный зал.</p>
            </div>
            <div class="slide">
                <img src="static/images/dzudo.jpeg" alt="Зал">
                <p>Зал дзюдо.</p>
            </div>
            <div class="slide">
                <img src="static/images/tableTennis.jpg" alt="Зал">
                <p>Зал настольного тенниса.</p>
            </div>
            <div class="slide">
                <img src="static/images/cafeteria.jpeg" alt="Буфет">
                <p>Буфет с полезной едой для спортсменов.</p>
            </div>
        </div>
    </section>

    <!-- Секция тренеров -->
    <section id="coaches">
        <h2>Наши тренеры</h2>
        <div class="card">
            <img src="static/images/Elmira.jpg" alt="Тренер">
            <p>Эльмира Назмединова — старший тренер по акробатике. Опыт работы более 25 лет. Подготовила более 10 мастеров спорта России</p>
        </div>
        <div class="card">
            <img src="static/images/Sergey.jpg" alt="Тренер">
            <p>Сергей Кожин - тренер по акробатике. Подготовил более 3 мастеров спорта</p>
        </div>
        <div class="card">
            <img src="static/images/Lubov.jpg" alt="Тренер">
            <p>Любовь Лебедева  — тренер по акробатике, подготовила множество способных маленьктх ребят</p>
        </div>
    </section>

    <!-- Секция спортсменов -->
    <section id="athletes">
        <h2>Именитые спортсмены</h2>
        <div class="card">
            <img src="static/images/Lena.jpg" alt="Спортсмен">
            <p>Елена Нейман - мастер спорта России, многократная чемпионка России, серебряный призёр Чемпионата европы по прыжкам на акробатической дорожке.</p>
        </div>
        <div class="card">
            <img src="static/images/Pavel.jpg" alt="Спортсмен">
            <p>Павел Солодянкин - мастер спорта России по прыжкам на АКД</p>
        </div>
        <div class="card">
            <img src="static/images/Egor.jpg" alt="Спортсмен">
            <p>Егор Селезнёв - мастер спорта России, многократный финалист первенств мира в разных возрастах, а также многократный чемпион России по прыжка на АКД.</p>
        </div>
    </section>
</main>

<footer>
    <p>&copy; 2024 Спортивный комплекс "Электрон". Все права защищены.</p>
</footer>

<script>
    let currentIndex = 0;
    const slides = document.querySelectorAll('.slide');
    const totalSlides = slides.length;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.style.display = i === index ? 'block' : 'none';
        });
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        showSlide(currentIndex);
    }

    setInterval(nextSlide, 3000);
    showSlide(currentIndex);
</script>
</body>
</html>
