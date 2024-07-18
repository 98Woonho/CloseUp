CREATE DATABASE  IF NOT EXISTS `close_up` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `close_up`;

-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: close_up
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `board_code` varchar(30) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` longtext,
  `view` int DEFAULT NULL,
  `written_at` datetime DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `board_code` (`board_code`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`board_code`) REFERENCES `board` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_file`
--

DROP TABLE IF EXISTS `article_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `data` longblob,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `article_file_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_file`
--

LOCK TABLES `article_file` WRITE;
/*!40000 ALTER TABLE `article_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_recommendation`
--

DROP TABLE IF EXISTS `article_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_recommendation` (
  `user_id` varchar(255) NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`article_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `article_recommendation_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_recommendation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_recommendation`
--

LOCK TABLES `article_recommendation` WRITE;
/*!40000 ALTER TABLE `article_recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `code` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `career`
--

DROP TABLE IF EXISTS `career`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `career` (
  `expert_nickname` varchar(255) NOT NULL,
  `information` varchar(100) NOT NULL,
  PRIMARY KEY (`expert_nickname`),
  CONSTRAINT `career_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `career`
--

LOCK TABLES `career` WRITE;
/*!40000 ALTER TABLE `career` DISABLE KEYS */;
/*!40000 ALTER TABLE `career` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `code` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chat_room_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `written_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `chat_room_id` (`chat_room_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `chat_message_ibfk_1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_message_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `expert_nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `expert_nickname` (`expert_nickname`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `chat_room_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_room_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `comment_id` bigint DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `written_at` datetime DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `comment_id` (`comment_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert`
--

DROP TABLE IF EXISTS `expert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert` (
  `nickname` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `introduction` longtext,
  `zipcode` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nickname`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `expert_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert`
--

LOCK TABLES `expert` WRITE;
/*!40000 ALTER TABLE `expert` DISABLE KEYS */;
INSERT INTO `expert` VALUES ('ExpertUser1','test1','I am an expert in the field of technology.','12345','123 Main Street','Apartment 1A'),('ExpertUser10','test10','I am knowledgeable in the field of information technology.','65432','654 Oak Drive','Townhouse 12'),('ExpertUser2','test2','I have extensive experience in business consulting.','67890','456 Oak Avenue','Suite 201'),('ExpertUser3','test3','My expertise lies in the field of healthcare.','54321','789 Elm Road','Building C'),('ExpertUser4','test4','I am knowledgeable in the field of education.','09876','321 Pine Lane','Room 305'),('ExpertUser5','test5','I specialize in the field of finance.','98765','654 Maple Drive','Townhouse 7'),('ExpertUser6','test6','My expertise is in the field of marketing.','43210','987 Oak Street','Apartment 2B'),('ExpertUser7','test7','I have extensive experience in the field of law.','76543','456 Elm Avenue','Suite 101'),('ExpertUser8','test8','My expertise lies in the field of design.','21098','789 Pine Road','Building D'),('ExpertUser9','test9','I specialize in the field of human resources.','87654','321 Maple Lane','Room 405');
/*!40000 ALTER TABLE `expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_detail`
--

DROP TABLE IF EXISTS `expert_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_detail` (
  `expert_nickname` varchar(255) NOT NULL,
  `category` varchar(30) NOT NULL,
  `information` varchar(255) NOT NULL,
  PRIMARY KEY (`expert_nickname`,`category`,`information`),
  CONSTRAINT `expert_detail_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_detail`
--

LOCK TABLES `expert_detail` WRITE;
/*!40000 ALTER TABLE `expert_detail` DISABLE KEYS */;
INSERT INTO `expert_detail` VALUES ('ExpertUser1','ability','Adaptability'),('ExpertUser1','ability','Critical Thinking'),('ExpertUser1','ability','Problem-solving'),('ExpertUser1','expertise','Innovation'),('ExpertUser1','expertise','Research'),('ExpertUser1','expertise','Technology'),('ExpertUser1','skill','Data Analysis'),('ExpertUser1','skill','Programming'),('ExpertUser1','skill','Project Management'),('ExpertUser10','career','Client Engagement'),('ExpertUser10','career','IT Consulting'),('ExpertUser10','career','Project Management'),('ExpertUser10','expertise','Information Technology'),('ExpertUser10','expertise','IT Consulting'),('ExpertUser10','expertise','Software Development'),('ExpertUser10','skill','Agile Methodologies'),('ExpertUser10','skill','Cloud Computing'),('ExpertUser10','skill','Software Development'),('ExpertUser2','career','Consulting Experience'),('ExpertUser2','career','Industry Expertise'),('ExpertUser2','career','Leadership'),('ExpertUser2','expertise','Business Consulting'),('ExpertUser2','expertise','Change Management'),('ExpertUser2','expertise','Strategic Planning'),('ExpertUser2','skill','Client Relationship'),('ExpertUser2','skill','Stakeholder Management'),('ExpertUser2','skill','Strategic Planning'),('ExpertUser3','ability','Analytical Skills'),('ExpertUser3','ability','Attention to Detail'),('ExpertUser3','ability','Patient Care'),('ExpertUser3','expertise','Healthcare'),('ExpertUser3','expertise','Medical Research'),('ExpertUser3','expertise','Patient Care'),('ExpertUser3','skill','Clinical Trials'),('ExpertUser3','skill','Medical Research'),('ExpertUser3','skill','Patient Diagnosis'),('ExpertUser4','career','Curriculum Evaluation'),('ExpertUser4','career','Educational Leadership'),('ExpertUser4','career','Teaching Experience'),('ExpertUser4','expertise','Curriculum Development'),('ExpertUser4','expertise','Education'),('ExpertUser4','expertise','Teaching Methodology'),('ExpertUser4','skill','Curriculum Development'),('ExpertUser4','skill','Instructional Design'),('ExpertUser4','skill','Student Engagement'),('ExpertUser5','ability','Decision Making'),('ExpertUser5','ability','Problem-solving'),('ExpertUser5','ability','Risk Management'),('ExpertUser5','expertise','Finance'),('ExpertUser5','expertise','Financial Analysis'),('ExpertUser5','expertise','Risk Management'),('ExpertUser5','skill','Financial Analysis'),('ExpertUser5','skill','Financial Modeling'),('ExpertUser5','skill','Investment Strategy'),('ExpertUser6','career','Analytics Interpretation'),('ExpertUser6','career','Brand Management'),('ExpertUser6','career','Campaign Planning'),('ExpertUser6','expertise','Brand Management'),('ExpertUser6','expertise','Digital Marketing'),('ExpertUser6','expertise','Marketing'),('ExpertUser6','skill','Content Creation'),('ExpertUser6','skill','Digital Marketing'),('ExpertUser6','skill','Social Media Marketing'),('ExpertUser7','ability','Attention to Detail'),('ExpertUser7','ability','Critical Thinking'),('ExpertUser7','ability','Litigation'),('ExpertUser7','expertise','Law'),('ExpertUser7','expertise','Legal Research'),('ExpertUser7','expertise','Litigation'),('ExpertUser7','skill','Contract Drafting'),('ExpertUser7','skill','Legal Research'),('ExpertUser7','skill','Negotiation'),('ExpertUser8','career','Design Thinking'),('ExpertUser8','career','Design Trends'),('ExpertUser8','career','UI/UX Design'),('ExpertUser8','expertise','Design'),('ExpertUser8','expertise','Graphic Design'),('ExpertUser8','expertise','UI/UX Design'),('ExpertUser8','skill','Graphic Design'),('ExpertUser8','skill','Prototyping'),('ExpertUser8','skill','Typography'),('ExpertUser9','ability','Coaching'),('ExpertUser9','ability','Conflict Resolution'),('ExpertUser9','ability','Employee Development'),('ExpertUser9','expertise','Employee Development'),('ExpertUser9','expertise','Human Resources'),('ExpertUser9','expertise','Talent Acquisition'),('ExpertUser9','skill','Performance Management'),('ExpertUser9','skill','Talent Acquisition'),('ExpertUser9','skill','Training Development');
/*!40000 ALTER TABLE `expert_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_portfolio`
--

DROP TABLE IF EXISTS `expert_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expert_nickname` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext,
  PRIMARY KEY (`id`),
  KEY `expert_nickname` (`expert_nickname`),
  CONSTRAINT `expert_portfolio_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_portfolio`
--

LOCK TABLES `expert_portfolio` WRITE;
/*!40000 ALTER TABLE `expert_portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_portfolio_file`
--

DROP TABLE IF EXISTS `expert_portfolio_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_portfolio_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expert_portfolio_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `size` int DEFAULT NULL,
  `data` longblob,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `expert_portfolio_id` (`expert_portfolio_id`),
  CONSTRAINT `expert_portfolio_file_ibfk_1` FOREIGN KEY (`expert_portfolio_id`) REFERENCES `expert_portfolio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_portfolio_file`
--

LOCK TABLES `expert_portfolio_file` WRITE;
/*!40000 ALTER TABLE `expert_portfolio_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert_portfolio_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_service`
--

DROP TABLE IF EXISTS `expert_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `expert_nickname` varchar(255) DEFAULT NULL,
  `category_code` varchar(30) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_code` (`category_code`),
  KEY `expert_nickname` (`expert_nickname`),
  CONSTRAINT `expert_service_ibfk_1` FOREIGN KEY (`category_code`) REFERENCES `category` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `expert_service_ibfk_2` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_service`
--

LOCK TABLES `expert_service` WRITE;
/*!40000 ALTER TABLE `expert_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `is_suspended` tinyint(1) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('test1','$2a$10$Nzv9rV2Q.o3MrJJa2Xi2JOhoQws6UA4nS5mkXNWzDbwm50.YH3.Vq','이운호','',0,'ROLE_USER'),('test2','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','박정우','',NULL,'ROLE_EXPERT'),('test3','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','이성훈',NULL,NULL,'ROLE_ADMIN'),('test4','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','길보령',NULL,NULL,'ROLE_EXPERT');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish` (
  `user_id` varchar(255) NOT NULL,
  `expert_nickname` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`expert_nickname`),
  KEY `expert_nickname` (`expert_nickname`),
  CONSTRAINT `wish_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`),
  CONSTRAINT `wish_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-18 16:07:26
