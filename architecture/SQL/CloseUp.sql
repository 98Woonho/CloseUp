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
  KEY `FK-board-code-article-board_code` (`board_code`),
  KEY `FK-user-id-article-user_id` (`user_id`),
  CONSTRAINT `FK-board-code-article-board_code` FOREIGN KEY (`board_code`) REFERENCES `board` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-user-id-article-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  KEY `FK-article-id-article_file-article_id` (`article_id`),
  CONSTRAINT `FK-article-id-article_file-article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  KEY `FK-article-id-article_recommendation-article_id` (`article_id`),
  CONSTRAINT `FK-article-id-article_recommendation-article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-user-id-article_recommendation-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `expert_user_id` varchar(255) NOT NULL,
  `information` varchar(100) NOT NULL,
  PRIMARY KEY (`expert_user_id`),
  CONSTRAINT `FK-expert-user_id-career-expert_user_id` FOREIGN KEY (`expert_user_id`) REFERENCES `expert` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `chat_room` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `written_at` datetime DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK-user-id-chat_message-user_id` (`user_id`),
  CONSTRAINT `FK-user-id-chat_message-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `expert_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK-user-id-chat_room-user_id` (`user_id`),
  KEY `FK-expert-user_id-chat_room-expert_user_id` (`expert_user_id`),
  CONSTRAINT `FK-expert-user_id-chat_room-expert_user_id` FOREIGN KEY (`expert_user_id`) REFERENCES `expert` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-user-id-chat_room-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  KEY `FK-user-id-comment-user_id` (`user_id`),
  KEY `FK-article-id-comment-article_id` (`article_id`),
  KEY `FK-comment-id-comment-comment_id` (`comment_id`),
  CONSTRAINT `FK-article-id-comment-article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-comment-id-comment-comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-user-id-comment-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `user_id` varchar(255) NOT NULL,
  `introduction` longtext,
  `region` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert`
--

LOCK TABLES `expert` WRITE;
/*!40000 ALTER TABLE `expert` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_portfolio`
--

DROP TABLE IF EXISTS `expert_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expert_user_id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext,
  PRIMARY KEY (`id`),
  KEY `FK-expert-user_id-expert_portfolio-expert_user_id` (`expert_user_id`),
  CONSTRAINT `FK-expert-user_id-expert_portfolio-expert_user_id` FOREIGN KEY (`expert_user_id`) REFERENCES `expert` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `expert_service`
--

DROP TABLE IF EXISTS `expert_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expert_service` (
  `title` varchar(255) NOT NULL,
  `expert_user_id` varchar(255) NOT NULL,
  `category_code` varchar(30) NOT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`title`,`expert_user_id`),
  KEY `FK-expert-user_id-expert_service-expert_user_id` (`expert_user_id`),
  KEY `FK-category-code-expert_service-category_code` (`category_code`),
  CONSTRAINT `FK-category-code-expert_service-category_code` FOREIGN KEY (`category_code`) REFERENCES `category` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-expert-user_id-expert_service-expert_user_id` FOREIGN KEY (`expert_user_id`) REFERENCES `expert` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `name` varchar(30) NOT NULL,
  `expert_user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`name`,`expert_user_id`),
  KEY `FK-expert-user_id-skill-expert_user_id` (`expert_user_id`),
  CONSTRAINT `FK-expert-user_id-skill-expert_user_id` FOREIGN KEY (`expert_user_id`) REFERENCES `expert` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
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
  `address` varchar(255) DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
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
INSERT INTO `user` VALUES ('lkj1150','$2a$10$loG5elL2oeH4z5Oh68urseX11PR1JzonDfabfYwTBfMzYAOpM0L96','이운호','01095331150',NULL,NULL,0,'ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-12 14:17:15
