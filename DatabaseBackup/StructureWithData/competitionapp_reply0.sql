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
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply` (
  `replyId` varchar(36) NOT NULL,
  `discussId` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `date` datetime NOT NULL,
  `content` varchar(500) CHARACTER SET utf8 NOT NULL,
  `replyUserId` varchar(36) DEFAULT NULL,
  `isFirst` tinyint(1) NOT NULL,
  PRIMARY KEY (`replyId`),
  UNIQUE KEY `replyId_UNIQUE` (`replyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES ('05e565c5447349c6aef2b7573476866b','f9f5dbcf63df4a03a09dd5bcdce1bc55','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-28 17:28:48','test content',NULL,1),('0b0b7ac923fc4a6e805399323dee79e1','67e5327265d04ad9a236eb0e83dbd53d','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-18 21:39:08','测试内容','',1),('17182e8743d54f0b8d2315a8a39e69b1','53d0d6cc4cee4ce39bb5bb17db2c68cc','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-28 18:56:31','我们组准备得比较仓促，希望比赛不要太难打','',0),('6bc2469f2fdd4623964ff51e96fe0d98','cabec56cb53843e7b019259e8a5edd67','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-18 21:26:27','测试内容','',1),('a87cc29500a54bd3b6ae5164c59a4acd','53d0d6cc4cee4ce39bb5bb17db2c68cc','b2d790ab9f8911ebb9fa00ff802344b6','2021-05-02 20:20:02','太强了，膜拜大佬','b2d790ab9f8911ebb9fa00ff802344b6',0),('c448353dd28d4c79b1f60066345a324d','53d0d6cc4cee4ce39bb5bb17db2c68cc','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-28 17:15:08','大家准备得如何？',NULL,1),('e8ab63c5a3eb45ef8065a33c53ce8d3e','53d0d6cc4cee4ce39bb5bb17db2c68cc','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-28 18:51:53','可以，准备充分','',0),('f2f8336d2ab942cfb35da4309ba5d5ae','0a91ec7bb1644df88b8862fe99f37d34','b2d790ab9f8911ebb9fa00ff802344b6','2021-04-28 17:27:23','测试',NULL,1);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-05 20:41:06
