CREATE USER 'procedury'@'localhost' IDENTIFIED BY 'Proc@1234';
GRANT SELECT ON messengerdatabase.* to 'procedury'@'localhost';
GRANT EXECUTE ON messengerdatabase.* TO 'procedury'@'localhost';
