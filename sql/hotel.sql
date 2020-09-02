
-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `hotelId` int(11) DEFAULT '-1',
                          `couponType` int(11) NOT NULL,
                          `couponName` varchar(255) NOT NULL,
                          `target_money` int(11) DEFAULT NULL,
                          `discount` double DEFAULT NULL,
                          `status` int(11) DEFAULT NULL,
                          `start_time` datetime DEFAULT NULL,
                          `end_time` datetime DEFAULT NULL,
                          `discount_money` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
-- (2,'满500-100优惠',2,3,'满减优惠券',500,0,1,NULL,NULL,100) 一开始的discount是 0，现修改为 1
--
BEGIN;
/*!40000 ALTER TABLE `Coupon` DISABLE KEYS */;
INSERT INTO `Coupon` VALUES (1,'满200-200优惠',-1,3,'满减优惠券',200,1,1,NULL,NULL,200),(2,'满500-100优惠',2,3,'满减优惠券',500,1,1,NULL,NULL,100),(3,'生日8折优惠',-1,1,'生日优惠券',0,0.8,1,NULL,NULL,0),(4,'限时8折优惠',-1,4,'限时优惠券',0,0.8,1,'2020-06-12','2022-07-12',0),(5,'限时满200-100优惠',-1,4,'限时优惠券',200,1,1,'2020-05-11','2020-06-11',100),(6,'多间9折优惠',1,2,'多间优惠券',0,0.9,1,'2020-06-12','2022-07-12',0);
/*!40000 ALTER TABLE `Coupon` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `hotelName` varchar(255) NOT NULL,
                         `hotelDescription` varchar(255) DEFAULT NULL,
                         `address` varchar(255) DEFAULT NULL,
                         `bizRegion` varchar(255) DEFAULT NULL,
                         `hotelStar` varchar(255) DEFAULT NULL,
                         `phoneNum` int(11) DEFAULT NULL,
                         `rate` double DEFAULT NULL,
                         `manager_id` int(11) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'汉庭酒店','客官，这边请','江苏省南京市江宁区珠江路66号','XiDan','Four',1829373819,4.8,7),(4,'四季酒店','这里四季如春~','江苏省南通市如东县掘港西路66号','JueGang','Four',1329373819,4.8,7),(2,'儒家酒店','欢迎您入住','江苏省南京市鼓楼区珠江路268号','XiDan','Four',1829373819,4.8,6),(3,'桂圆酒店','欢迎您入住','江苏省南京市栖霞区珠江路268号','XiDan','Four',1829553719,4.8,6);
/*!40000 ALTER TABLE `Hotel` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `userId` int(11) DEFAULT NULL,
                             `hotelId` int(11) DEFAULT NULL,
                             `hotelName` varchar(255) DEFAULT NULL,
                             `checkInDate` varchar(255) DEFAULT NULL,
                             `checkOutDate` varchar(255) DEFAULT NULL,
                             `roomType` varchar(255) DEFAULT NULL,
                             `roomId` int(11) DEFAULT NULL,
                             `roomNum` int(255) DEFAULT NULL,
                             `peopleNum` int(255) DEFAULT NULL,
                             `haveChild` tinytext,
                             `createDate` varchar(255) DEFAULT NULL,
                             `cancellationDate` varchar(255) DEFAULT NULL,  -- personal
                             `price` decimal(65,0) DEFAULT NULL,
                             `clientName` varchar(255) DEFAULT NULL,
                             `phoneNumber` varchar(255) DEFAULT NULL,
                             `orderState` varchar(255) DEFAULT NULL,
                             `checkInTime` varchar(255) DEFAULT NULL,
                             `checkOutTime` varchar(255) DEFAULT NULL,
                             `errorDate` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

/*!40000 ALTER TABLE `OrderList` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderList` ENABLE KEYS */;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `price` double DEFAULT NULL,
                        `curNum` int(11) DEFAULT NULL,
                        `total` int(11) DEFAULT NULL,
                        `hotel_id` int(11) DEFAULT NULL,
                        `roomType` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES (2,199,20,20,1,'BigBed'),(3,299,30,30,1,'DoubleBed'),(4,399,3,3,1,'Family'),(5,122,0,0,1,'BigBed'),(6,399,10,10,2,'Family');
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) NOT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        `phonenumber` varchar(255) DEFAULT NULL,
                        `credit` double(255,0) DEFAULT NULL,
                        `usertype` varchar(255) DEFAULT NULL,
                        `birthday` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (3,'MUBxcS5jb20=','MTIzNDU2',NULL,NULL,NULL,'Manager',NULL),(4,'MTAxMjY4MUBxcS5jb20=','MTIzNDU2','5rWL6K+V5LiA5Y+3','MTIzNDU2Nzg5MTk=',100,'Client','2000-08-11'),(5,'MTIzQHFxLmNvbQ==','MTIzNDU2','5rWL6K+V5LqM5Y+3','MTIzNDU2Nzg5MTE=',100,'Client','2000-06-12'),(6,'MzMzQHFxLmNvbQ==','MTIzNDU2',NULL,NULL,NULL,'HotelManager',NULL),(7,'MzMzM0BxcS5jb20=','MTIzNDU2',NULL,NULL,NULL,'HotelManager',NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
COMMIT;





-- personal
-- Table structure for table `Credit`
--

DROP TABLE IF EXISTS `Credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credit`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `changeTime`   varchar(255) DEFAULT NULL,
    `userId`       int(11) NOT NULL,   -- 感觉也可以是 default NULL ？？？？
    `orderId`      int(11)  DEFAULT NULL,
    `actionType`    varchar(255) NOT NULL,
    `creditChange` double(255,0)  DEFAULT NULL,
    `creditResult` double(255,0)  DEFAULT NULL, -- 感觉也可以是 NOT NULL ？？？？
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Credit`
--

# BEGIN;
# /*!40000 ALTER TABLE `Credit` DISABLE KEYS */;
# INSERT INTO `Credit` VALUES (4,'2020-06-07 12:52:00',4,NULL,'订单执行',0,100);   -- 不知道datetime格式是不是这么写的
# /*!40000 ALTER TABLE `Credit` ENABLE KEYS */;
# COMMIT;




-- Table structure for table `Remark`
--

DROP TABLE IF EXISTS `Remark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Remark`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `user_id`       int(11) NOT NULL,
    `hotel_id`      int(11)  NOT NULL,
    `content`       varchar(255) NOT NULL,
    `star`          int(11) NOT NULL ,
    `remark_time`   varchar(255) NOT NULL ,
    `user_name`   varchar(255) NOT NULL ,
    PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Remark`
--

# BEGIN;
# /*!40000 ALTER TABLE `Remark` DISABLE KEYS */;
# INSERT INTO `Remark` VALUES (10,6,1),(9,6,2),(8,7,1),(7,7,3);
# /*!40000 ALTER TABLE `Remark` ENABLE KEYS */;
# COMMIT;





/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;
