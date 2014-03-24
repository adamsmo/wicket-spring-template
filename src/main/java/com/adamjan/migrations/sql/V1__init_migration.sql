CREATE TABLE account (
  id        INT NOT NULL,
  user_name VARCHAR(100),
  password  VARCHAR(100),
  PRIMARY KEY (id),
  UNIQUE (user_name)
);

CREATE TABLE roles (
  id        INT NOT NULL,
  role_name VARCHAR(30),
  PRIMARY KEY (id),
  UNIQUE (role_name)
);

CREATE TABLE account_has_role (
  account_id INT,
  role_id    INT,
  PRIMARY KEY (account_id, role_id),
  FOREIGN KEY (account_id) REFERENCES account (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (id, role_name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, role_name) VALUES (2, 'USER');
INSERT INTO roles (id, role_name) VALUES (3, 'SUPER_USER');
INSERT INTO account (id, user_name, password) VALUES (1, 'admin', 'pseeesfffaw');
INSERT INTO account_has_role (account_id, role_id) VALUES (1, 1);