# Семестровая 1

## Семестровая работа №1. Сайт.

### Сдача работы и баллы
Работу необходимо будет сдать лично. На своей паре или в доп.время. Не тяните, распределяйте время, чтобы не было столпотворений!

На сдаче нужно показать код, продемонстрировать работу основного функционала, ответить на вопросы, связанные с программой.

При проверке будет оцениваться соответствие всем нижеперечисленным требованиям, реализация правильной архитектуры, умение применять используемые технологии, удобство пользования системой, защищенность системы.

### Требования
- Сайт, написанный на сервлетах без использования Java Web-фреймворков. Допускается использование вспомогательных библиотек, но не кода, который напрямую решает задачи бизнес-логики. При использовании любой библиотеки нужно хорошо понимать, для чего она, какие у неё возможности и логика использования.
- У сайта должна быть идея, обладающая новизной и актуальностью. Это не значит, что такого функционала нет ни на одном сайте, но можно взять функционал существующего портала и доработать его. Если сомневаетесь в сложности и новизне проекта, лучше подойдите-уточните.
- Сайт должен иметь удобный интерфейс.
- Необходимо использовать HTML5, CSS3. CSS-препроцессоры не запрещаются, но нужно знать их основные принципы. Если используете фреймворки (Bootstrap и проч.), должны уметь пояснить. что за классы используете. особенно классы сетки (grid).
- Необходимо корректно работать с формами: проверка данных, защита от повторной отправки там, где это нужно.
- Нужно использовать шаблонизатор Freemarker, JSP. В коде шаблонов нельзя напрямую вызывать Java-код. Должна быть развитая система шаблонов (свои теги, разделение видов на несколько частей). Если хотите использовать другой шаблонизатор-подойдите, утвердим лично.
- Сайт должен иметь аутентификацию, авторизацию, регистрацию.
- Сайт должен хранить данные в реляционной СУБД.
- Для работы с СУБД необходимо использовать JDBC (с PrepareStatement!). Допускается использование JDBC Template при наличии понимания, что это, но никаких ORM, кроме самописных по утверждению у преподавателя.
- Система должна работать, как минимум с  тремя сущностями. Должны быть связи M2M, O2M.
- Как минимум, для одной из сущностей должен быть реализован полностью CRUD с интерфейсом, но не обязательно в виде админки.
- Система должна быть построена по модели MVC. Необходимо придерживаться принципов SOLID. Весь код должен быть корректно поделен на пакеты. Крайне желательно выделить сервисы бизнес-логики.
- В работе нужно показать использование WebFilter, WebListener. Второй можно использовать для инициализации карты используемых сервисов (DAO/Repository, сервисы бизнес-логики, хелперы и т.д.).
- Необходимо применить Javascript.
- Проект должен быть основан на проекте Maven.

## Lombok

Экономия времени и пространства на стандартных геттерах и сеттерах (билдерах, ...)

```
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.22</version>
        <scope>provided</scope>
    </dependency>
```

В Intellij IDEA установите плагин Lombok

Геттеры и сеттеры для всех нестатических членов класса можно описать аннотациями класса @Getter @Setter


## JDBC, PostgreSQL

- Для каждой СУБД используется своя реализация JDBC спецификации, необходимо включить ее в проект
```
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
```
### Работа с JDBC
```
    // Загружаем драйвер
    Class.forName("org.postgresql.Driver");
    // Создаем подключение, после использования необходимо закрыть connection.close()
    Connection connection =
                    DriverManager.getConnection(
                            // адрес БД , имя пользователя, пароль
                            "jdbc:postgresql://localhost:5432/db_name","postgres","passwd");
                        
    // Создание оболочки для выполнения SQL выражений, после использования необходимо закрыть
    Statement statement = connection.createStatement();
    
    // Выполнение запроса (любая SQL команда) 
    boolean result = statement.execute("insert into ... ");
    
    // Запрос на изменение данных (insert, update) с возвратом количества измененных записей
    int result = statement.executeUpdate(
                    "update bank set name= 'Bank' ");
    
    // Запрос на выборку данных
    PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM bank where id= ? ");
    statement.setInt(1, id);

    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
        System.out.println(resultSet.getString("bik"));
        System.out.println(resultSet.getString("name"));
    }

    resultSet.close();
    

```



## Миграции Flyway

Миграции БД в проекте - механизм, позволяющий вести историю изменений структуры БД

