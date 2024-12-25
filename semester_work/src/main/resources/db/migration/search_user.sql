WITH user_info AS (
    SELECT id AS user_id
    FROM users
    WHERE login = 'coach_login_1' AND password = 'password123'
)
SELECT
    'sportsman' AS role, s.id, s.name, s.gender, s.age, s.phone
FROM sportsman s
         JOIN user_info u ON s.user_id = u.user_id
UNION ALL
SELECT
    'coach' AS role, c.id, c.name, c.gender, c.age, c.phone
FROM coach c
         JOIN user_info u ON c.user_id = u.user_id
UNION ALL
SELECT
    'staff' AS role, st.id, st.name, st.gender, st.age, st.phone
FROM worker st
         JOIN user_info u ON st.user_id = u.user_id;