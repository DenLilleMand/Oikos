-- ----------------------------------------------------
-- Database creation script
-- Based on the reverse engineered database from
-- MySQL Workbench created during the course of the
-- project by Jon, Mads, Matti, Peter
-- 
-- Users and data added manually by Mads
-- Script created by Mads
-- 
-- ----------------------------------------------------

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `2nd_semesters` DEFAULT CHARACTER SET utf8 ;
USE `2nd_semesters` ;


-- ----------------------------------------------------
-- Create Users
-- ----------------------------------------------------
CREATE USER `matti`@`%` IDENTIFIED BY 'vn4Nh4XAGXvFsZ';
CREATE USER `loginuser`@`%` IDENTIFIED BY 'pass';

-- -----------------------------------------------------
-- Table `2nd_semesters`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Account` (
  `PK_AccountId` INT(11) NOT NULL AUTO_INCREMENT,
  `AccountNumber` INT(11) NULL DEFAULT NULL,
  `RegistrationNumber` INT(11) NULL DEFAULT NULL,
  `AccountType` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AccountId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Address` (
  `PK_AddressId` INT(11) NOT NULL AUTO_INCREMENT,
  `StreetName` VARCHAR(45) NULL DEFAULT NULL,
  `StreetNumber` VARCHAR(45) NULL DEFAULT NULL,
  `Floor` INT(11) NULL DEFAULT NULL,
  `ApartmentNumberOrSide` VARCHAR(45) NULL DEFAULT NULL,
  `CO` VARCHAR(45) NULL DEFAULT NULL,
  `PostalArea` VARCHAR(45) NULL DEFAULT NULL,
  `PostalCode` INT(11) NULL DEFAULT NULL,
  `City` VARCHAR(45) NULL DEFAULT NULL,
  `Country` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_AddressId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Company` (
  `PK_CompanyId` INT(11) NOT NULL AUTO_INCREMENT,
  `CVR` INT(11) NULL DEFAULT NULL,
  `CompanyName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_CompanyId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Customer` (
  `PK_CustomerId` INT(11) NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NULL DEFAULT NULL,
  `LastName` VARCHAR(45) NULL DEFAULT NULL,
  `CPR` CHAR(11) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_CustomerId`),
  UNIQUE INDEX `CPR_UNIQUE` (`CPR` ASC),
  INDEX `FIRSTNAME` (`FirstName` ASC),
  INDEX `LASTNAME` (`LastName` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerAccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerAccount` (
  `FK_AccountId` INT(11) NOT NULL,
  `FK_CustomerId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_CustomerId`, `FK_AccountId`),
  INDEX `CustomerAccount_Account_idx` (`FK_AccountId` ASC),
  CONSTRAINT `CustomerAccount_Account`
    FOREIGN KEY (`FK_AccountId`)
    REFERENCES `2nd_semesters`.`Account` (`PK_AccountId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerAccount_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerAddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerAddress` (
  `FK_CustomerId` INT(11) NOT NULL,
  `FK_AddressId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_CustomerId`, `FK_AddressId`),
  INDEX `CustomerAddress_Address_idx` (`FK_AddressId` ASC),
  CONSTRAINT `CustomerAddress_Address`
    FOREIGN KEY (`FK_AddressId`)
    REFERENCES `2nd_semesters`.`Address` (`PK_AddressId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerAddress_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerCompany`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerCompany` (
  `FK_CompanyId` INT(11) NOT NULL,
  `FK_CustomerId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_CompanyId`, `FK_CustomerId`),
  INDEX `CustomerCompany_Customer_idx` (`FK_CustomerId` ASC),
  CONSTRAINT `CustomerCompany_Company`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Company` (`PK_CompanyId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerCompany_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Email` (
  `PK_EmailId` INT(11) NOT NULL AUTO_INCREMENT,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_EmailId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerEmail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerEmail` (
  `FK_EmailId` INT(11) NOT NULL,
  `FK_CustomerId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_EmailId`, `FK_CustomerId`),
  INDEX `CustomerEmail_idx` (`FK_CustomerId` ASC),
  CONSTRAINT `CustomerEmail_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerEmail_Email`
    FOREIGN KEY (`FK_EmailId`)
    REFERENCES `2nd_semesters`.`Email` (`PK_EmailId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Mobile_phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Mobile_phone` (
  `PK_Mobile_phoneId` INT(11) NOT NULL AUTO_INCREMENT,
  `Mobile_phone` VARCHAR(16) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_Mobile_phoneId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerMobile_phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerMobile_phone` (
  `FK_CustomerId` INT(11) NOT NULL,
  `FK_Mobile_phoneId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_CustomerId`, `FK_Mobile_phoneId`),
  INDEX `CustomerMobile_phone_Mobile_phone_idx` (`FK_Mobile_phoneId` ASC),
  CONSTRAINT `CustomerMobile_phone_Mobile_phone`
    FOREIGN KEY (`FK_Mobile_phoneId`)
    REFERENCES `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerMobile_phone_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`Phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`Phone` (
  `PK_PhoneId` INT(11) NOT NULL AUTO_INCREMENT,
  `Phone` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_PhoneId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`CustomerPhone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`CustomerPhone` (
  `FK_PhoneId` INT(11) NOT NULL,
  `FK_CustomerId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_PhoneId`, `FK_CustomerId`),
  INDEX `CustomerPhone_Customer_idx` (`FK_CustomerId` ASC),
  CONSTRAINT `CustomerPhone_Customer`
    FOREIGN KEY (`FK_CustomerId`)
    REFERENCES `2nd_semesters`.`Customer` (`PK_CustomerId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CustomerPhone_Phone`
    FOREIGN KEY (`FK_PhoneId`)
    REFERENCES `2nd_semesters`.`Phone` (`PK_PhoneId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`User` (
  `PK_UserId` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(150) NOT NULL,
  `hash` VARCHAR(512) NOT NULL,
  `salt` CHAR(32) NOT NULL,
  `Name` VARCHAR(150) NOT NULL,
  `UserType` INT(11) NOT NULL,
  PRIMARY KEY (`PK_UserId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`LogEntry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`LogEntry` (
 `PK_LogEntryId` int(11) NOT NULL AUTO_INCREMENT,
  `FK_CustomerId` int(11) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `CommentField` varchar(450) NOT NULL,
  `CreatedBy` int(11) NOT NULL,
  `LastEditedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LastEditedBy` int(11) NOT NULL,
  `Description` varchar(150) NOT NULL,
  PRIMARY KEY (`PK_LogEntryId`),
  KEY `LogEntry_Customer_idx` (`FK_CustomerId`),
  KEY `LogEntry.CreatedBy_User_idx` (`CreatedBy`),
  KEY `LogEntry.EditBy_User_idx` (`LastEditedBy`),
  CONSTRAINT `LogEntry.CreatedBy_User` FOREIGN KEY (`CreatedBy`) REFERENCES `User` (`PK_UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `LogEntry.EditBy_User` FOREIGN KEY (`LastEditedBy`) REFERENCES `User` (`PK_UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `LogEntry_Customer` FOREIGN KEY (`FK_CustomerId`) REFERENCES `Customer` (`PK_CustomerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`File`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`File` (
  `PK_FileId` INT(11) NOT NULL AUTO_INCREMENT,
  `FileReference` VARCHAR(150) NULL DEFAULT NULL,
  `FK_LogEntryId` INT(11) NOT NULL,
  PRIMARY KEY (`PK_FileId`),
  UNIQUE INDEX `FK_LogEntryId_UNIQUE` (`FK_LogEntryId` ASC),
  CONSTRAINT `File_LogEntry`
    FOREIGN KEY (`FK_LogEntryId`)
    REFERENCES `2nd_semesters`.`LogEntry` (`PK_LogEntryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`login_attempt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`login_attempt` (
  `PK_login_attemptId` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(150) NOT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT NULL,
  `ip` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_login_attemptId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2nd_semesters`.`successful_login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2nd_semesters`.`successful_login` (
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FK_UserId` INT(11) NOT NULL,
  PRIMARY KEY (`FK_UserId`, `timestamp`),
  CONSTRAINT `FK_UserId`
    FOREIGN KEY (`FK_UserId`)
    REFERENCES `2nd_semesters`.`User` (`PK_UserId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `2nd_semesters` ;

-- -----------------------------------------------------
-- procedure attemptLogin
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `attemptLogin`(IN triedUsername VARCHAR(150), IN pass VARCHAR(150))
BEGIN
DECLARE tmpId INT;

SELECT `PK_Userid`, `username`, `Name`,  `userType` FROM `2nd_semesters`.`User`
WHERE `hash`=(SELECT SHA2(CONCAT((SELECT `salt` FROM `User` WHERE `username`=triedUsername), pass), 512));

INSERT INTO `2nd_semesters`.`login_attempt` VALUES (default, triedUsername, NOW(), (SELECT SUBSTRING_INDEX(host, ':', 1) as 'ip' FROM information_schema.processlist WHERE ID=connection_id()));
SET tmpId = ((SELECT PK_UserId FROM `2nd_semesters`.`User` WHERE username = triedUsername AND `hash`=(SELECT SHA2(CONCAT((SELECT `salt` FROM `User` WHERE username=triedUsername), pass), 512))));
IF tmpId > 0 THEN INSERT INTO `2nd_semesters`.`successful_login` VALUES (NOW(), tmpId);
END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure createUser
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `createUser`(IN inUserName VARCHAR(150), IN inPassword VARCHAR(150), IN inSalt CHAR(32), IN inRealName VARCHAR(150), IN inUserType INT(11))
BEGIN
	INSERT INTO `2nd_semesters`.`User` VALUES (default, inUserName, (SELECT SHA2(CONCAT(inSalt, inPassword), 512)), inSalt, inRealName, inUserType);
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure deleteFileReference
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `deleteFileReference`(IN logEntryId int)
BEGIN

DELETE FROM `2nd_semesters`.`File`
WHERE `FK_LogEntryId` = logEntryId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure deleteLogEntry
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `deleteLogEntry`(IN logEntryId int)
BEGIN
DELETE FROM `2nd_semesters`.`LogEntry`
WHERE `PK_LogEntryId` = logEntryId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure editFileReference
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `editFileReference`(IN fileReference VARCHAR(150), IN logEntryId INT)
BEGIN
UPDATE `2nd_semesters`.`File`
SET `FileReference` = fileReference
WHERE `2nd_semesters`.`File`.`FK_LogEntryId` = logEntryId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure editLogEntry
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `editLogEntry`(IN logEntryId int, IN inputComment VARCHAR(150), IN lastEditDate TIMESTAMP, IN lastEditBy int, IN description VARCHAR(150))
BEGIN

UPDATE LogEntry
SET
CommentField = inputComment,
LastEditedDate = lastEditDate,
LastEditedBy = lastEditBy,
Description = description

WHERE LogEntry.PK_LogEntryId = logEntryId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getFileReference
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `getFileReference`(IN logEntryId int)
BEGIN

SELECT
F.FileReference
From File F
Where F.FK_LogEntryId = logEntryId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getLogEntryList
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `getLogEntryList`(IN customerId int)
BEGIN
SELECT
L.`PK_LogEntryId`,
L.`CreatedDate`,
U1.`Name`,
L.`LastEditedDate`,
U.`Name`,
L.`Description`,
L.`CommentField`,
F.`FileReference`
FROM
LogEntry L
LEFT JOIN `File` F
on F.FK_LogEntryId = L.PK_LogEntryId
LEFT JOIN `User` U
on U.PK_UserId = L.`LastEditedBy`
LEFT JOIN `User` U1 -- need to be unique for each join we make into the table
on U1.PK_UserId = L.`CreatedBy`
Where L.FK_CustomerId = customerId;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure modifyUser
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `modifyUser`(IN inPK_UserId INT(11),IN inUserName VARCHAR(150), IN inPassword VARCHAR(150), IN inSalt CHAR(32), IN inRealName VARCHAR(150), IN inUserType VARCHAR(45))
BEGIN
	UPDATE `2nd_semesters`.`User` SET `username` = inUserName,`hash` = (SELECT SHA2(CONCAT(inSalt, inPassword), 512)), `salt` = inSalt, `Name` = inRealName, `userType` = inUserType WHERE `PK_UserId` = inPK_UserId; 
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure newLogEntry
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `newLogEntry`( IN customerId INTEGER, IN createdDate DATETIME, IN inputComment VARCHAR(150), IN createdBy VARCHAR(150), IN lastEditDate DATETIME, IN lastEditBy VARCHAR(150), IN description VARCHAR(150), OUT out_LogEntryId Int)
BEGIN

INSERT INTO LogEntry ( FK_CustomerId, CreatedDate, CommentField, CreatedBy, LastEditedDate, LastEditedBy, Description) VALUES (customerId, createdDate, inputComment, createdBy, lastEditDate, lastEditBy, description);

SET out_LogEntryId =  LAST_INSERT_ID();
SELECT out_LogEntryId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure nullifySalt
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `nullifySalt`(In inPK_UserId INT(11))
BEGIN
	UPDATE `2nd_semesters`.`User` SET `salt` = '00000000000000000000000000000000', `hash` = '00000000000000000000000000000000' WHERE `PK_UserId` = inPK_UserId;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveAdditionalCustomerInfo
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `retrieveAdditionalCustomerInfo`(IN search INT(8))
BEGIN
SELECT
A.AccountNumber,
A.RegistrationNumber,
A.AccountType,
Ad.StreetName,
Ad.StreetNumber,
Ad.Floor,
Ad.ApartmentNumberOrSide,
Ad.CO,
Ad.PostalArea,
Ad.PostalCode,
Ad.City,
Ad.Country,
Co.CVR,
Co.CompanyName,
E.Email,
P.Phone,
MP.Mobile_phone
FROM Customer C
left join CustomerAccount CA
on CA.FK_CustomerId = C.PK_CustomerId 
left join Account A
on A.PK_AccountId = CA.FK_AccountId 
left join CustomerAddress CAd
on CAd.FK_CustomerId = C.PK_CustomerId 
left join Address Ad
on Ad.PK_AddressId = CAd.FK_AddressId
left join CustomerCompany CC
on CC.FK_CustomerId = C.PK_CustomerId 
left join Company Co
on Co.PK_CompanyId = CC.FK_CompanyId
left join CustomerEmail CE
on CE.FK_CustomerId = C.PK_CustomerId
left join Email E
on E.PK_EmailId = CE.FK_EmailId
left join CustomerPhone CP
on CP.FK_CustomerId = C.PK_CustomerId
left join Phone P
on P.PK_PhoneId = CP.FK_PhoneId
left join CustomerMobile_phone CMP
on CMP.FK_CustomerId = C.PK_CustomerId
left join Mobile_phone MP
on MP.PK_Mobile_phoneId = CMP.FK_Mobile_phoneId


WHERE search = C.PK_CustomerId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure saveFileReference
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `saveFileReference`(IN fileReference VARCHAR(150), IN logEntryId INT)
BEGIN
INSERT INTO File (FileReference, fk_LogEntryId) VALUES (fileReference,logEntryId);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure searchCustomer
-- -----------------------------------------------------

DELIMITER $$
USE `2nd_semesters`$$
CREATE DEFINER=`matti`@`%` PROCEDURE `searchCustomer`(IN search CHAR(1))
BEGIN

SELECT
C.PK_CustomerId,
C.FirstName,
C.LastName,
C.CPR
FROM Customer C

WHERE C.LastName LIKE CONCAT(search,"%") OR
C.FirstName LIKE CONCAT(search,"%")  OR
C.CPR LIKE CONCAT(search,"%") OR
CAST(C.PK_CustomerId as CHAR) LIKE CONCAT(search,"%")
GROUP BY C.PK_CustomerId
ORDER BY C.LastName ASC;

END$$

DELIMITER ;

-- ----------------------------------------------------
-- Insert the two users 
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`User` VALUES (default, 'plasma', '7e5842c70b0bcc098cb0b524c046aec07c173b4022366c645ca26f1d819d7b0c41d60fa20d86004ccc1f81391b8a4a2d3b863a971330dc2e8f78e2d1df367f08', 'pZXAesuAreBBPV7I1GvlFph8Ij6bVN4n', 'Plasma Amelund', 0);
INSERT INTO `2nd_semesters`.`User` VALUES (default, 'admin', 'acf12552aa70e93d88334378c706d8bfb2bbea00082a02929692a662deae8091473a7fd3113c3c4343c55fb59180995d34daa642cea08108b99510ff6b9eddae', 'XGZ3f7marCP41RWZgSNrShdbU25O20lL', 'Maximillian Syfax', 1);


-- ----------------------------------------------------
-- Insert demo customers - Backstreet Boys and Spice Girls
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('1', 'Alexander James', 'McLean', '090178-1337');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('2', 'Howard Dwaine', 'Dorough', '220873-1337');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('3', 'Nickolas Gene', 'Carter', '280180-1337');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('4', 'Kevin Scott', 'Richardson', '031071-1337');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('5', 'Brian Thomas', 'Littrell', '200275-1337');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('6', 'Victoria Caroline', 'Beckham', '170474-3306');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('7', 'Melanie Janine', 'Brown', '290575-3306');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('8', 'Emma Lee', 'Bunton', '210176-3306');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('9', 'Melanie Jayne', 'Chisholm', '120174-3306');
INSERT INTO `2nd_semesters`.`Customer` (`PK_CustomerId`, `FirstName`, `LastName`, `CPR`) VALUES ('10', 'Geraldine Estelle', 'Halliwell', '060872-3306');

-- ----------------------------------------------------
-- Insert demo addresses
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('1', 'Backstreet', '1', 'SOHO', '1337', 'London', 'England');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('2', 'Backstreet', '2', 'SOHO', '1337', 'London', 'England');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `Floor`, `ApartmentNumberOrSide`, `CO`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('3', 'FrontStreet', '17', '3', '34', 'Westlife', 'Shepherds Bush', '1214', 'London', 'England');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `Floor`, `ApartmentNumberOrSide`, `CO`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('4', 'Oxford Circus', '10', '1', '1', 'Wham', 'Oxford Circus', '3423', 'London', 'England');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `Floor`, `ApartmentNumberOrSide`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('5', 'Storegade', '2', '4', '12', '', '6880', 'Tarm', 'Danmark');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('6', 'Magstræde', '42', 'KBH K', '1260', 'KBH K', 'Danmark');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `Floor`, `ApartmentNumberOrSide`, `CO`, `PostalCode`, `City`, `Country`) VALUES ('7', 'Studiestræde', '6', '2', '2', 'Cosy', '1271', 'KBH K', 'Danmark');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalCode`, `City`, `Country`) VALUES ('8', 'Scheißestraße', '69', '0945', 'Berlin', 'DeutschLand');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalArea`, `PostalCode`, `City`, `Country`) VALUES ('9', 'Vaniljevej', '5', 'Paradis', '1337', 'Tønder', 'Danmark');
INSERT INTO `2nd_semesters`.`Address` (`PK_AddressId`, `StreetName`, `StreetNumber`, `PostalCode`, `City`) VALUES ('10', 'Englandsvej', '90', '2300', 'KBH S');

-- ----------------------------------------------------
-- Insert links between customers and adresses
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerAddress` (`FK_CustomerId`, `FK_AddressId`) VALUES ('10', '10');

-- ----------------------------------------------------
-- Insert Email 
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('1', 'aj@bsb.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('2', 'howie@bsb.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('3', 'nickcarter@bsb.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('4', 'kevin@bsb.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('5', 'bl@bsb.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('6', 'vicbeckham@spicegirls.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('7', 'melb@spicegirls.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('8', 'emmasbuns@spicegirl.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('9', 'melaniec@spicegirls.com');
INSERT INTO `2nd_semesters`.`Email` (`PK_EmailId`, `Email`) VALUES ('10', 'itsrainingmen@spicegirl.com');


-- ----------------------------------------------------
-- Insert links between customers and emails
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerEmail` (`FK_EmailId`, `FK_CustomerId`) VALUES ('10', '10');

-- ----------------------------------------------------
-- Insert phone
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('1', '20345678');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('2', '86758493');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('3', '45385768');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('4', '16274850');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('5', '99574836');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('6', '64758520');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('7', '18471042');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('8', '12164746');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('9', '14619468');
INSERT INTO `2nd_semesters`.`Phone` (`PK_PhoneId`, `Phone`) VALUES ('10', '16473849');

-- ----------------------------------------------------
-- Insert links between customers and phones
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerPhone` (`FK_PhoneId`, `FK_CustomerId`) VALUES ('10', '10');


-- ----------------------------------------------------
-- Insert mobile phones
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('1', '95467483');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('2', '81710401');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('3', '42741010');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('4', '42710140');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('5', '47210971');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('6', '47171040');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('7', '42184121');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('8', '21401247');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('9', '32810932');
INSERT INTO `2nd_semesters`.`Mobile_phone` (`PK_Mobile_phoneId`, `Mobile_phone`) VALUES ('10', '12831238');


-- ----------------------------------------------------
-- Insert links between customers and mobile phones
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerMobile_phone` (`FK_CustomerId`, `FK_Mobile_phoneId`) VALUES ('10', '10');


-- ----------------------------------------------------
-- Insert Accounts
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('1', '467381', '0400', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('2', '112321', '9620', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('3', '1412151', '0440', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('4', '132141', '7000', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('5', '34700141', '5602', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('6', '1412167', '3405', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('7', '3470057', '4205', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('8', '1417511', '2354', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('9', '658479', '5460', 'Privat');
INSERT INTO `2nd_semesters`.`Account` (`PK_AccountId`, `AccountNumber`, `RegistrationNumber`, `AccountType`) VALUES ('10', '144125151', '2142', 'Privat');


-- ----------------------------------------------------
-- Insert links between customers and account
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerAccount` (`FK_AccountId`, `FK_CustomerId`) VALUES ('10', '10');


-- ----------------------------------------------------
-- Insert companies
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('1', '10304784	', 'Hell');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('2', '10204627', 'Monki');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('3', '25768190', 'SpiceUpYourLife');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('4', '657591', 'Back Alley Barber');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('5', '1414151', 'wagamama');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('6', '5161611', 'Expensive Monday');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('7', '86960502', 'Crappy Music inc.');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('8', '8594940', 'We dance, we lose');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('9', '9147971', '7-eleven');
INSERT INTO `2nd_semesters`.`Company` (`PK_CompanyId`, `CVR`, `CompanyName`) VALUES ('10', '0606064', 'Medley Records');

-- ----------------------------------------------------
-- Insert links between companies and customers
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('1', '1');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('2', '2');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('3', '3');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('4', '4');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('5', '5');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('6', '6');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('7', '7');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('8', '8');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('9', '9');
INSERT INTO `2nd_semesters`.`CustomerCompany` (`FK_CompanyId`, `FK_CustomerId`) VALUES ('10', '10');


-- ----------------------------------------------------
-- Insert Logentries
-- ----------------------------------------------------
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('1', '1', '2014-05-26 03:11:16', 'No Comment', '1', '2', 'andet');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('2', '1', '2014-05-26 03:11:16', 'Bought a new record', '1', '1', 'andet');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('3', '1', '2014-05-26 03:11:16', 'Vandt melodigrandprix', '2', '2', 'Jeg er jo conchita');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('4', '2', '2014-05-26 03:11:16', ' ', '1', '1', 'andet');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('5', '3', '2014-05-26 03:11:16', ' ', '2', '1', 'andet');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('6', '4', '2014-05-26 03:11:16', 'Ingen kommentarer', '1', '2', 'andet');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('7', '5', '2014-05-26 03:11:16', 'Masser af penge her', '1', '1', 'Vi lænser ham');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('8', '7', '2014-05-26 03:11:16', ' ', '1', '2', 'Smed sit dankort væk');
INSERT INTO `2nd_semesters`.`LogEntry` (`PK_LogEntryId`, `FK_CustomerId`, `CreatedDate`, `CommentField`, `CreatedBy`, `LastEditedBy`, `Description`) VALUES ('9', '9', '2014-05-26 03:11:16', 'Spice up your life', '1', '1', 'med dans');











GRANT ALL PRIVILEGES ON `2nd_semesters`.* TO `matti`@`%` WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON `2nd_semesters`.* TO `matti`@`localhost` IDENTIFIED BY 'vn4Nh4XAGXvFsZ' WITH GRANT OPTION;

GRANT SELECT ON `2nd_semesters`.`User` TO `loginuser`@`%`;
GRANT INSERT ON `2nd_semesters`.`login_attempt` TO `loginuser`@`%`;
GRANT INSERT ON `2nd_semesters`.`successful_login` TO `loginuser`@`%`;
GRANT EXECUTE ON PROCEDURE `2nd_semesters`.`attemptLogin` TO `loginuser`@`%`;

GRANT SELECT ON `2nd_semesters`.`User` TO `loginuser`@`localhost` IDENTIFIED BY 'pass';
GRANT INSERT ON `2nd_semesters`.`login_attempt` TO `loginuser`@`localhost`;
GRANT INSERT ON `2nd_semesters`.`successful_login` TO `loginuser`@`localhost`;
GRANT EXECUTE ON PROCEDURE `2nd_semesters`.`attemptLogin` TO `loginuser`@`localhost`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

