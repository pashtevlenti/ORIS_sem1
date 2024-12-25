INSERT INTO users (login, password) VALUES
                                        ('coach_login_1', 'password123'),
                                        ('coach_login_2', 'password456'),
                                        ('sportsman_login_1', 'pass_sportsman1'),
                                        ('sportsman_login_2', 'pass_sportsman2'),
                                        ('sportsman_login_3', 'pass_sportsman3'),
                                        ('sportsman_login_4', 'pass_sportsman4'),
                                        ('worker_login_1', 'workerpass1'),
                                        ('worker_login_2', 'workerpass2'),
                                        ('worker_login_3', 'workerpass3');
INSERT INTO sport (name) VALUES
                             ('Football'),
                             ('Basketball'),
                             ('Tennis'),
                             ('Swimming'),
                             ('Boxing');
INSERT INTO coach (name, gender, age, phone, adress, sport_id, user_id) VALUES
                                                                            ('John Smith', 'M', 40, '123-456-7890', '123 Coach St.', 1, 1),
                                                                            ('Jane Doe', 'F', 35, '987-654-3210', '456 Coach Ave.', 2, 2),
                                                                            ('Peter Johnson', 'M', 45, '111-222-3333', '789 Coach Blvd.', 3, 1),
                                                                            ('Anna White', 'F', 29, '444-555-6666', '321 Coach Dr.', 4, 2),
                                                                            ('Michael Brown', 'M', 50, '777-888-9999', '654 Coach Ln.', 5, 1);
INSERT INTO group_sportsman (group_name, coach_id) VALUES
                                                       ('Football Group A', 1),
                                                       ('Football Group B', 1),
                                                       ('Basketball Group A', 2),
                                                       ('Tennis Group C', 3),
                                                       ('Swimming Group A', 4),
                                                       ('Boxing Group B', 5);
INSERT INTO schedule_training (day_of_week, time, coach_id) VALUES
                                                                ('Monday', '08:00 AM', 1),
                                                                ('Wednesday', '06:00 PM', 1),
                                                                ('Tuesday', '09:00 AM', 2),
                                                                ('Thursday', '07:00 PM', 2),
                                                                ('Saturday', '10:00 AM', 3),
                                                                ('Sunday', '06:00 PM', 4),
                                                                ('Friday', '08:00 AM', 5);
INSERT INTO sportsman (name, gender, age, phone, adress, group_id, user_id) VALUES
                                                                                ('Alice Brown', 'F', 20, '555-1234', '123 Sports St.', 1, 3),
                                                                                ('Bob White', 'M', 22, '555-5678', '456 Sports Ave.', 2, 4),
                                                                                ('Charlie Green', 'M', 19, '555-9876', '789 Sports Ln.', 3, 5),
                                                                                ('Diana Blue', 'F', 21, '555-6543', '321 Sports Dr.', 4, 6),
                                                                                ('Ethan Black', 'M', 23, '555-1111', '231 Sports Rd.', 5, 4),
                                                                                ('Fiona Gray', 'F', 24, '555-2222', '456 Sports Ct.', 6, 5);
INSERT INTO worker (name, gender, age, phone, adress, post, group_id, user_id) VALUES
                                                                                   ('Clara Green', 'F', 28, '555-4321', '789 Worker Rd.', 'Administrator', 1, 7),
                                                                                   ('David Black', 'M', 32, '555-8765', '987 Worker Blvd.', 'Manager', 2, 8),
                                                                                   ('Eva White', 'F', 30, '555-5555', '654 Worker Ave.', 'Coordinator', 3, 9);
INSERT INTO sportsman_coach (sportsman_id, coach_id) VALUES
                                                         (1, 1), -- Alice Brown тренируется у John Smith
                                                         (2, 1), -- Bob White тренируется у John Smith
                                                         (3, 2), -- Charlie Green тренируется у Jane Doe
                                                         (4, 3), -- Diana Blue тренируется у Peter Johnson
                                                         (5, 4), -- Ethan Black тренируется у Anna White
                                                         (6, 5); -- Fiona Gray тренируется у Michael Brown
INSERT INTO sportsman_schedule_training (sportsman_id, schedule_training_id) VALUES
                                                                                 (1, 1), -- Alice Brown на Monday 08:00 AM с тренером John Smith
                                                                                 (2, 2), -- Bob White на Wednesday 06:00 PM с тренером John Smith
                                                                                 (3, 3), -- Charlie Green на Tuesday 09:00 AM с тренером Jane Doe
                                                                                 (4, 5), -- Diana Blue на Saturday 10:00 AM с тренером Peter Johnson
                                                                                 (5, 6), -- Ethan Black на Sunday 06:00 PM с тренером Anna White
                                                                                 (6, 7); -- Fiona Gray на Friday 08:00 AM с тренером Michael Brown