Удобно сопровождать фиксирование изменений (например git) в структуре приложения, затрагивающие работу с БД,
соответствующим скриптом-миграцией.

```
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
    <version>10.21.0</version>
</dependency>

<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-database-postgresql</artifactId>
    <version>10.21.0</version>
    <scope>runtime</scope>
</dependency>
```
- Скрипты с миграциями располагаем в директории по умолчанию resources/db/migration
- Файлы именуем: `V1_0__description_first.sql, V1_1__description_second.sql`
- Скрипт содержит SQL команды, формирующие изменения в БД (create, insert, update, alter, ... )
- Перед стартом основной логики работы приложения отрабатываем миграцию:
    ```
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/db_name", "postgres", "password").load();
        flyway.migrate(); 
    ```
- Механизм миграций отработает только те скрипты, которые ранее не выполнялись (контроль по журналу версий)

## Freemarker

Freemarker - довольно удобный и быстрый шаблонизатор

### Использование Freemarker в веб приложении (сервлеты)

1. Подключаем к проекту библиотеку
```
    <dependency>
        <groupId>org.beangle.jakarta</groupId>
        <artifactId>beangle-jakarta-freemarker</artifactId>
        <version>2.3.31</version>
    </dependency>
```
2. Регистрируем сервлет, отрабатывающий обработку шаблонов (в файле web.xml)
```
    <servlet>
        <servlet-name>freemarker</servlet-name>
        <servlet-class>freemarker.ext.servlet.FreemarkerServlet</servlet-class>

        <!--
          Init-param documentation:
          https://freemarker.apache.org/docs/api/freemarker/ext/servlet/FreemarkerServlet.html
        -->

        <!-- FreemarkerServlet settings: -->
        <init-param>
            <param-name>TemplatePath</param-name>
            <param-value>classpath:template</param-value>
        </init-param>
        <init-param>
            <param-name>NoCache</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>ResponseCharacterEncoding</param-name>
            <!-- Use the output_encoding setting of FreeMarker: -->
            <param-value>fromTemplate</param-value>
        </init-param>
        <init-param>
            <param-name>ExceptionOnMissingTemplate</param-name>
            <!-- true => HTTP 500 on missing template, instead of HTTP 404. -->
            <param-value>true</param-value>
        </init-param>

        <!-- FreeMarker engine settings: -->
        <init-param>
            <param-name>incompatible_improvements</param-name>
            <param-value>2.3.31</param-value>
            <!--
              Recommended to set to a high value.
              See: https://freemarker.apache.org/docs/pgui_config_incompatible_improvements.html
            -->
        </init-param>
        <init-param>
            <param-name>template_exception_handler</param-name>
            <!-- Use "html_debug" during development! -->
            <param-value>rethrow</param-value>
        </init-param>
        <init-param>
            <param-name>template_update_delay</param-name>
            <!-- Use 0 during development! Consider what value you need otherwise. -->
            <param-value>30 s</param-value>
        </init-param>
        <init-param>
            <param-name>default_encoding</param-name>
            <!-- The encoding of the template files: -->
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>output_encoding</param-name>
            <!-- The encoding of the template output; Note that you must set
                 "ResponseCharacterEncodring" to "fromTemplate" for this to work! -->
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>locale</param-name>
            <!-- Influences number and date/time formatting, etc. -->
            <param-value>en_US</param-value>
        </init-param>
        <init-param>
            <param-name>number_format</param-name>
            <param-value>0.##########</param-value>
        </init-param>

        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>freemarker</servlet-name>
        <url-pattern>*.ftl</url-pattern>
        <url-pattern>*.ftlxxx</url-pattern>
        <!-- HTML and XML auto-escaped if incompatible_improvements >= 2.3.24: -->
        <url-pattern>*.ftlh</url-pattern>
        <url-pattern>*.ftlx</url-pattern>
    </servlet-mapping>
```
3. Шаблоны располагаем в директории (по-умолчанию) resources/template
4. В сервлетах, ответственных за возврат html контента используем конструкцию вида
```
        // кладем в атрибуты запроса данные, эти атрибуты будут обработаны шаблонизатором
        request.setAttribute("clientid", clientId);
        request.setAttribute("clientname", clientName);
        request.setAttribute("hello", "Hello for freemarker!");

        //Передаем управление диспетчеру, говоря, что требуется вызвать сервлет по пути
        // index.ftl
        request.getRequestDispatcher("index.ftl").forward(request, response);
```


## JS

https://learn.javascript.ru/