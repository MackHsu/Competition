-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: 192.168.137.1    Database: competitionapp
-- ------------------------------------------------------
-- Server version	5.7.33-log

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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `messageId` varchar(36) NOT NULL,
  `conversationId` varchar(36) NOT NULL,
  `isSend` tinyint(4) NOT NULL,
  `content` varchar(300) CHARACTER SET utf8 DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`messageId`),
  UNIQUE KEY `messageId_UNIQUE` (`messageId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('3f485956754f478a9e102ad6309cd881','c7aaaae3d13e42e99d3c0f8242064a49',1,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','2021-05-05 19:14:38'),('630a57676f7749519e20b63a00b027a8','c7aaaae3d13e42e99d3c0f8242064a49',1,'123456','2021-05-05 19:06:53'),('796b4c1721f241b4815af74d7ffde0a0','c7aaaae3d13e42e99d3c0f8242064a49',1,'time test','2021-05-05 19:55:47'),('a09f990d513f4236ba492a669dd4b90a','5663543d64364719b9af5c1ac8a8f2eb',0,'time test','2021-05-05 19:55:47'),('bcfa23682e1a4e10a5899360721a1816','5663543d64364719b9af5c1ac8a8f2eb',0,'测试测试','2021-05-05 19:12:22'),('cd2b46f90b3441faaff1a0a2870f7292','5663543d64364719b9af5c1ac8a8f2eb',0,'blablablablabla','2021-05-05 17:47:24'),('d3018c3fe8c54c3eae0ba004c29972fe','c7aaaae3d13e42e99d3c0f8242064a49',1,'blablablablabla','2021-05-05 17:47:24'),('d95130b149ce431487259e0350e466bb','5663543d64364719b9af5c1ac8a8f2eb',0,'123456','2021-05-05 19:06:53'),('e0bfdbd557334c23b4ae2c227ca7a048','5663543d64364719b9af5c1ac8a8f2eb',0,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','2021-05-05 19:14:38'),('f94c0389cd8d4cf9923a18c62fb6fba1','c7aaaae3d13e42e99d3c0f8242064a49',1,'测试测试','2021-05-05 19:12:22');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-05 20:41:02
