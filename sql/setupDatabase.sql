CREATE SCHEMA `logbook` ;

CREATE TABLE `logbook`.`user` (
  `Name` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Admin` TINYINT NOT NULL,
  PRIMARY KEY (`Name`));

CREATE TABLE `logbook`.`trip` (
  `TripID` INT NOT NULL AUTO_INCREMENT,
  `User` VARCHAR(45) NOT NULL,
  `Origin` VARCHAR(200) NOT NULL,
  `Destination` VARCHAR(200) NOT NULL,
  `Distance` FLOAT(6,1) NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`TripID`));
