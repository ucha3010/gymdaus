-- MySQL dump 10.13  Distrib 5.5.57, for Win64 (AMD64)
--
-- Host: localhost    Database: gymdaus
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.13-MariaDB

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  `url` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'','taekwondo',0,'/gimnasio/tipoInscripcion'),(2,'','volley',1,'/gimnasio');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `change_low_sequence`
--

DROP TABLE IF EXISTS `change_low_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `change_low_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `change_low_sequence`
--

LOCK TABLES `change_low_sequence` WRITE;
/*!40000 ALTER TABLE `change_low_sequence` DISABLE KEYS */;
INSERT INTO `change_low_sequence` VALUES (160);
/*!40000 ALTER TABLE `change_low_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'country.spain',0),(2,'country.france',10),(3,'country.portugal',22),(4,'country.austria',1),(5,'country.belgium',2),(6,'country.bulgaria',3),(7,'country.croatia',4),(8,'country.cyprus',5),(9,'country.czechia',6),(10,'country.denmark',7),(11,'country.estonia',8),(12,'country.finland',9),(13,'country.germany',11),(14,'country.greece',12),(15,'country.hungary',13),(16,'country.ireland',14),(17,'country.italy',15),(18,'country.latvia',16),(19,'country.lithuania',17),(20,'country.luxembourg',18),(21,'country.malta',19),(22,'country.netherlands',20),(23,'country.poland',21),(24,'country.romania',23),(25,'country.slovakia',24),(26,'country.slovenia',25),(27,'country.sweden',26);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment`
--

DROP TABLE IF EXISTS `enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address_city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_zip` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_address_zip` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_as` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_birthdate` datetime(6) DEFAULT NULL,
  `authorizer_enrollment_id_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_lastname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorizer_enrollment_second_lastname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enrollment_date` datetime(6) NOT NULL,
  `gym_activity_id` bigint(20) DEFAULT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `gym_more_registration_id` bigint(20) DEFAULT NULL,
  `gym_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `inclusive` bit(1) NOT NULL,
  `minor` bit(1) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `own` bit(1) NOT NULL,
  `paid` bit(1) NOT NULL,
  `paid_date` datetime(6) DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sepa_account_number` varchar(34) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sepa_account_person` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sepa_direct_debit` bit(1) NOT NULL,
  `signed` bit(1) NOT NULL,
  `swift` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tournament_date` datetime(6) DEFAULT NULL,
  `user_enrollment_birthdate` datetime(6) DEFAULT NULL,
  `user_enrollment_id_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_enrollment_lastname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_enrollment_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_enrollment_second_lastname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_enrollment_sex` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `enrollment_kind` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment`
--

LOCK TABLES `enrollment` WRITE;
/*!40000 ALTER TABLE `enrollment` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment_as`
--

DROP TABLE IF EXISTS `enrollment_as`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment_as` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_as`
--

LOCK TABLES `enrollment_as` WRITE;
/*!40000 ALTER TABLE `enrollment_as` DISABLE KEYS */;
INSERT INTO `enrollment_as` VALUES (1,'mother',1),(2,'father',0),(3,'tutor',2);
/*!40000 ALTER TABLE `enrollment_as` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment_more_data`
--

DROP TABLE IF EXISTS `enrollment_more_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment_more_data` (
  `enrollment_id` bigint(20) NOT NULL,
  `belt` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `notes` text COLLATE utf8_unicode_ci,
  `poomsae` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `whatsapp_authorization` bit(1) NOT NULL,
  PRIMARY KEY (`enrollment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_more_data`
--

LOCK TABLES `enrollment_more_data` WRITE;
/*!40000 ALTER TABLE `enrollment_more_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollment_more_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym`
--

DROP TABLE IF EXISTS `gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym` (
  `id` bigint(20) NOT NULL,
  `contracted_records` int(11) NOT NULL,
  `contracted_visibility` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `modification_date` datetime(6) DEFAULT NULL,
  `modification_username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `tax_id_code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym`
--

LOCK TABLES `gym` WRITE;
/*!40000 ALTER TABLE `gym` DISABLE KEYS */;
INSERT INTO `gym` VALUES (1,200,2,'',NULL,NULL,'Gimnasio Cobeña','2024-10-30 00:00:00.000000','W3430473C',0),(2,100,1,'','2024-11-19 11:28:04.000000','05959715R','Gimnasio Algete','2024-10-30 13:25:31.000000','F34452110',2),(3,300,3,'',NULL,NULL,'Gimnasio Alcobendas','2024-10-30 00:00:00.000000','C28621969',3),(63,300,5,'',NULL,'05959715R','Championdo','2024-11-25 20:10:26.000000','F41386525',1);
/*!40000 ALTER TABLE `gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_activity`
--

DROP TABLE IF EXISTS `gym_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_activity` (
  `id` bigint(20) NOT NULL,
  `activity_id` bigint(20) DEFAULT NULL,
  `registration_date` datetime(6) NOT NULL,
  `registration_user` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gym_address_id` bigint(20) DEFAULT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_activity`
--

LOCK TABLES `gym_activity` WRITE;
/*!40000 ALTER TABLE `gym_activity` DISABLE KEYS */;
INSERT INTO `gym_activity` VALUES (80,1,'2024-12-03 18:58:20.000000','13919945T',50,2,''),(85,2,'2024-12-04 20:34:27.000000','13919945T',50,2,''),(98,1,'2024-12-08 20:45:16.000000','02262310F',66,1,''),(101,1,'2024-12-08 20:49:11.000000','02262310F',65,1,'');
/*!40000 ALTER TABLE `gym_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_activity_schedule`
--

DROP TABLE IF EXISTS `gym_activity_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_activity_schedule` (
  `id` bigint(20) NOT NULL,
  `end_time` time(6) DEFAULT NULL,
  `friday` bit(1) NOT NULL,
  `gym_activity_id` bigint(20) DEFAULT NULL,
  `monday` bit(1) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `position` int(11) NOT NULL,
  `room_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saturday` bit(1) NOT NULL,
  `specific_end_date` datetime(6) DEFAULT NULL,
  `specific_start_date` datetime(6) DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  `sunday` bit(1) NOT NULL,
  `thursday` bit(1) NOT NULL,
  `tuesday` bit(1) NOT NULL,
  `wednesday` bit(1) NOT NULL,
  `adult` bit(1) NOT NULL,
  `capacity` int(11) NOT NULL,
  `inclusive` bit(1) NOT NULL,
  `minor` bit(1) NOT NULL,
  `price` double NOT NULL,
  `activity_id` bigint(20) DEFAULT NULL,
  `gym_address_id` bigint(20) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_activity_schedule`
--

LOCK TABLES `gym_activity_schedule` WRITE;
/*!40000 ALTER TABLE `gym_activity_schedule` DISABLE KEYS */;
INSERT INTO `gym_activity_schedule` VALUES (86,'10:30:00.000000','\0',85,'\0','Vóley Menor Sa',0,'','','2024-12-31 00:00:00.000000','2024-12-04 00:00:00.000000','09:00:00.000000','\0','\0','\0','\0','',0,'\0','\0',25,2,50,''),(87,'19:00:00.000000','\0',80,'','Taekwondo Adultos Tarde L-Mi',0,'','\0','2034-12-31 00:00:00.000000','2024-12-04 00:00:00.000000','18:00:00.000000','\0','\0','\0','','',45,'\0','\0',50,1,50,''),(88,'18:00:00.000000','\0',80,'','Taekwondo Infantil Tarde L-Mi',1,'Pequeños','\0','2034-12-31 00:00:00.000000','2024-12-04 00:00:00.000000','17:00:00.000000','\0','\0','\0','','\0',23,'\0','',35,1,50,''),(93,'17:00:00.000000','',80,'\0','para eliminar',2,'','\0','2024-12-20 00:00:00.000000','2024-12-05 00:00:00.000000','16:00:00.000000','\0','\0','\0','\0','',0,'\0','\0',15,1,50,''),(95,'18:00:00.000000','\0',85,'\0','para eliminar',2,'','\0','2024-12-27 00:00:00.000000','2024-12-08 00:00:00.000000','22:00:00.000000','\0','','','','\0',0,'\0','',0,2,50,''),(96,'20:55:00.000000','',85,'\0','otra más',1,'','\0','2024-12-26 00:00:00.000000','2024-12-08 00:00:00.000000','19:58:00.000000','\0','\0','\0','\0','',0,'\0','\0',0,2,50,''),(97,'22:00:00.000000','\0',85,'\0','otro 2',3,'','','2024-12-27 00:00:00.000000','2024-12-07 00:00:00.000000','21:00:00.000000','\0','','\0','\0','\0',0,'\0','',0,2,50,''),(99,'19:30:00.000000','\0',98,'','Tkd Adultos Tardes L-Mi',1,'','\0','2025-01-02 00:00:00.000000','2024-12-08 00:00:00.000000','18:30:00.000000','\0','\0','\0','','',0,'\0','\0',48,1,66,''),(100,'18:30:00.000000','\0',98,'','Tkd Menores',0,'','\0','2024-12-26 00:00:00.000000','2024-12-08 00:00:00.000000','17:30:00.000000','\0','\0','\0','','\0',0,'\0','',27,1,66,''),(102,'19:30:00.000000','\0',101,'\0','Tkd Adultos Tardes Ma - J',0,'','\0','2024-12-29 00:00:00.000000','2024-12-08 00:00:00.000000','18:30:00.000000','\0','','','\0','',0,'\0','\0',0,1,65,'');
/*!40000 ALTER TABLE `gym_activity_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_address`
--

DROP TABLE IF EXISTS `gym_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_address` (
  `id` bigint(20) NOT NULL,
  `address_city` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `address_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_street` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address_zip` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `email_host` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_password` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_port` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gym_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_address`
--

LOCK TABLES `gym_address` WRITE;
/*!40000 ALTER TABLE `gym_address` DISABLE KEYS */;
INSERT INTO `gym_address` VALUES (50,'Daganzo de Arriba','8','Planta 1','Paseo de Arriba','28862',1,'damianjava@gmail.com','smtp.gmail.com','1234','587','\0','Sede Daganzo','917778989',2),(52,'Ajalvir','2','Local 3','Avenida de Ajalvir','28861',1,'damianjava@gmail.com','smtp.office365.com',NULL,'587','','Sede Ajalvir','915556644',2),(64,'Tres Cantos','12','','Paseo Islas','28440',1,'damianjava@gmail.com','smtp.gmail.com',NULL,'587','','Championdo','915556643',63),(65,'Cobeña','15','','Avenida del Barranco del agua','28863',1,'damianjava@gmail.com','smtp.office365.com','1234','587','','Barranco del Agua','915556699',1),(66,'Cobeña','1','','Calle de arriba','28863',1,'damianjava@gmail.com','',NULL,'','','Zona Pilotos','915556689',1),(67,'Cobeña','','Local 6','Calle del centro','28863',1,'damianjava@gmail.com','',NULL,'','','Centro comercial','915556695',1);
/*!40000 ALTER TABLE `gym_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_belt`
--

DROP TABLE IF EXISTS `gym_belt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_belt` (
  `id` bigint(20) NOT NULL,
  `color` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `gym_id` bigint(20) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_belt`
--

LOCK TABLES `gym_belt` WRITE;
/*!40000 ALTER TABLE `gym_belt` DISABLE KEYS */;
INSERT INTO `gym_belt` VALUES (20,'Blanco',1,0),(22,'Amarillo',1,2),(31,'123456789012345678901234567890',2,0),(39,'Verde',1,6),(107,'Blanco Amarillo',1,1),(109,'Verde Azul',1,7),(110,'Azul',1,8),(111,'Azul Rojo',1,9),(112,'Rojo',1,10),(113,'Rojo Negro',1,11),(114,'Negro 1º Dan',1,12),(145,'Amarillo Naranja',1,3),(146,'Naranja',1,4),(147,'Naranja Verde',1,5),(159,'Negro 2º Dan',1,13);
/*!40000 ALTER TABLE `gym_belt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_category`
--

DROP TABLE IF EXISTS `gym_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `start_birthday_year` int(11) NOT NULL,
  `end_birthday_year` int(11) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `poomsae_id` bigint(20) DEFAULT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_category`
--

LOCK TABLES `gym_category` WRITE;
/*!40000 ALTER TABLE `gym_category` DISABLE KEYS */;
INSERT INTO `gym_category` VALUES (141,'A1 Infantil',2014,2014,1,29,0),(148,'A2 Infantil',2014,2014,1,29,1),(152,'A3 Infantil',2014,2014,1,29,2);
/*!40000 ALTER TABLE `gym_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_category_gym_belt`
--

DROP TABLE IF EXISTS `gym_category_gym_belt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_category_gym_belt` (
  `id` bigint(20) NOT NULL,
  `gym_belt_id` bigint(20) DEFAULT NULL,
  `gym_category_id` bigint(20) DEFAULT NULL,
  `registration_date` datetime(6) NOT NULL,
  `registration_user` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_category_gym_belt`
--

LOCK TABLES `gym_category_gym_belt` WRITE;
/*!40000 ALTER TABLE `gym_category_gym_belt` DISABLE KEYS */;
INSERT INTO `gym_category_gym_belt` VALUES (142,20,141,'2024-12-12 23:16:29.000000','13919945T'),(143,107,141,'2024-12-12 23:16:29.000000','13919945T'),(144,22,141,'2024-12-12 23:16:29.000000','13919945T'),(149,145,148,'2024-12-12 23:19:33.000000','13919945T'),(150,146,148,'2024-12-12 23:19:33.000000','13919945T'),(151,147,148,'2024-12-12 23:19:33.000000','13919945T'),(153,39,152,'2024-12-12 23:20:08.000000','13919945T'),(154,109,152,'2024-12-12 23:20:08.000000','13919945T'),(155,110,152,'2024-12-12 23:20:08.000000','13919945T'),(156,111,152,'2024-12-12 23:20:08.000000','13919945T'),(157,112,152,'2024-12-12 23:20:08.000000','13919945T'),(158,113,152,'2024-12-12 23:20:08.000000','13919945T');
/*!40000 ALTER TABLE `gym_category_gym_belt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_document_manager`
--

DROP TABLE IF EXISTS `gym_document_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_document_manager` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime(6) NOT NULL,
  `delete_date` datetime(6) DEFAULT NULL,
  `extension` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `filename` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `gym_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `path` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `section` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `signed` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_document_manager`
--

LOCK TABLES `gym_document_manager` WRITE;
/*!40000 ALTER TABLE `gym_document_manager` DISABLE KEYS */;
INSERT INTO `gym_document_manager` VALUES (68,'2024-11-28 18:44:10.000000',NULL,'.jpg','carnet1819.jpg',1,'Gimnasio Cobeña','files\\gyms\\1','','\0'),(71,'2024-11-28 20:35:57.000000',NULL,'.docx','Contenidopisoparasegurohogar.docx',1,'Gimnasio Cobeña','files\\gyms\\1','','\0'),(72,'2024-11-29 11:57:22.000000',NULL,'.xlsx','BDSociospruebas.xlsx',2,'Gimnasio Algete','files\\gyms\\2','','\0'),(94,'2024-12-08 13:43:19.000000',NULL,'.docx','Contenidopisoparasegurohogar.docx',2,'Gimnasio Algete','files\\gyms\\2','para probar sortable','\0');
/*!40000 ALTER TABLE `gym_document_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_more_registration`
--

DROP TABLE IF EXISTS `gym_more_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_more_registration` (
  `id` bigint(20) NOT NULL,
  `address_city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_zip` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `more_registration_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `needs_paid` bit(1) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `registration_user` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sepa` bit(1) NOT NULL,
  `end_registration_available` datetime(6) DEFAULT NULL,
  `modification_date` datetime(6) DEFAULT NULL,
  `modification_user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_registration_available` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_more_registration`
--

LOCK TABLES `gym_more_registration` WRITE;
/*!40000 ALTER TABLE `gym_more_registration` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_more_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_more_registration_belt`
--

DROP TABLE IF EXISTS `gym_more_registration_belt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_more_registration_belt` (
  `id` bigint(20) NOT NULL,
  `belt_id` bigint(20) DEFAULT NULL,
  `gym_more_registration_id` bigint(20) DEFAULT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_more_registration_belt`
--

LOCK TABLES `gym_more_registration_belt` WRITE;
/*!40000 ALTER TABLE `gym_more_registration_belt` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_more_registration_belt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_more_registration_date`
--

DROP TABLE IF EXISTS `gym_more_registration_date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_more_registration_date` (
  `id` bigint(20) NOT NULL,
  `end_date_time` datetime(6) NOT NULL,
  `gym_more_registration_id` bigint(20) DEFAULT NULL,
  `start_date_time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_more_registration_date`
--

LOCK TABLES `gym_more_registration_date` WRITE;
/*!40000 ALTER TABLE `gym_more_registration_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_more_registration_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_more_registration_gym_category`
--

DROP TABLE IF EXISTS `gym_more_registration_gym_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_more_registration_gym_category` (
  `id` bigint(20) NOT NULL,
  `gym_category_id` bigint(20) NOT NULL,
  `gym_more_registration_id` bigint(20) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `registration_user` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_more_registration_gym_category`
--

LOCK TABLES `gym_more_registration_gym_category` WRITE;
/*!40000 ALTER TABLE `gym_more_registration_gym_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_more_registration_gym_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_more_registration_participating_entity`
--

DROP TABLE IF EXISTS `gym_more_registration_participating_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_more_registration_participating_entity` (
  `id` bigint(20) NOT NULL,
  `gym_more_registration_id` bigint(20) DEFAULT NULL,
  `participating_entity_id` bigint(20) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `registration_user` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_more_registration_participating_entity`
--

LOCK TABLES `gym_more_registration_participating_entity` WRITE;
/*!40000 ALTER TABLE `gym_more_registration_participating_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_more_registration_participating_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_parameter`
--

DROP TABLE IF EXISTS `gym_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_parameter` (
  `gym_id` bigint(20) NOT NULL,
  `key_data` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `modification_date` datetime(6) NOT NULL,
  `modification_username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`gym_id`,`key_data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_parameter`
--

LOCK TABLES `gym_parameter` WRITE;
/*!40000 ALTER TABLE `gym_parameter` DISABLE KEYS */;
INSERT INTO `gym_parameter` VALUES (1,'email.address','2024-11-08 14:35:50.000000','02262310F','damianjava@gmail.com'),(1,'email.host','2024-11-08 14:47:45.000000','13919945T','smtp.gmail.com'),(1,'email.password','2024-11-08 14:46:18.000000','02262310F','ylmfgftrkhhzneui'),(1,'email.port','2024-11-08 14:47:45.000000','13919945T','587'),(2,'email.address','2024-11-10 14:35:50.000000','02262310F','damianjava@gmail.com'),(2,'email.host','2024-11-24 13:44:06.000000','13919945T','smtp.office365.com'),(2,'email.password','2024-11-27 23:02:23.000000','13919945T','ylmfgftrkhhzneui'),(2,'email.port','2024-11-24 13:44:06.000000','13919945T','587');
/*!40000 ALTER TABLE `gym_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_photo`
--

DROP TABLE IF EXISTS `gym_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_photo` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime(6) NOT NULL,
  `delete_date` datetime(6) DEFAULT NULL,
  `extension` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `filename` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `path` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `section` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `main_photo` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_photo`
--

LOCK TABLES `gym_photo` WRITE;
/*!40000 ALTER TABLE `gym_photo` DISABLE KEYS */;
INSERT INTO `gym_photo` VALUES (53,'2024-11-24 19:00:28.000000',NULL,'.jpg','Canillafiltrada.jpg',1,'files\\photos\\gym\\1',NULL,'\0'),(56,'2024-11-24 19:27:37.000000',NULL,'.jpg','Fregadero.jpg',1,'files\\photos\\gym\\1',NULL,'\0'),(57,'2024-11-24 19:28:24.000000',NULL,'.jpg','Terreno2.jpg',1,'files\\photos\\gym\\1',NULL,'\0'),(61,'2024-11-25 15:27:23.000000',NULL,'.jpg','LogoGimnasioCobea.jpg',1,'files\\photos\\gym\\1',NULL,''),(62,'2024-11-25 15:31:42.000000',NULL,'.jpg','LogoGimnasioAlgete.jpg',2,'files\\photos\\gym\\2',NULL,'');
/*!40000 ALTER TABLE `gym_photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_poomsae`
--

DROP TABLE IF EXISTS `gym_poomsae`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_poomsae` (
  `id` bigint(20) NOT NULL,
  `gym_id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_poomsae`
--

LOCK TABLES `gym_poomsae` WRITE;
/*!40000 ALTER TABLE `gym_poomsae` DISABLE KEYS */;
INSERT INTO `gym_poomsae` VALUES (27,1,'2º Poomsae - Taeguk i chang',1),(28,1,'3º Poomsae - Taeguk sam chang',2),(29,1,'1º Poomsae - Taeguk il chang',0),(30,2,'123456789012345678901234567890',0),(40,1,'4º Poomsae - Taeguk sa chang',3),(115,1,'5º Poomsae - Taeguk oh chang',4),(116,1,'6º Poomsae - Taeguk yuk chang',5),(117,1,'7º Poomsae - Taeguk chill chang',6),(118,1,'8º Poomsae - Taeguk pal chang',7);
/*!40000 ALTER TABLE `gym_poomsae` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_user`
--

DROP TABLE IF EXISTS `gym_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_user` (
  `id` bigint(20) NOT NULL,
  `gym_id` bigint(20) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `registration_user` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gym_role` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_user`
--

LOCK TABLES `gym_user` WRITE;
/*!40000 ALTER TABLE `gym_user` DISABLE KEYS */;
INSERT INTO `gym_user` VALUES (1,1,'2024-11-04 00:00:00.000000','02262310F','02262310F','ROLE_MANAGER'),(2,1,'2024-11-04 00:00:00.000000','13919945T','13919945T','ROLE_EMPLOYEE'),(3,2,'2024-11-04 00:00:00.000000','13919945T','13919945T','ROLE_MANAGER');
/*!40000 ALTER TABLE `gym_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_parameter`
--

DROP TABLE IF EXISTS `manager_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_parameter` (
  `id` bigint(20) NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_host` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_port` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host_page_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_parameter`
--

LOCK TABLES `manager_parameter` WRITE;
/*!40000 ALTER TABLE `manager_parameter` DISABLE KEYS */;
INSERT INTO `manager_parameter` VALUES (1,'damianjava@gmail.com','smtp.gmail.com','587','http://localhost:8080','ylmfgftrkhhzneui');
/*!40000 ALTER TABLE `manager_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `more_registration`
--

DROP TABLE IF EXISTS `more_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `more_registration` (
  `id` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL,
  `url` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `more_registration`
--

LOCK TABLES `more_registration` WRITE;
/*!40000 ALTER TABLE `more_registration` DISABLE KEYS */;
INSERT INTO `more_registration` VALUES (1,'','taekwondo.tournament',0,'/tournamentRegistration/mainPage'),(2,'','taekwondo.license.order',1,'/mandato/mandatos');
/*!40000 ALTER TABLE `more_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participating_entity`
--

DROP TABLE IF EXISTS `participating_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participating_entity` (
  `id` bigint(20) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `notes` text COLLATE utf8_unicode_ci,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participating_entity`
--

LOCK TABLES `participating_entity` WRITE;
/*!40000 ALTER TABLE `participating_entity` DISABLE KEYS */;
INSERT INTO `participating_entity` VALUES (74,2,'La cueva de arriba','Es un sucucho donde practican pesos los gordos con olor a cebolla podrida',0),(75,2,'La otra casa','',1),(76,2,'El aljibe','Un espectacular lugar, muy refinado, para ir a pasar un momento de relax junto con tus amigos y seres queridos',2),(103,1,'Gimnasio Algetes','',1),(104,1,'Gimnasio Daganzo','',2),(105,1,'Gimnasio Ajalvir','',0),(106,1,'Gimnasio Fuente el Saz','',3);
/*!40000 ALTER TABLE `participating_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signature`
--

DROP TABLE IF EXISTS `signature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attempts` int(11) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `operation_id` bigint(20) DEFAULT NULL,
  `operation_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `signed` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signature`
--

LOCK TABLES `signature` WRITE;
/*!40000 ALTER TABLE `signature` DISABLE KEYS */;
/*!40000 ALTER TABLE `signature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signature_code`
--

DROP TABLE IF EXISTS `signature_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signature_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `expiration_date` datetime(6) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `operation_id` bigint(20) DEFAULT NULL,
  `operation_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `signed_ok_page` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signature_code`
--

LOCK TABLES `signature_code` WRITE;
/*!40000 ALTER TABLE `signature_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `signature_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `attempts` int(11) NOT NULL,
  `expiration` datetime(6) NOT NULL,
  `gym_id` bigint(20) DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `method_to_be_use` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username_send_change` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_used_ok` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES ('0017de1d-17eb-447c-af9c-1c563555d5af',1,'2024-11-18 14:04:28.000000',2,'02262310F','newManager','13919945T','2024-11-11 14:05:15.000000'),('0416bf6d-759d-4d62-91d3-d72a34d6af5e',1,'2024-11-17 19:36:34.000000',2,'29283702X','newManager','13919945T','2024-11-10 19:39:54.000000'),('b6aae460-726d-4945-9b8f-11bceb6e1f0e',0,'2024-11-17 19:31:46.000000',2,'29283702X','newManager','13919945T',NULL),('cfe53ab3-a8b2-40b7-829a-44bdb0c66362',0,'2024-11-17 19:34:18.000000',2,'29283702X','newManager','13919945T',NULL),('edfd85f3-31e3-4741-b713-d35e0df6b3fc',1,'2024-11-17 19:26:14.000000',1,'29283702X','newManager','13919945T','2024-11-10 19:28:07.000000'),('ee4c27ee-83b2-4f6b-b449-97de087d7ae6',0,'2024-11-17 19:55:32.000000',1,'29283702X','newManager','13919945T',NULL);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `address_city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_street` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_zip` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthdate` datetime(6) NOT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` bit(1) NOT NULL,
  `lastname` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `modification_date` datetime(6) DEFAULT NULL,
  `modification_username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `registration_date` datetime(6) NOT NULL,
  `second_lastname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('02262310F',NULL,NULL,NULL,NULL,NULL,'1977-02-10 00:00:00.000000',1,'dusheff@hotmail.com','','Huertas',NULL,NULL,'Lidia María','$2a$10$ZOwGdhbHD5OhtadFpkjKY.pNfpvgqDdJYcCDJKFbUJJHFUefaYuOG',NULL,'2024-10-28 00:00:00.000000','Cejudo',NULL),('05959715R',NULL,NULL,NULL,NULL,NULL,'1976-10-30 00:00:00.000000',1,'dusheff@hotmail.com','','Usheff',NULL,NULL,'Damián','$2a$10$mRX05pSePQGx9tun.srtdewjdrwq8A8LYmxy/hbupSxTSs0yXj7pi',NULL,'2024-10-28 00:00:00.000000','Vellianitis',NULL),('13919945T',NULL,NULL,NULL,NULL,NULL,'1980-04-23 00:00:00.000000',1,'dusheff@hotmail.com','','Álvarez',NULL,NULL,'Alberto','$2a$10$hJHJv1YYG5Jh9OG9TyFgqezLhI.Wr2I9lz6yYGho9wQrDG4cY93tG',NULL,'2024-11-04 00:00:00.000000',NULL,NULL),('29283702X','Madrid','8','','Calle San Ignacio','28015','1984-09-20 00:00:00.000000',1,'dusheff@hotmail.com','','Sánchez','2024-12-09 23:07:08.000000','05959715R','Elena','$2a$10$hqU7aFRprjCKSBPxKZxVcOw1ZvlxF8f1aa14U3J5ir7zEbstROXTK','654654654','2024-10-28 10:26:37.000000','SegundoApellido4','female');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_document_manager`
--

DROP TABLE IF EXISTS `user_document_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_document_manager` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime(6) NOT NULL,
  `delete_date` datetime(6) DEFAULT NULL,
  `extension` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `filename` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `path` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_document_manager`
--

LOCK TABLES `user_document_manager` WRITE;
/*!40000 ALTER TABLE `user_document_manager` DISABLE KEYS */;
INSERT INTO `user_document_manager` VALUES (16,'2024-11-03 18:33:58.000000','2024-11-03 18:56:09.000000','.jpg','UFBS3882.jpg',NULL,'files\\userPhotos\\29283702X','29283702X','\0'),(17,'2024-11-03 18:56:10.000000',NULL,'.jpg','IMG20171028211356.jpg',NULL,'files\\userPhotos\\29283702X','29283702X',''),(18,'2024-11-04 14:58:23.000000',NULL,'.jpg','TTCK4315.jpg',NULL,'files\\userPhotos\\02262310F','02262310F',''),(42,'2024-11-11 19:31:09.000000','2024-11-11 19:51:24.000000','.JPG','IMG0300.JPG',NULL,'files\\userPhotos\\13919945T','13919945T','\0'),(43,'2024-11-11 19:51:24.000000',NULL,'.jpeg','WhatsAppImage20200612at13.43.051.jpeg',NULL,'files\\userPhotos\\13919945T','13919945T','');
/*!40000 ALTER TABLE `user_document_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` bigint(20) NOT NULL,
  `role` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `UKadnyt6agwl65jnnokuvnskhn2` (`role`,`username`),
  KEY `FKnircs1pyebpo0eucojumm0aed` (`username`),
  CONSTRAINT `FKnircs1pyebpo0eucojumm0aed` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (2,'ROLE_ADMIN','02262310F'),(4,'ROLE_ADMIN','13919945T'),(3,'ROLE_ROOT','05959715R'),(47,'ROLE_USER','29283702X');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'gymdaus'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 22:32:36
