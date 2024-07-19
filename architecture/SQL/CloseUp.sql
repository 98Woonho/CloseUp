-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 192.168.5.15    Database: close_up
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'free','test2','자유게시판','자유',1,'2024-07-18 16:23:03',NULL),(2,'expertRecommendation','test2','전문가','전문가',1,'2024-07-18 16:24:03',NULL),(3,'expertReview','test2','전문가 후기','후기',1,'2024-07-18 16:24:28',NULL),(4,'question','test2','간단한 질문','간단한 질문',1,'2024-07-18 16:24:57',NULL),(5,'expert','test2','전문가 자유','자유',1,'2024-07-18 16:25:21',NULL),(6,'free','test4','자유','자유\r\n',0,'2024-07-18 16:26:00',NULL),(7,'expertRecommendation','test4','전문가 추천','추천',0,'2024-07-18 16:26:23',NULL),(8,'expertReview','test4','전문가의 후기','전문가 후기\r\n',1,'2024-07-18 16:27:42',NULL);
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
INSERT INTO `article_recommendation` VALUES ('test2',1),('test4',1),('test2',3),('test2',4),('test2',5),('test4',5);
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
INSERT INTO `board` VALUES ('expert','전문가 자유 게시판'),('expertrecommendation','전문가 추천 게시판'),('expertReview','전문가의 후기 게시판'),('free','자유 게시판'),('question','간단한 질문 게시판');
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'test2',NULL,'자유','2024-07-18 16:23:42',NULL),(2,2,'test2',NULL,'전문가','2024-07-18 16:24:08',NULL),(3,3,'test2',NULL,'후기','2024-07-18 16:24:32',NULL),(4,4,'test2',NULL,'간단함','2024-07-18 16:25:04',NULL),(5,5,'test2',NULL,'자유','2024-07-18 16:25:25',NULL),(6,5,'test4',NULL,'좋습니다.','2024-07-18 16:29:21',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_recommendation`
--

DROP TABLE IF EXISTS `comment_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_recommendation` (
  `user_id` varchar(255) NOT NULL,
  `comment_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`comment_id`),
  KEY `FK-comment-id-comment_recommendation-comment_id` (`comment_id`),
  CONSTRAINT `FK-comment-id-comment_recommendation-comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK-user-id-comment_recommendation-user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_recommendation`
--

LOCK TABLES `comment_recommendation` WRITE;
/*!40000 ALTER TABLE `comment_recommendation` DISABLE KEYS */;
INSERT INTO `comment_recommendation` VALUES ('test2',1),('test2',2),('test2',3),('test2',4),('test2',5),('test4',5),('test4',6);
/*!40000 ALTER TABLE `comment_recommendation` ENABLE KEYS */;
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
  `profile_img` longblob,
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
INSERT INTO `expert` VALUES ('디자인마스터','test1','안녕하세요11',NULL,'중앙대로 366','코리아 IT 9층',NULL),('스프링부트','test3','스프링부트 잘해요',NULL,'공평로 105','노마즈하우스',NULL),('웹개발신','test2','소개입니다2',NULL,'동성로 1길 65','현풍닭칼국수',NULL);
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
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `name` varchar(30) NOT NULL,
  `expert_nickname` varchar(255) NOT NULL,
  PRIMARY KEY (`name`,`expert_nickname`),
  KEY `expert_nickname` (`expert_nickname`),
  CONSTRAINT `skill_ibfk_1` FOREIGN KEY (`expert_nickname`) REFERENCES `expert` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `is_suspended` tinyint(1) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  `profile_img` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('test1','$2a$10$Nzv9rV2Q.o3MrJJa2Xi2JOhoQws6UA4nS5mkXNWzDbwm50.YH3.Vq','이운호','',0,'ROLE_USER',NULL),('test2','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','박정우','',1,'ROLE_EXPERT',NULL),('test3','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','이성훈','01098583656',1,'ROLE_ADMIN',NULL),('test4','$2a$10$xrVGKyUCdUwPYI.pL53HBe3Q9RAe.FiMg6yg/C4a8oxA3LUDgCG4C','길보령',NULL,NULL,'ROLE_EXPERT',NULL);
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
  `expert_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`expert_id`),
  KEY `wish_ibfk_1_idx` (`expert_id`),
  CONSTRAINT `wish_ibfk_1` FOREIGN KEY (`expert_id`) REFERENCES `expert` (`user_id`),
  CONSTRAINT `wish_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
INSERT INTO `wish` VALUES ('test4','test2'),('test4','test3');
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

-- Dump completed on 2024-07-18 17:56:14
