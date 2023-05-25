DROP TABLE IF EXISTS nombredetabla;

CREATE TABLE nombredetabla(ID INT AUTO_INCREMENT PRIMARY KEY, campo1 INT NOT NULL, campo2 VARCHAR(100) NOT NULL, campo3 VARCHAR(100) NOT NULL);



-- para probar de la database --

INSERT INTO nombredetabla(campo1, campo2, campo3) VALUES (482701, 'Carlos', 'Rodriguez'), (555222, 'Perla', 'Blanco');