/*
SQLyog Community v13.1.2 (64 bit)
MySQL - 10.1.38-MariaDB-0+deb9u1 : Database - JAVAFINAL
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`JAVAFINAL` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `JAVAFINAL`;

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `Pro_num` INT(11) NOT NULL,
  `Stu_num` INT(11) NOT NULL,
  `aDate` DATE NOT NULL,
  `aTime` TIME DEFAULT NULL,
  `attend` VARCHAR(2) DEFAULT NULL,
  PRIMARY KEY (`Pro_num`,`aDate`),
  KEY `Stu_num` (`Stu_num`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`Pro_num`) REFERENCES `project` (`Pro_num`) ON DELETE CASCADE,
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`Stu_num`) REFERENCES `student` (`Stu_num`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `attendance` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `Log_ID` VARCHAR(12) NOT NULL,
  `Log_PW` VARCHAR(12) NOT NULL,
  `Log_name` VARCHAR(10) NOT NULL,
  `Log_birth` DATE DEFAULT NULL,
  `Log_tel` VARCHAR(13) DEFAULT NULL,
  PRIMARY KEY (`Log_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `login` */

/*Table structure for table `professor` */

DROP TABLE IF EXISTS `professor`;

CREATE TABLE `professor` (
  `Pro_num` INT(11) NOT NULL AUTO_INCREMENT,
  `Pro_ID` VARCHAR(12) DEFAULT NULL,
  PRIMARY KEY (`Pro_num`),
  KEY `Pro_ID` (`Pro_ID`),
  CONSTRAINT `professor_ibfk_1` FOREIGN KEY (`Pro_ID`) REFERENCES `login` (`Log_ID`) ON DELETE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

/*Data for the table `professor` */

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `Pro_num` INT(11) NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(1) DEFAULT NULL,
  `lTime` INT(11) DEFAULT NULL,
  `Sub_num` INT(11) DEFAULT NULL,
  PRIMARY KEY (`Pro_num`),
  KEY `Sub_num` (`Sub_num`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`Sub_num`) REFERENCES `subject` (`Sub_num`) ON DELETE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

/*Data for the table `project` */

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `Stu_num` INT(11) NOT NULL,
  `Stu_grd` INT(11) DEFAULT NULL,
  `Stu_name` VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (`Stu_num`),
  KEY `Stu_grd` (`Stu_grd`),
  KEY `Stu_name` (`Stu_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `Sub_num` INT(11) NOT NULL,
  `Sub_name` VARCHAR(12) DEFAULT NULL,
  `Pro_num` INT(11) DEFAULT NULL,
  PRIMARY KEY (`Sub_num`),
  KEY `Pro_num` (`Pro_num`),
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`Pro_num`) REFERENCES `professor` (`Pro_num`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Data for the table `subject` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
