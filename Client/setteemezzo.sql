CREATE DATABASE  IF NOT EXISTS `setteemezzo` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `setteemezzo`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: setteemezzo
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `giocatori`
--

DROP TABLE IF EXISTS `giocatori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `giocatori` (
  `Username` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Nome` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Cognome` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `DataDiNascita` date NOT NULL,
  `Email` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Sesso` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giocatori`
--

LOCK TABLES `giocatori` WRITE;
/*!40000 ALTER TABLE `giocatori` DISABLE KEYS */;
INSERT INTO `giocatori` VALUES ('a.chelaru','Angel','Chelaru','1996-02-19','a.chelaru@studenti.unipi.it','M'),('c.pinguini','Chiara','Pinguini','1998-12-24','c.pinguini@gmail.com','F'),('f.rondini','Francesca','Rondini','1989-10-21','f.rondini@gmail.com','F'),('g.bianchi','Giovanni','Bianchi','1990-11-30','g.bianchi@gmail.com','M'),('g.gabbiani','Giada','Gabbiani','1992-09-11','g.gabbiani@gmail.com','F'),('l.celesti','Luca','Celesti','1996-08-24','l.celesti@gmail.com','M'),('l.lupi','Laura','Lupi','1987-12-03','l.lupi@gmail.com','F'),('m.lepri','Martina','Lepri','1994-07-16','m.lepri@gmail.com','F'),('m.rossi','Mario','Rossi','1997-03-22','m.rossi@gmail.com','M'),('s.verdi','Simone','Verdi','1993-01-14','s.verdi@gmail.com','M');
/*!40000 ALTER TABLE `giocatori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partite`
--

DROP TABLE IF EXISTS `partite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partite` (
  `Username` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `OraFinePartita` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Esito` tinyint(4) NOT NULL,
  `PunteggioGiocatore` float DEFAULT NULL,
  `PunteggioMazziere` float DEFAULT NULL,
  PRIMARY KEY (`Username`,`OraFinePartita`),
  CONSTRAINT `Username` FOREIGN KEY (`Username`) REFERENCES `giocatori` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partite`
--

LOCK TABLES `partite` WRITE;
/*!40000 ALTER TABLE `partite` DISABLE KEYS */;
INSERT INTO `partite` VALUES ('a.chelaru','2018-04-05 06:55:42',1,5.5,8),('a.chelaru','2018-04-05 07:55:42',1,7,9),('a.chelaru','2018-04-05 08:55:42',1,7.5,8),('a.chelaru','2018-04-05 09:05:42',1,6.5,10),('a.chelaru','2018-04-05 09:55:42',0,10,4),('a.chelaru','2018-04-05 10:55:42',1,7,8.5),('a.chelaru','2018-04-05 10:57:42',0,6.5,7),('c.pinguini','2018-04-05 06:55:42',1,6,9),('c.pinguini','2018-04-06 06:55:42',1,5,8),('c.pinguini','2018-04-07 06:55:42',0,9,3),('f.rondini','2018-04-07 06:35:42',1,6,8),('f.rondini','2018-04-07 06:56:42',0,5.5,6),('f.rondini','2018-04-08 06:55:42',0,7,7),('f.rondini','2019-03-17 17:55:42',1,7,11),('g.bianchi','2018-04-07 07:55:42',1,5,8),('g.bianchi','2018-05-07 06:55:42',1,7,10),('g.gabbiani','2017-04-07 06:55:42',0,9,2),('l.celesti','2018-04-07 08:55:42',1,6.5,9.5),('l.celesti','2018-05-07 09:55:42',1,7.5,9),('l.celesti','2018-11-07 07:55:42',1,7,11),('l.celesti','2018-12-07 07:55:42',1,5,8.5),('l.celesti','2019-04-06 06:55:42',0,6.5,12),('l.celesti','2019-04-07 06:55:42',0,8,6),('l.lupi','2018-04-07 16:55:42',1,6.5,8.5),('l.lupi','2018-06-07 06:55:42',0,8.5,3),('l.lupi','2018-07-07 06:55:42',0,10,5),('l.lupi','2018-08-07 06:55:42',0,9.5,2),('l.lupi','2018-09-07 06:55:42',0,8,3),('m.lepri','2017-04-07 06:55:42',1,5,8.5),('m.lepri','2018-04-07 10:55:42',0,9,5),('m.lepri','2018-05-07 06:55:42',1,7.5,9.5),('m.rossi','2018-03-17 17:55:42',0,8.5,2),('m.rossi','2018-04-07 06:55:42',1,7.5,10.5),('m.rossi','2018-04-07 13:55:42',0,5.5,7),('m.rossi','2018-05-07 06:55:42',1,6.5,9),('s.verdi','2018-04-07 06:55:42',0,11,0.5),('s.verdi','2018-05-07 06:55:42',1,6,8.5);
/*!40000 ALTER TABLE `partite` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-28 23:57:32

