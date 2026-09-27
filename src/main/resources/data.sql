DELETE FROM attendance;
DELETE FROM marks;
DELETE FROM students;

INSERT INTO students (id, name, email, department, year, username, password) VALUES
(1, 'Ananya Sharma', 'ananya.sharma@example.com', 'Computer Science', 2, 'ananya', 'pass123'),
(2, 'Rahul Verma', 'rahul.verma@example.com', 'Information Technology', 3, 'rahul', 'pass123'),
(3, 'Meera Iyer', 'meera.iyer@example.com', 'Electronics', 1, 'meera', 'pass123');

INSERT INTO marks (student_id, subject, marks) VALUES
(1, 'Database Management Systems', 86),
(1, 'Operating Systems', 78),
(1, 'Java Programming', 91),
(2, 'Computer Networks', 82),
(2, 'Software Engineering', 88),
(2, 'Web Technologies', 80),
(3, 'Digital Electronics', 84),
(3, 'Engineering Mathematics', 76),
(3, 'Physics', 89);

INSERT INTO attendance (student_id, subject, percentage) VALUES
(1, 'Database Management Systems', 92.5),
(1, 'Operating Systems', 88.0),
(1, 'Java Programming', 95.0),
(2, 'Computer Networks', 85.0),
(2, 'Software Engineering', 90.5),
(2, 'Web Technologies', 87.0),
(3, 'Digital Electronics', 91.0),
(3, 'Engineering Mathematics', 83.5),
(3, 'Physics', 89.5);
