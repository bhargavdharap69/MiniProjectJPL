Create Database romeobank;
CREATE TABLE users (
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100),
    balance DECIMAL(10, 2) DEFAULT 0.00
);
select * from users;
Truncate table users;
Drop database romeobank;