-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: mapdb
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

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

CREATE DATABASE IF NOT EXISTS mapdb;
USE mapdb;

--
-- Table structure for table `empt`
--

DROP TABLE IF EXISTS `empt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empt` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empt`
--

LOCK TABLES `empt` WRITE;
/*!40000 ALTER TABLE `empt` DISABLE KEYS */;
/*!40000 ALTER TABLE `empt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playtennis`
--

DROP TABLE IF EXISTS `playtennis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playtennis` (
  `outlook` varchar(10) DEFAULT NULL,
  `temperature` float(5,2) DEFAULT NULL,
  `umidity` varchar(10) DEFAULT NULL,
  `wind` varchar(10) DEFAULT NULL,
  `play` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playtennis`
--

LOCK TABLES `playtennis` WRITE;
/*!40000 ALTER TABLE `playtennis` DISABLE KEYS */;
INSERT INTO `playtennis` VALUES ('sunny',30.30,'high','weak','no'),('sunny',30.30,'high','strong','no'),('overcast',30.00,'high','weak','yes'),('rain',13.00,'high','weak','yes'),('rain',0.00,'normal','weak','yes'),('rain',0.00,'normal','strong','no'),('overcast',0.10,'normal','strong','yes'),('sunny',13.00,'high','weak','no'),('sunny',0.10,'normal','weak','yes'),('rain',12.00,'normal','weak','yes'),('sunny',12.50,'normal','strong','yes'),('overcast',12.50,'high','strong','yes'),('overcast',29.21,'normal','weak','yes'),('rain',12.50,'high','strong','no');
/*!40000 ALTER TABLE `playtennis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotti`
--

DROP TABLE IF EXISTS `prodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotti` (
  `nome` varchar(20) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `categoria` varchar(20) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `prezzo` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti`
--

LOCK TABLES `prodotti` WRITE;
/*!40000 ALTER TABLE `prodotti` DISABLE KEYS */;
INSERT INTO `prodotti` VALUES ('Maglione','Abbigliamento',22.99),('Scarpe','Abbigliamento',50.40),('Cintura','Abbigliamento',10.00),('Decoder','Elettronica',100.00),('Stampante','Elettronica',80.50),('Monitor','Elettronica',208.50),('Cereali','Alimentari',3.20),('T-Shirt','Abbigliamento',20.00),('Yogurt','Alimentari',2.80),('Auricolari','Elettronica',15.00),('Pasta','Alimentari',0.80),('Power bank','Elettronica',25.00),('Penna nera','Cancelleria',0.40),('Gomma','Cancelleria',0.80),('Quaderno','Cancelleria',0.80);
/*!40000 ALTER TABLE `prodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `ID` int(11) DEFAULT NULL,
  `stringa` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'a'),(2,'b');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test2`
--

DROP TABLE IF EXISTS `test2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test2` (
  `ID` double DEFAULT NULL,
  `value` varchar(3) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test2`
--

LOCK TABLES `test2` WRITE;
/*!40000 ALTER TABLE `test2` DISABLE KEYS */;
INSERT INTO `test2` VALUES (0.1,'aaa'),(0.7,'aab'),(0.9,'aab'),(5.3,'sdf'),(5.8,'sdf'),(5.1,'sdf');
/*!40000 ALTER TABLE `test2` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-22 23:22:23
