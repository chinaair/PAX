CREATE DATABASE  IF NOT EXISTS `chinaair_pax` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `chinaair_pax`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: chinaair_pax
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `LOCATION` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `TYPE` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VAT_CODE` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FAX` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEPOSIT_TYPE` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEPOSIT_AMT` decimal(11,2) DEFAULT NULL,
  `VALID_DATE` date DEFAULT NULL,
  `RETAILFLAG` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` (`ID`, `CODE`, `LOCATION`, `TYPE`, `NAME`, `COMPANY`, `VAT_CODE`, `PHONE`, `FAX`, `ADDRESS`, `EMAIL`, `DEPOSIT_TYPE`, `DEPOSIT_AMT`, `VALID_DATE`, `RETAILFLAG`) VALUES (1,'371-1234','0','0','Agent 1','company 11','03058484','123456','4562347','Hồ chí minh','acb@mail.vn','0',3000.00,'2014-05-31',NULL),(2,'371-2146','0','0','HOÀNG TRÀ','CÔNG TY DU LỊCH HOÀNG TRÀ alibab cana  xi muoi cahng biet go cai gi vao them cho no dai nua, kho suy nghi qua di','0301','','','','','0',NULL,'2018-06-05',NULL),(3,'371-4444','0','1','CTY A Thong','Cong ty THNN A Thong','0526262','126262','1516262','fdhsdfgbsf','davsdg','0',NULL,'2014-08-02',NULL),(4,'371-5262','0','0','HihiHhaha','cty hihihaha','0894595','26262','262626','2612626','fdghdf','0',NULL,'2014-08-02',NULL),(5,'371-8545','0','0','cococ','cty cococ','0959226','1262626','9962262','dsgsfdh','sfbgsdb','0',NULL,'2014-08-02',NULL),(6,'371-5265','1','0','jojo','cty jojo','0959595','959262','9490626','dsgsfg','xdfhdf','0',NULL,'2014-08-02',NULL),(7,'371-5622','0','0','AAAA','AAAA','00596560','','','','','0',NULL,'2014-08-03',NULL),(8,'371-5547','0','0','BBBB','BBBB','0595092','','','','','0',NULL,'2014-08-03',NULL),(9,'371-4512','0','0','CCCC','CCCC','09505252','','','','','0',NULL,'2014-08-03',NULL),(10,'371-2929','0','0','DDDD','DDDD','90590596','','','','','0',NULL,'2014-08-03',NULL),(11,'371-9521','0','0','EEEE','EEEE','41562162','','','','','0',NULL,'2014-08-03',NULL),(12,'371-1801','0','0','FFFF','FFFF','','','','','','0',NULL,'2014-08-03',NULL),(13,'371-1959','0','0','GGGG','GGGG','','','','','','0',NULL,'2014-08-03',NULL),(14,'371-8929','0','0','HHHH','HHHH','','','','','','0',NULL,'2014-08-03',NULL),(15,'371-1888','0','0','IIII','IIII','','','','','','0',NULL,'2014-08-03',NULL),(16,'371-2928','0','0','JJJJ','JJJJ','','','','','','0',NULL,'2014-08-03',NULL),(17,'371-3318','0','0','KKKK','KKKK','','','','','','0',NULL,'2014-08-03',NULL),(18,'371-4841','0','0','LLLL','LLLL','','','','','','0',NULL,'2014-08-03',NULL),(19,'371-5487','0','0','MMMM','MMMM','','','','','','0',NULL,'2014-08-03',NULL),(20,'371-9411','0','0','NNNN','NNNN','','','','','','0',NULL,'2014-08-03',NULL),(21,'371-2894','0','0','OOOO','OOOO','','','','','','0',NULL,'2014-08-03',NULL),(22,'371-6494','0','0','PPPP','PPPP','','','','','','0',NULL,'2014-08-03',NULL),(23,'371-3491','0','0','QQQQ','QQQQ','','','','','','0',NULL,'2014-08-03',NULL),(24,'371-7189','0','0','RRRR','RRRR','','','','','','0',NULL,'2014-08-03',NULL),(25,'371-7192','0','0','TTTT','TTTT','','','','','','0',NULL,'2014-08-03',NULL),(26,'371-7777','0','0','sdfas','sdfgs','','','','','','0',NULL,'2014-08-27',NULL),(27,'371-1234','1','0','ta day','cty ta day','','','','','','0',NULL,'2014-08-25',NULL),(28,'371-9999','0','0','Khach le','An binh','','','','','','0',NULL,'2014-11-26','1');
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `CODE` char(4) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` (`CODE`, `NAME`, `LASTUPDATE`) VALUES ('0001','Ticket issue editable','2014-08-03 13:50:58'),('0002','Tax invoice editable','2014-08-03 13:50:58');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMPANY` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `PHONE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FAX` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VAT` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`ID`, `COMPANY`, `PHONE`, `FAX`, `ADDRESS`, `EMAIL`, `VAT`) VALUES (1,'CÔNG TY DU LỊCH HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ','987654321','','123 NTMK nay thi dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di','','0301121321'),(2,'Cong ty THNN A Thong',NULL,NULL,'fdhsdfgbsf',NULL,'0526262111');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`ID`, `NAME`) VALUES (1,'ngoc');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_ticket_journal`
--

DROP TABLE IF EXISTS `daily_ticket_journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_ticket_journal` (
  `PBSR_DATE` date NOT NULL,
  `TOTAL_AMT_USD` decimal(11,2) NOT NULL,
  `TOTAL_AMT_VND` decimal(11,0) DEFAULT NULL,
  `CLOSE_EMP_ID` bigint(20) DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`PBSR_DATE`),
  UNIQUE KEY `ID_UNIQUE` (`PBSR_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_ticket_journal`
--

LOCK TABLES `daily_ticket_journal` WRITE;
/*!40000 ALTER TABLE `daily_ticket_journal` DISABLE KEYS */;
INSERT INTO `daily_ticket_journal` (`PBSR_DATE`, `TOTAL_AMT_USD`, `TOTAL_AMT_VND`, `CLOSE_EMP_ID`, `STATUS`) VALUES ('2014-06-05',6320.00,134206000,NULL,'0'),('2014-06-07',642.00,13559000,NULL,'0'),('2014-06-12',224.00,4731000,NULL,'1'),('2014-06-21',2421.00,51131000,NULL,'1'),('2014-07-27',2400.00,51216000,4,'1'),('2014-09-10',141.00,2998000,1,'1');
/*!40000 ALTER TABLE `daily_ticket_journal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMPLOYEENAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `POSITION` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `USERID` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `AUTHORITY` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TAXCODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `USAGEFLAG` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `USERID_UNIQUE` (`USERID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`ID`, `EMPLOYEENAME`, `POSITION`, `USERID`, `PASSWORD`, `AUTHORITY`, `TAXCODE`, `USAGEFLAG`) VALUES (1,'TRAN THI HUYEN TRANG','1','admin','123','5,6,11,12,13,14,15,16,17,18,19,20,21,22,23','123','1'),(3,'User 1','','user','123','11,12,13,14,15,16,17,18,19,20,21,22','','1'),(4,'John Smith','administrator','john','123','5,6,11,12,13,14,15,16,17,18,19,20,21,22','123456','1');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_authority`
--

DROP TABLE IF EXISTS `employee_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_authority` (
  `EMP_ID` bigint(20) NOT NULL,
  `AUTHOR_CODE` char(4) COLLATE utf8_unicode_ci NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`EMP_ID`,`AUTHOR_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_authority`
--

LOCK TABLES `employee_authority` WRITE;
/*!40000 ALTER TABLE `employee_authority` DISABLE KEYS */;
INSERT INTO `employee_authority` (`EMP_ID`, `AUTHOR_CODE`, `LASTUPDATE`) VALUES (1,'0001','2014-08-03 15:40:12'),(1,'0002','2014-08-03 15:40:12');
/*!40000 ALTER TABLE `employee_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_stock`
--

DROP TABLE IF EXISTS `invoice_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_stock` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` date NOT NULL,
  `INVOICE_CODE_ID` bigint(20) NOT NULL,
  `PREFIX` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `STARTNO` bigint(20) NOT NULL,
  `QUANTITY` bigint(20) NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CARGO` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_stock`
--

LOCK TABLES `invoice_stock` WRITE;
/*!40000 ALTER TABLE `invoice_stock` DISABLE KEYS */;
INSERT INTO `invoice_stock` (`ID`, `CREATE_DATE`, `INVOICE_CODE_ID`, `PREFIX`, `STARTNO`, `QUANTITY`, `LASTUPDATE`, `CARGO`) VALUES (1,'2014-05-31',1,'A',1,30,'2014-05-31 04:49:03',NULL),(2,'2014-06-12',3,'13',1,20,'2014-06-12 16:14:07',NULL),(3,'2014-08-14',3,'JO',1,20,'2014-08-14 16:06:46','1');
/*!40000 ALTER TABLE `invoice_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_stock_detail`
--

DROP TABLE IF EXISTS `invoice_stock_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_stock_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INVOICE_STOCK_ID` bigint(20) DEFAULT NULL,
  `INVOICE_STOCK_NO` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `STATUS` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_stock_detail`
--

LOCK TABLES `invoice_stock_detail` WRITE;
/*!40000 ALTER TABLE `invoice_stock_detail` DISABLE KEYS */;
INSERT INTO `invoice_stock_detail` (`ID`, `INVOICE_STOCK_ID`, `INVOICE_STOCK_NO`, `STATUS`, `LASTUPDATE`) VALUES (1,1,'A0023','0','2014-08-07 15:59:04'),(2,1,'A0026','0','2014-05-31 04:49:03'),(3,1,'A0012','0','2014-05-31 04:49:03'),(4,1,'A0029','0','2014-05-31 04:49:03'),(5,1,'A0018','0','2014-05-31 04:49:03'),(6,1,'A0015','0','2014-05-31 04:49:03'),(7,1,'A0007','2','2014-06-12 16:10:59'),(8,1,'A0009','0','2014-05-31 04:49:03'),(9,1,'A0003','2','2014-06-07 05:38:04'),(10,1,'A0005','2','2014-06-05 06:31:28'),(11,1,'A0002','2','2014-06-07 05:38:28'),(12,1,'A0028','0','2014-05-31 04:49:03'),(13,1,'A0013','0','2014-05-31 04:49:03'),(14,1,'A0011','0','2014-05-31 04:49:03'),(15,1,'A0016','0','2014-05-31 04:49:03'),(16,1,'A0030','0','2014-05-31 04:49:03'),(17,1,'A0021','0','2014-05-31 04:49:03'),(18,1,'A0024','0','2014-05-31 04:49:03'),(19,1,'A0014','0','2014-05-31 04:49:03'),(20,1,'A0006','2','2014-06-12 16:10:52'),(21,1,'A0004','2','2014-06-07 05:20:27'),(22,1,'A0017','0','2014-05-31 04:49:03'),(23,1,'A0025','0','2014-05-31 04:49:03'),(24,1,'A0010','0','2014-05-31 04:49:03'),(25,1,'A0020','0','2014-05-31 04:49:03'),(26,1,'A0027','0','2014-05-31 04:49:03'),(27,1,'A0001','1','2014-05-31 04:57:51'),(28,1,'A0022','0','2014-05-31 04:49:03'),(29,1,'A0019','0','2014-05-31 04:49:03'),(30,1,'A0008','2','2014-06-07 05:37:48'),(31,2,'130014','1','2014-09-10 15:35:35'),(32,2,'130012','1','2014-09-10 15:35:35'),(33,2,'130019','1','2015-01-14 16:18:58'),(34,2,'130005','1','2014-07-31 15:48:55'),(35,2,'130009','2','2014-08-20 15:45:34'),(36,2,'130004','1','2014-07-27 06:24:35'),(37,2,'130010','1','2014-09-02 04:59:18'),(38,2,'130007','1','2014-08-11 16:41:37'),(39,2,'130020','1','2015-03-29 05:37:49'),(40,2,'130008','1','2014-08-11 16:41:37'),(41,2,'130016','1','2014-10-19 17:24:26'),(42,2,'130018','1','2014-11-10 14:19:58'),(43,2,'130017','1','2014-10-21 17:12:22'),(44,2,'130006','1','2014-08-11 16:41:37'),(45,2,'130001','1','2014-06-12 16:15:13'),(46,2,'130013','2','2014-09-10 15:36:19'),(47,2,'130003','1','2014-08-12 17:42:57'),(48,2,'130002','1','2014-06-27 16:59:47'),(49,2,'130011','1','2014-09-08 17:42:19'),(50,2,'130015','2','2014-10-18 16:42:57'),(51,3,'JO0004','1','2014-09-02 04:59:18'),(52,3,'JO0020','0','2014-08-14 16:06:46'),(53,3,'JO0009','0','2014-08-14 16:06:46'),(54,3,'JO0007','0','2014-08-14 16:06:46'),(55,3,'JO0002','1','2014-09-02 04:59:18'),(56,3,'JO0006','0','2014-08-14 16:06:46'),(57,3,'JO0011','0','2014-08-14 16:06:46'),(58,3,'JO0016','0','2014-08-14 16:06:46'),(59,3,'JO0014','0','2014-08-14 16:06:46'),(60,3,'JO0015','0','2014-08-14 16:06:46'),(61,3,'JO0008','0','2014-08-14 16:06:46'),(62,3,'JO0010','0','2014-08-14 16:06:46'),(63,3,'JO0019','0','2014-08-14 16:06:46'),(64,3,'JO0005','0','2014-08-14 16:06:46'),(65,3,'JO0003','1','2014-09-02 04:59:18'),(66,3,'JO0018','0','2014-08-14 16:06:46'),(67,3,'JO0017','0','2014-08-14 16:06:46'),(68,3,'JO0001','1','2014-08-31 06:49:40'),(69,3,'JO0013','0','2014-08-14 16:06:46'),(70,3,'JO0012','0','2014-08-14 16:06:46');
/*!40000 ALTER TABLE `invoice_stock_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master`
--

DROP TABLE IF EXISTS `master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master` (
  `MASTERNO` char(4) COLLATE utf8_unicode_ci NOT NULL,
  `MASTERPARENT` char(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VALUE` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEVEL` int(11) DEFAULT NULL,
  PRIMARY KEY (`MASTERNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master`
--

LOCK TABLES `master` WRITE;
/*!40000 ALTER TABLE `master` DISABLE KEYS */;
INSERT INTO `master` (`MASTERNO`, `MASTERPARENT`, `NAME`, `VALUE`, `LEVEL`) VALUES ('0001','0000','address','37 Ton Duc Thang, Dist. 1 Ho Chi Minh city',NULL),('0002','0000','tel','84-8-9111584',NULL),('0003','0000','fax','84-8-9111596',NULL),('0004','0000','accManagerName','MA CHENG I',NULL),('0005','0001','system','CI',NULL),('0006','0001','rss','http://vnexpress.net/rss/tin-moi-nhat.rss',NULL),('0007','0001','googleCalendar','https://www.google.com/calendar/embed?title=China%20Airlines%20Calendar&amp;showTitle=0&amp;showPrint=0&amp;showCalendars=0&amp;height=600&amp;wkst=2&amp;bgcolor=%23ffffff&amp;src=1jr73qsepe7ss35q2j37lbsd8g%40group.calendar.google.com&amp;color=%232952A3&amp;ctz=Asia%2FBangkok;',NULL),('0008','0001','taxName','CHINA AIRLINES LTD A',NULL),('0009','0001','taxNo','0301485277',NULL);
/*!40000 ALTER TABLE `master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MENU` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `MENU_VI` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEVEL` int(11) NOT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `USEFLAG` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LINK` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ICON` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `MENU_UNIQUE` (`MENU`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`ID`, `MENU`, `MENU_VI`, `LEVEL`, `PARENTID`, `USEFLAG`, `LINK`, `ICON`) VALUES (0,'HOME','TRANG CHỦ',1,NULL,'1',NULL,NULL),(1,'INPUT','FORM NHẬP',1,NULL,'1',NULL,NULL),(2,'REPORT','BÁO CÁO',1,NULL,'1',NULL,NULL),(3,'CATELOGY','DANH MỤC',1,NULL,'1',NULL,NULL),(4,'SYSTEM','HỆ THỐNG',1,NULL,'1',NULL,NULL),(5,'Employee','Quản lý nhân viên',2,4,'1','EmployeeListScreen.xhtml','ui-icon-gear'),(6,'Menu','Danh mục menu',2,4,'1','MenuListScreen.xhtml','ui-icon-gear'),(11,'Ticket/Cash Receipt','Ticket/Cash Receipt',2,1,'1','TicketIssueListScreen.xhtml','ui-icon-arrowthickstop-1-s'),(12,'Issue Tax Invoice (Pax)','Issue Tax Invoice (Pax)',2,1,'1','TaxInvoiceIssueListScreen.xhtml','ui-icon-arrowthickstop-1-s'),(13,'Tax Invoice Stock (Pax)','Tax Invoice Stock (Pax)',2,1,'1','TaxInvoiceStock.xhtml','ui-icon-arrowthickstop-1-s'),(14,'Issue Tax Invoice (Cgo)','Issue Tax Invoice (Cgo)',2,1,'1','TaxInvoiceIssueListScreen.xhtml?cargo=true','ui-icon-arrowthickstop-1-s'),(15,'Tax Invoice Stock (Cgo)','Tax Invoice Stock (Cgo)',2,1,'1','TaxInvoiceStock.xhtml?cargo=true','ui-icon-arrowthickstop-1-s'),(16,'Generate PBSR','Generate PBSR',2,2,'1','CloseOpenPBSRScreen.xhtml','ui-icon-print'),(17,'Agent','Agent',2,3,'1','AgentListScreen.xhtml','ui-icon-note'),(18,'Exchange Rate','Exchange Rate',2,3,'1','InputRate.xhtml','ui-icon-note'),(19,'Company','Company',2,3,'1','CompanyListScreen.xhtml','ui-icon-note'),(20,'Tax Invoice Code','Tax Invoice Code',2,3,'1','TaxInvoiceCodeListScreen.xhtml','ui-icon-note'),(21,'Final Report','Final Report',2,2,'1','FinalReportListScreen.xhtml','ui-icon-print'),(22,'Tax Invoice List (Pax/Cgo)','Tax Invoice List (Pax/Cgo)',2,2,'1','TaxInvoiceReportScreen.xhtml','ui-icon-print'),(23,'Master Input','Master Input',2,4,'1','MasterInputScreen.xhtml','ui-icon-gear');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate`
--

DROP TABLE IF EXISTS `rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE` date NOT NULL,
  `RATE` decimal(11,0) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `DATE_UNIQUE` (`DATE`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate`
--

LOCK TABLES `rate` WRITE;
/*!40000 ALTER TABLE `rate` DISABLE KEYS */;
INSERT INTO `rate` (`ID`, `DATE`, `RATE`) VALUES (1,'2014-05-31',21034),(3,'2014-06-05',21120),(4,'2014-06-28',21340),(5,'2014-07-27',21340),(6,'2014-08-11',21333),(7,'2014-08-12',21260);
/*!40000 ALTER TABLE `rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_invoice_code`
--

DROP TABLE IF EXISTS `tax_invoice_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_invoice_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_invoice_code`
--

LOCK TABLES `tax_invoice_code` WRITE;
/*!40000 ALTER TABLE `tax_invoice_code` DISABLE KEYS */;
INSERT INTO `tax_invoice_code` (`ID`, `CODE`, `NAME`, `LASTUPDATE`) VALUES (3,'1234','name1234','2014-06-11 15:08:42'),(4,'1111','name1234','2014-06-11 15:14:12');
/*!40000 ALTER TABLE `tax_invoice_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_invoice_issue`
--

DROP TABLE IF EXISTS `tax_invoice_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_invoice_issue` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ISSUE_DATE` date NOT NULL,
  `TICKET_ISSUE_ID` bigint(20) DEFAULT NULL,
  `INVOICE_STOCK_DETAIL_ID` bigint(20) NOT NULL,
  `COMPANY_NAME` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VAT_CODE` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_TYPE` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `AMT_USD` decimal(14,2) NOT NULL,
  `RATE_ID` bigint(20) DEFAULT NULL,
  `CREATE_EMP_ID` bigint(20) DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ROUNDUP` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `CARGO` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `USE_MANUAL_RATE` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MANUAL_RATE` decimal(11,0) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_invoice_issue`
--

LOCK TABLES `tax_invoice_issue` WRITE;
/*!40000 ALTER TABLE `tax_invoice_issue` DISABLE KEYS */;
INSERT INTO `tax_invoice_issue` (`ID`, `ISSUE_DATE`, `TICKET_ISSUE_ID`, `INVOICE_STOCK_DETAIL_ID`, `COMPANY_NAME`, `VAT_CODE`, `ADDRESS`, `PAYMENT_TYPE`, `AMT_USD`, `RATE_ID`, `CREATE_EMP_ID`, `STATUS`, `LASTUPDATE`, `ROUNDUP`, `CARGO`, `USE_MANUAL_RATE`, `MANUAL_RATE`) VALUES (1,'2014-05-31',1,27,'company 1','03058484',' 台湾','0',246.00,1,NULL,'0','2014-05-31 04:57:51','1',NULL,NULL,NULL),(2,'2014-06-05',NULL,11,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301','nguyen thi minh khai q1','2',2900.00,2,NULL,'2','2014-06-07 05:38:28','1',NULL,NULL,NULL),(3,'2014-06-05',NULL,9,'company 1','03058484','Hồ chí minh','0',3420.00,2,NULL,'2','2014-06-07 05:38:04','1',NULL,NULL,NULL),(4,'2014-06-05',NULL,21,'CÔNG TY DU LỊCH HOÀNG tra','0301','Q.3','2',750.00,2,NULL,'2','2014-06-07 05:20:27','0',NULL,NULL,NULL),(5,'2014-06-05',NULL,10,'company 1','03058484','Hồ chí minh','0',2000.00,2,NULL,'2','2014-06-05 06:31:28','1',NULL,NULL,NULL),(6,'2014-06-05',NULL,20,'C«ng ty TNHH DÞch Vô Gi¸m §Þnh C¸c KÕt CÊu Hµn Kim Lo¹i Yeong Jaan','1100664655-001','Lô 117, khu công nghiệp trong khu chế xuất Sài Gòn- Linh Trung, phường Linh Trung, quận Thủ Đức, TP.HCM','0',450.00,3,NULL,'2','2014-06-12 16:10:52','1',NULL,NULL,NULL),(7,'2014-06-05',NULL,7,'khách lẻ','111111111','37 ton duc thang','0',700.00,3,NULL,'2','2014-06-12 16:10:59','0',NULL,NULL,NULL),(8,'2014-06-07',NULL,30,'company 1','03058484','Hồ chí minh','2',222.00,3,NULL,'2','2014-06-07 05:37:48','1',NULL,NULL,NULL),(9,'2014-06-12',NULL,45,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301','43 An Binh','0',240.00,3,NULL,'0','2014-06-12 16:15:13','1',NULL,NULL,NULL),(10,'2014-06-27',NULL,48,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301','123 NTMK','0',246.00,4,1,'0','2014-06-27 17:04:54','1',NULL,NULL,NULL),(11,'2014-07-27',18,47,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301','123 NGTMK','0',1200.00,5,4,'0','2014-07-27 06:24:35','1',NULL,NULL,NULL),(12,'2014-07-27',NULL,36,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK','0',800.00,5,4,'0','2014-07-27 09:59:21','1',NULL,NULL,NULL),(13,'2014-07-31',NULL,34,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK','0',124.00,5,4,'0','2014-07-31 15:48:55','1',NULL,NULL,NULL),(14,'2014-08-02',NULL,44,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK','0',264.00,5,4,'0','2014-08-11 16:49:54','0',NULL,'1',21999),(15,'2014-08-11',NULL,38,'CÔNG TY DU LỊCH HOÀNG TRÀ','','','0',246.00,NULL,1,'0','2014-08-11 16:41:37','1',NULL,'1',21444),(16,'2014-08-12',NULL,40,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK','0',25.00,7,1,'0','2014-08-11 17:06:43','1',NULL,NULL,NULL),(17,'2014-08-14',NULL,68,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK nay thi dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di','2',617342534589.00,NULL,1,'0','2014-08-31 10:09:13','0','1','1',1),(18,'2014-08-17',NULL,35,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK','0',456.00,NULL,1,'2','2014-08-20 15:45:34','1',NULL,'1',21260),(19,'2014-09-02',35,37,'Cong ty THNN A Thong','0526262','fdhsdfgbsf','3',2380.00,7,1,'0','2014-09-02 04:59:18','1',NULL,NULL,NULL),(20,'2014-09-02',NULL,55,'Cong ty THNN A Thong','0526262111','fdhsdfgbsf','2',100511615.00,NULL,1,'0','2014-09-02 05:15:31','0','1','1',1),(21,'2014-09-02',NULL,65,'CÔNG TY DU LỊCH HOÀNG TRÀ','0301121321','123 NTMK nay thi dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di','2',110848454.00,NULL,1,'0','2014-09-02 05:18:47','0','1','1',1),(22,'2014-09-02',NULL,51,'Cong ty THNN A Thong','0526262111','fdhsdfgbsf','2',51000414.00,NULL,1,'0','2014-09-02 05:19:44','0','1','1',1),(23,'2014-09-09',NULL,49,'CÔNG TY DU LỊCH HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ','0301121321','123 NTMK nay thi dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di','0',1112.00,7,1,'0','2014-09-08 17:42:19','1',NULL,NULL,NULL),(24,'2014-09-10',NULL,32,'Cong ty THNN A Thong','0526262111','fdhsdfgbsf','0',111.00,7,1,'0','2014-09-10 15:35:35','1',NULL,NULL,NULL),(25,'2014-09-10',NULL,46,'Cong ty THNN A Thong','0526262111','fdhsdfgbsf','0',222.00,7,1,'2','2014-09-10 15:36:19','1',NULL,NULL,NULL),(26,'2014-09-10',NULL,31,'CÔNG TY DU LỊCH HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ HOÀNG TRÀ','0301121321','123 NTMK nay thi dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di, dai ra di','0',211.00,7,1,'0','2014-09-10 15:44:13','1',NULL,NULL,NULL),(27,'2014-10-18',NULL,50,'','','','1',1029.00,7,1,'2','2014-10-18 16:42:57','1',NULL,NULL,NULL),(28,'2014-10-20',NULL,41,'','','','1',4.00,7,1,'0','2014-10-19 17:24:26','1',NULL,NULL,NULL),(29,'2014-10-22',NULL,43,'aaadada','sdgvsd','dsfgdas','0',13000000000.00,7,1,'0','2014-10-21 17:25:39','1',NULL,'1',1),(30,'2014-11-10',43,42,'Cong ty THNN A Thong','0526262','fdhsdfgbsf','0',123.00,7,1,'0','2014-11-10 14:19:58','1',NULL,NULL,NULL),(31,'2015-01-14',NULL,33,'cty jofen abc','123456','123 DTH','0',123.00,7,1,'0','2015-01-14 16:18:58','1',NULL,NULL,NULL),(32,'2015-03-29',46,39,'Jofen Quan 1','3423424','AAAA','0',1308.00,7,1,'0','2015-03-29 05:37:49','1',NULL,NULL,NULL);
/*!40000 ALTER TABLE `tax_invoice_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_invoice_issue_detail`
--

DROP TABLE IF EXISTS `tax_invoice_issue_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_invoice_issue_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TICKET_NO` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `TAX_INVOICE_ISSUE_ID` bigint(20) NOT NULL,
  `ROUTE` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUANTITY` bigint(20) NOT NULL,
  `PRICE` decimal(14,2) NOT NULL,
  `AMOUNT` decimal(14,2) DEFAULT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DISPORDER` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_invoice_issue_detail`
--

LOCK TABLES `tax_invoice_issue_detail` WRITE;
/*!40000 ALTER TABLE `tax_invoice_issue_detail` DISABLE KEYS */;
INSERT INTO `tax_invoice_issue_detail` (`ID`, `TICKET_NO`, `TAX_INVOICE_ISSUE_ID`, `ROUTE`, `QUANTITY`, `PRICE`, `AMOUNT`, `LASTUPDATE`, `DISPORDER`) VALUES (1,'1111111111111',1,'JKGJK',2,123.00,246.00,'2014-05-31 04:57:51',0),(4,'297248630/88/',2,'sgn-tpe',5,348.00,1740.00,'2014-06-05 06:09:40',0),(5,'24697777/3330',2,'sgn-tpe',2,580.00,1160.00,'2014-06-05 06:09:40',0),(6,'2972459845555',3,'sgn-rmq',6,570.00,3420.00,'2014-06-05 06:11:10',0),(8,'2972482123456',4,'ci',2,375.00,750.00,'2014-06-05 06:29:45',0),(9,'2972499999999',5,'sgn-lax',2,1000.00,2000.00,'2014-06-05 06:31:03',0),(11,'1234567893123',6,'DG',1,450.00,450.00,'2014-06-05 06:47:48',0),(12,'2333333333333',7,'sgnakch',1,700.00,700.00,'2014-06-05 06:48:19',0),(13,'CCCCCC',8,'SFS',2,111.00,222.00,'2014-06-07 04:33:55',0),(14,'111111111111111',9,'FSFSF',2,120.00,240.00,'2014-06-12 16:15:13',0),(16,'52435252',10,'SDGVS',2,123.00,246.00,'2014-06-27 17:04:54',0),(18,'ASD451',12,'KA/IC',2,400.00,800.00,'2014-07-27 09:59:21',0),(19,'sdgsd',13,'SDGSD',1,124.00,124.00,'2014-07-31 15:48:56',0),(22,'AAAA',15,'ASDDD',2,123.00,246.00,'2014-08-11 16:41:37',0),(23,'ABCD',14,'',2,132.00,264.00,'2014-08-11 16:49:54',0),(26,'VVVV',16,'ASAS',1,25.00,25.00,'2014-08-11 17:06:43',0),(27,'ABCD12344444444444444444',11,'CDEF/ACVD',1,1200.00,1200.00,'2014-08-12 17:45:55',0),(33,'vdv',18,'QWADQW',2,111.00,222.00,'2014-08-17 16:22:43',0),(34,'11a1',18,'1111',1,123.00,123.00,'2014-08-17 16:22:43',0),(35,'effr',18,'1131',1,111.00,111.00,'2014-08-17 16:22:43',0),(40,'Kỳ 1 tháng 7 năm 2014',17,'',1,0.00,0.00,'2014-08-31 10:09:13',2),(41,'Cước vận tải quốc tế',17,'',1,617342534589.00,617342534589.00,'2014-08-31 10:09:13',1),(42,'gfjfg',19,'WTG',1,2380.00,2380.00,'2014-09-02 04:59:18',1),(43,'cgvsdf',20,'GFJMF',1,100511615.00,100511615.00,'2014-09-02 05:15:31',1),(44,'dgfd',21,'GJF',1,110848454.00,110848454.00,'2014-09-02 05:18:47',1),(45,'fhdf',22,'DSDGSD4',1,51000414.00,51000414.00,'2014-09-02 05:19:44',1),(46,'sdgs',23,'SDGSD',1,1112.00,1112.00,'2014-09-08 17:42:20',1),(47,'dsg',24,'DS',1,111.00,111.00,'2014-09-10 15:35:35',1),(48,'sdg',25,'131ES',1,222.00,222.00,'2014-09-10 15:36:15',1),(49,'saf',26,'SDG22',1,211.00,211.00,'2014-09-10 15:44:13',1),(50,'rrrsdsd33',27,'DSG',3,343.00,1029.00,'2014-10-18 16:42:22',1),(51,'fh',28,'SFS',2,300.00,600.00,'2014-10-19 17:24:27',2),(52,'dsfgsd',28,'SDFSD',2,-298.00,-596.00,'2014-10-19 17:24:27',1),(58,'fdg',29,'DFHDF',1,13000000000.00,13000000000.00,'2014-10-21 17:25:39',1),(59,'dsg',30,'SDG',1,123.00,123.00,'2014-11-10 14:19:58',1),(60,'jddjdjdj',31,'KDKXKS',1,123.00,123.00,'2015-01-14 16:18:58',1),(61,'fgj4',32,'FGJ',2,654.00,1308.00,'2015-03-29 05:37:49',1);
/*!40000 ALTER TABLE `tax_invoice_issue_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_issue`
--

DROP TABLE IF EXISTS `ticket_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_issue` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ISSUE_DATE` date NOT NULL,
  `RATE_ID` bigint(20) NOT NULL,
  `PAYMENT_TYPE` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `AGENT_ID` bigint(20) NOT NULL,
  `TAX_INVOICE_ID` bigint(20) DEFAULT NULL,
  `INPUT_EMP` bigint(20) DEFAULT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `AMOUNT_USD` decimal(14,2) DEFAULT NULL,
  `MODIFYDATETIME` datetime DEFAULT NULL,
  `MODIFY_EMP` bigint(20) DEFAULT NULL,
  `REPORTED_DATE` date DEFAULT NULL,
  `RETAIL_CUSTNAME` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_issue`
--

LOCK TABLES `ticket_issue` WRITE;
/*!40000 ALTER TABLE `ticket_issue` DISABLE KEYS */;
INSERT INTO `ticket_issue` (`ID`, `ISSUE_DATE`, `RATE_ID`, `PAYMENT_TYPE`, `AGENT_ID`, `TAX_INVOICE_ID`, `INPUT_EMP`, `LASTUPDATE`, `STATUS`, `AMOUNT_USD`, `MODIFYDATETIME`, `MODIFY_EMP`, `REPORTED_DATE`, `RETAIL_CUSTNAME`) VALUES (1,'2014-05-31',1,'0',1,1,NULL,'2014-05-31 04:57:51','1',246.00,NULL,NULL,NULL,NULL),(2,'2014-06-05',2,'0',2,NULL,NULL,'2014-06-07 05:38:58','2',2900.00,'2014-06-05 13:08:03',NULL,NULL,NULL),(3,'2014-06-05',2,'0',1,NULL,NULL,'2014-06-07 05:39:03','2',3420.00,'2014-06-05 13:08:23',NULL,NULL,NULL),(4,'2014-06-05',2,'2',2,NULL,NULL,'2014-06-07 05:39:08','2',750.00,'2014-06-05 13:26:11',NULL,NULL,NULL),(5,'2014-06-05',2,'0',1,NULL,NULL,'2014-06-07 05:20:17','2',2000.00,NULL,NULL,NULL,NULL),(6,'2014-06-05',3,'0',2,NULL,NULL,'2014-06-07 05:14:13','2',345.00,NULL,NULL,NULL,NULL),(7,'2014-06-07',3,'1',1,NULL,NULL,'2014-06-11 16:11:55','0',246.00,'2014-06-11 23:11:55',1,NULL,NULL),(8,'2014-06-07',3,'1',2,NULL,NULL,'2014-06-11 16:17:12','2',642.00,'2014-06-11 23:17:12',1,NULL,NULL),(9,'2014-06-07',3,'2',1,NULL,NULL,'2014-06-11 14:55:58','2',222.00,NULL,NULL,NULL,NULL),(10,'2014-06-12',3,'0',2,NULL,1,'2014-06-18 16:45:46','0',224.00,NULL,NULL,'2014-06-18',NULL),(11,'2014-06-21',3,'1',2,NULL,1,'2014-06-21 01:19:40','0',444.00,'2014-06-21 08:16:34',1,'2014-06-21',NULL),(12,'2014-06-21',3,'1',1,NULL,1,'2014-06-21 01:19:40','0',333.00,NULL,NULL,'2014-06-21',NULL),(13,'2014-06-21',3,'1',1,NULL,1,'2014-06-21 01:19:40','0',700.00,NULL,NULL,'2014-06-21',NULL),(14,'2014-06-21',3,'2',2,NULL,1,'2014-06-21 01:19:40','0',300.00,NULL,NULL,'2014-06-21',NULL),(15,'2014-06-21',3,'3',2,NULL,NULL,'2014-06-21 01:18:53','3',246.00,NULL,NULL,NULL,NULL),(16,'2014-06-21',3,'3',2,NULL,1,'2014-06-21 01:19:40','0',644.00,NULL,NULL,'2014-06-21',NULL),(17,'2014-07-27',5,'0',2,NULL,4,'2014-07-28 14:55:37','0',1200.00,NULL,NULL,'2014-07-28',NULL),(18,'2014-07-27',5,'0',2,11,4,'2014-07-28 14:55:37','1',1200.00,NULL,NULL,'2014-07-28',NULL),(19,'2014-07-29',5,'3',1,NULL,4,'2014-07-29 15:40:57','0',246.00,NULL,NULL,NULL,NULL),(20,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 14:47:53','0',123.00,NULL,NULL,NULL,NULL),(21,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 15:52:41','2',123.00,NULL,NULL,NULL,NULL),(22,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 14:50:35','0',123.00,NULL,NULL,NULL,NULL),(23,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 15:18:37','0',111.00,NULL,NULL,NULL,NULL),(24,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 15:49:22','2',222.00,NULL,NULL,NULL,NULL),(25,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 15:28:43','0',112.00,NULL,NULL,NULL,NULL),(26,'2014-07-31',5,'0',1,NULL,4,'2014-07-31 15:37:47','0',113.00,NULL,NULL,NULL,NULL),(27,'2014-08-02',5,'0',1,NULL,4,'2014-08-02 02:31:08','0',246.00,NULL,NULL,NULL,NULL),(28,'2014-08-02',5,'0',1,NULL,4,'2014-08-02 02:50:41','0',222.00,NULL,NULL,NULL,NULL),(29,'2014-08-02',5,'0',1,NULL,4,'2014-08-02 02:52:21','0',244.00,NULL,NULL,NULL,NULL),(30,'2014-08-02',5,'0',1,NULL,4,'2014-08-02 02:53:18','0',266.00,NULL,NULL,NULL,NULL),(31,'2014-08-13',7,'0',2,NULL,1,'2014-08-13 16:57:30','0',330.00,NULL,NULL,NULL,NULL),(32,'2014-08-17',7,'0',3,NULL,1,'2014-08-17 16:03:27','0',444.00,'2014-08-17 23:03:27',1,NULL,NULL),(33,'2014-08-19',7,'0',5,NULL,1,'2014-08-19 15:02:12','0',666.00,NULL,NULL,NULL,NULL),(34,'2014-09-02',7,'0',4,NULL,1,'2014-09-15 15:32:56','0',1309039383.00,'2014-09-15 22:32:57',1,NULL,NULL),(35,'2014-09-02',7,'3',3,19,1,'2014-09-02 04:59:18','1',2380.00,NULL,NULL,NULL,NULL),(36,'2014-09-06',7,'2',4,NULL,1,'2014-09-05 17:29:38','0',25.00,NULL,NULL,NULL,NULL),(37,'2014-09-10',7,'0',1,NULL,1,'2014-09-10 15:42:23','0',111.00,NULL,NULL,'2014-09-10',NULL),(38,'2014-09-10',7,'0',3,NULL,1,'2014-09-10 15:57:34','2',222.00,NULL,NULL,NULL,NULL),(39,'2014-09-10',7,'0',2,NULL,1,'2014-09-10 15:58:44','3',10.00,NULL,NULL,NULL,NULL),(40,'2014-09-10',7,'1',5,NULL,1,'2014-09-10 15:42:23','0',30.00,'2014-09-10 23:09:52',1,'2014-09-10',NULL),(41,'2014-10-07',7,'0',6,NULL,1,'2014-10-07 16:52:02','0',666.00,NULL,NULL,NULL,NULL),(42,'2014-10-20',7,'2',3,NULL,1,'2014-10-19 17:43:31','0',50.00,NULL,NULL,NULL,NULL),(43,'2014-11-10',7,'0',3,30,1,'2014-11-10 14:19:58','1',123.00,NULL,NULL,NULL,NULL),(44,'2014-11-10',7,'0',4,NULL,1,'2014-11-10 14:18:36','0',111.00,NULL,NULL,NULL,NULL),(45,'2014-11-15',7,'0',2,NULL,1,'2014-11-15 04:31:12','0',22.00,NULL,NULL,NULL,NULL),(46,'2015-03-10',7,'0',28,32,1,'2015-03-29 05:37:49','1',1308.00,'2015-03-10 23:47:18',1,NULL,'Jofen Quan 1'),(47,'2015-03-10',7,'0',28,NULL,1,'2015-03-10 16:58:18','0',268.00,'2015-03-10 23:58:18',1,NULL,'Takehashi1');
/*!40000 ALTER TABLE `ticket_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_issue_detail`
--

DROP TABLE IF EXISTS `ticket_issue_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_issue_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TICKET_NO` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `TICKET_ISSUE_ID` bigint(20) NOT NULL,
  `ROUTE` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUANTITY` bigint(20) NOT NULL,
  `PRICE` decimal(14,2) NOT NULL,
  `AMOUNT` decimal(14,2) DEFAULT NULL,
  `LASTUPDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DISPORDER` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_issue_detail`
--

LOCK TABLES `ticket_issue_detail` WRITE;
/*!40000 ALTER TABLE `ticket_issue_detail` DISABLE KEYS */;
INSERT INTO `ticket_issue_detail` (`ID`, `TICKET_NO`, `TICKET_ISSUE_ID`, `ROUTE`, `QUANTITY`, `PRICE`, `AMOUNT`, `LASTUPDATE`, `DISPORDER`) VALUES (1,'1111111111111',1,'JKGJK',2,123.00,246.00,'2014-05-31 04:55:15',0),(5,'24697777/3330',2,'sgn-tpe',2,580.00,1160.00,'2014-06-05 06:08:03',0),(6,'297248630/88/',2,'sgn-tpe',5,348.00,1740.00,'2014-06-05 06:08:03',0),(7,'2972459845555',3,'sgn-rmq',6,570.00,3420.00,'2014-06-05 06:08:22',0),(9,'2972482123456',4,'ci',2,375.00,750.00,'2014-06-05 06:26:10',0),(10,'2972499999999',5,'sgn-lax',2,1000.00,2000.00,'2014-06-05 06:30:15',0),(11,'1111111111111',6,'qqqqqq',1,345.00,345.00,'2014-06-05 06:59:10',0),(15,'CCCCCC',9,'SFS',2,111.00,222.00,'2014-06-07 04:33:31',0),(18,'AAAAAAAAAAAAAAAAAAA',7,'SDFSS',2,123.00,246.00,'2014-06-11 16:11:55',0),(20,'BBBBBB',8,'SSSS',2,321.00,642.00,'2014-06-11 16:17:12',0),(21,'1111212121',10,'SFSF',2,112.00,224.00,'2014-06-12 16:35:08',0),(23,'3423523',12,'DFD',3,111.00,333.00,'2014-06-21 01:16:07',0),(24,'22113344455',11,'FGSD',2,222.00,444.00,'2014-06-21 01:16:34',0),(25,'43643',13,'JHMJTG',1,300.00,300.00,'2014-06-21 01:17:32',0),(26,'53657653',13,'DGFG',2,200.00,400.00,'2014-06-21 01:17:32',0),(27,'43643',14,'GGGTT',3,100.00,300.00,'2014-06-21 01:18:04',0),(28,'56746',15,'TRJ',2,123.00,246.00,'2014-06-21 01:18:53',0),(29,'443646',16,'DFH',2,322.00,644.00,'2014-06-21 01:19:24',0),(30,'ABCD1234',17,'CDEF/ACVD',1,1200.00,1200.00,'2014-07-27 05:52:20',0),(31,'ABCD1234',18,'CDEF/ACVD',1,1200.00,1200.00,'2014-07-27 05:54:00',0),(32,'AAA111',19,'AMNC',2,123.00,246.00,'2014-07-29 15:40:57',0),(33,'ABCD',20,'11131',1,123.00,123.00,'2014-07-31 14:47:53',0),(34,'AADA',21,'AADA',1,123.00,123.00,'2014-07-31 14:50:10',0),(35,'safss',22,'121SDFS',1,123.00,123.00,'2014-07-31 14:50:35',0),(36,'sfas',23,'SFS',1,111.00,111.00,'2014-07-31 15:18:37',0),(37,'AADA',24,'SDF',1,222.00,222.00,'2014-07-31 15:24:17',0),(38,'cgsd',25,'DSGD',1,112.00,112.00,'2014-07-31 15:28:43',0),(39,'sdfdas',26,'2DS3',1,113.00,113.00,'2014-07-31 15:37:47',0),(40,'ACSAD',27,'',2,123.00,246.00,'2014-08-02 02:31:08',0),(41,'HIHIH',28,'',2,111.00,222.00,'2014-08-02 02:50:41',0),(42,'JOJOJO',29,'',2,122.00,244.00,'2014-08-02 02:52:21',0),(43,'ADDADA',30,'',2,133.00,266.00,'2014-08-02 02:53:18',0),(44,'ABAKKK',31,'AAACCC',1,213.00,213.00,'2014-08-13 16:57:30',0),(45,'JKW',31,'DDAAA',1,117.00,117.00,'2014-08-13 16:57:30',0),(50,'sdfs',32,'SD',1,222.00,222.00,'2014-08-17 16:03:27',0),(51,'zasz',32,'DSGFS',2,111.00,222.00,'2014-08-17 16:03:27',0),(52,'dsgsd',33,'SDF',2,111.00,222.00,'2014-08-19 15:02:12',0),(53,'sdgsd',33,'ASDFA',2,222.00,444.00,'2014-08-19 15:02:12',0),(55,'gfjfg',35,'WTG',1,2380.00,2380.00,'2014-09-02 04:58:49',1),(56,'fbd',36,'FBDS',1,25.00,25.00,'2014-09-05 17:29:38',1),(57,'dgvd',37,'SDVDS',1,111.00,111.00,'2014-09-10 15:56:30',1),(58,'sdf',38,'DSG',1,222.00,222.00,'2014-09-10 15:57:14',1),(59,'asdf',39,'DSAF',1,10.00,10.00,'2014-09-10 15:58:44',1),(61,'sdgsd',40,'SDG',1,30.00,30.00,'2014-09-10 16:09:52',1),(64,'fgg3w',34,'SDFGS',3,436346436.00,1309039308.00,'2014-09-15 15:32:56',1),(65,'efsd',34,'33SD',1,75.00,75.00,'2014-09-15 15:32:56',2),(66,'dfgsdg',41,'SDF',2,333.00,666.00,'2014-10-07 16:52:02',1),(67,'sdfg',42,'DFH',1,450.00,450.00,'2014-10-19 17:43:31',2),(68,'dfg',42,'SDG',2,-200.00,-400.00,'2014-10-19 17:43:31',1),(69,'dsg',43,'SDG',1,123.00,123.00,'2014-11-10 14:12:48',1),(70,'uk',44,'TRU',1,111.00,111.00,'2014-11-10 14:18:36',1),(71,'sdaf',45,'ASDF',1,22.00,22.00,'2014-11-15 04:31:12',1),(73,'fgj4',46,'FGJ',2,654.00,1308.00,'2015-03-10 16:47:18',1),(76,'45g4353',47,'FBH',2,134.00,268.00,'2015-03-10 16:58:18',1);
/*!40000 ALTER TABLE `ticket_issue_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-18  9:23:54
