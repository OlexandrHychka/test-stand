CREATE TABLE IF NOT EXISTS books
(
  id       INT(11)        NOT NULL AUTO_INCREMENT,
  category VARCHAR(100)   NOT NULL,
  author   VARCHAR(100)   NOT NULL,
  title    VARCHAR(100)   NOT NULL,
  price    DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id)
);