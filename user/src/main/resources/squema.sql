CREATE TABLE role (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    role_descrip VARCHAR(255) NOT NULL
);

CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);