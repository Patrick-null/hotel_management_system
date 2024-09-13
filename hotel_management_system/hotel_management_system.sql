/*
 Navicat Premium Dump SQL

 Source Server         : Project
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : hotel_management_system

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 13/09/2024 15:32:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` int DEFAULT '1' COMMENT '0:管理员\n1:普通用户\n\n',
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` (`aid`, `username`, `password`, `role`, `avatar`) VALUES (1, 'admin', 'c3284d0f94606de1fd2af172aba15bf3', 0, NULL);
INSERT INTO `admin` (`aid`, `username`, `password`, `role`, `avatar`) VALUES (2, 'user', '0d8d5cd06832b29560745fe4e1b941cf', 1, NULL);
INSERT INTO `admin` (`aid`, `username`, `password`, `role`, `avatar`) VALUES (24, '1234', 'ec6a6536ca304edf844d1d248a4f08dc', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for facility
-- ----------------------------
DROP TABLE IF EXISTS `facility`;
CREATE TABLE `facility` (
  `fid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) DEFAULT NULL,
  `fnum` int DEFAULT '1',
  `fvalue` decimal(10,2) DEFAULT NULL,
  `favatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of facility
-- ----------------------------
BEGIN;
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (1, '空调', 1, 2888.00, '050f32007c5b45cd90ef5a81f6deb90c_8.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (2, '豪华单人床', 1, 2888.00, 'c64646c584584dac8a626556b5941fbd_abc.JPG');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (3, '单人房', 1, 1888.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (4, '电视', 1, 6999.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (5, '咖啡机', 1, 2999.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (6, '洗衣机', 1, 3888.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (7, '烘干机', 1, 4666.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (8, '洗漱套装', 1, 199.00, '88deea64423b4d74a42ffd0254485fba_1.jpg');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (10, '茶吧机', 1, 888.00, '8c7fba767faf420ab65cf97911ef51ad_abc.JPG');
INSERT INTO `facility` (`fid`, `fname`, `fnum`, `fvalue`, `favatar`) VALUES (11, '电脑', 1, 18888.00, '7594c969b3cf4f1392a5e4ddc4803f90_abc.JPG');
COMMIT;

-- ----------------------------
-- Table structure for guest
-- ----------------------------
DROP TABLE IF EXISTS `guest`;
CREATE TABLE `guest` (
  `gid` int NOT NULL AUTO_INCREMENT,
  `gname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ggender` varchar(1) DEFAULT NULL,
  `gno` varchar(17) NOT NULL,
  `gphone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gstart` date DEFAULT NULL,
  `gend` date DEFAULT NULL,
  `gstate` int NOT NULL DEFAULT '0' COMMENT '0: 已预订 1：已入住  2:已离店',
  `rid` int NOT NULL,
  `ono` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of guest
-- ----------------------------
BEGIN;
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (98, 'tom', '男', '123', '123', '2024-09-10', '2024-09-11', 0, 4, '1725951267760');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (113, '王2', '男', '222', '222', '2024-09-16', '2024-09-19', 0, 7, '1725954553233');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (114, '王1', '男', '111', '111', '2024-09-16', '2024-09-19', 0, 7, '1725954553233');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (115, '李九', '男', '999', '999', '2024-09-16', '2024-09-19', 0, 2, '1725954601635');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (116, '李八', '男', '888', '888', '2024-09-16', '2024-09-19', 0, 2, '1725954601635');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (163, '李五', '男', '5555', '5555', '2024-09-17', '2024-09-10', 0, 10, '1725954003390');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (164, '李六', '男', '666', '666', '2024-09-17', '2024-09-10', 0, 10, '1725954003390');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (165, '李七', '男', '777', '777', '2024-09-17', '2024-09-10', 0, 10, '1725954003390');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (169, 'p2', '男', '2132776', '32131', '2024-09-10', '2024-09-11', 1, 3, '1725957410881');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (170, 'Patrick', '男', '10010', '10010', '2024-09-10', '2024-09-11', 1, 3, '1725957410881');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (172, '李四', '男', '123123', '123123', '2024-09-17', '2024-09-18', 0, 10, '1725954003390');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (185, 'Patrick', '男', '10010', '10010', '2024-09-11', '2024-09-13', 0, 28, '1725978599534');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (186, '派大星', '男', '009909', '009909', '2024-09-15', '2024-09-17', 0, 5, '1726016811589');
INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gno`, `gphone`, `gstart`, `gend`, `gstate`, `rid`, `ono`) VALUES (187, 'Patrick', '男', '10010', '10010', '2024-09-15', '2024-09-17', 0, 5, '1726016811589');
COMMIT;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `no` int NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `aid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of info
-- ----------------------------
BEGIN;
INSERT INTO `info` (`id`, `name`, `gender`, `no`, `phone`, `addr`, `aid`) VALUES (1, 'Patrick', '男', 10010, '10010', '山东青岛', 2);
INSERT INTO `info` (`id`, `name`, `gender`, `no`, `phone`, `addr`, `aid`) VALUES (2, 'Admin', '男', 10005, '100101', '北京', 1);
INSERT INTO `info` (`id`, `name`, `gender`, `no`, `phone`, `addr`, `aid`) VALUES (7, '123', '男', 6565, '656', '656', 9);
INSERT INTO `info` (`id`, `name`, `gender`, `no`, `phone`, `addr`, `aid`) VALUES (10, 'llll', '男', 12341, '12341', '上海', 24);
COMMIT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `mid` int NOT NULL AUTO_INCREMENT,
  `mname` varchar(20) DEFAULT NULL COMMENT '会员姓名',
  `mgender` varchar(2) DEFAULT NULL COMMENT '会员性别',
  `mno` varchar(17) DEFAULT NULL COMMENT '会员身份账号',
  `mphone` varchar(11) DEFAULT NULL COMMENT '会员手机号',
  `mstate` int DEFAULT '1' COMMENT '是否被选中\n0:未选中\n1:被选中',
  `mstart` date DEFAULT NULL,
  `mend` date DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of member
-- ----------------------------
BEGIN;
INSERT INTO `member` (`mid`, `mname`, `mgender`, `mno`, `mphone`, `mstate`, `mstart`, `mend`) VALUES (33, '刘子兴', '男', '3213', '213', 0, NULL, NULL);
INSERT INTO `member` (`mid`, `mname`, `mgender`, `mno`, `mphone`, `mstate`, `mstart`, `mend`) VALUES (34, '3213', '女', '3213', '213', 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `ono` varchar(50) NOT NULL COMMENT '订单号',
  `gno` varchar(17) NOT NULL COMMENT '下单住客',
  `otime` datetime DEFAULT NULL COMMENT '下单时间',
  `ostate` int DEFAULT '0' COMMENT '0:订单未使用\n1:订单完成',
  `moneys` decimal(10,2) DEFAULT '0.00',
  `rid` int NOT NULL,
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (37, '1725951267760', '123', '2024-09-10 15:34:50', 0, 200.00, 4);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (41, '1725954553233', '111', '2024-09-10 15:49:35', 0, -900.00, 7);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (42, '1725954601635', '888', '2024-09-10 15:50:01', 0, 900.00, 2);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (58, '1725957410881', '10010', '2024-09-10 16:44:01', 1, 1000.00, 3);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (60, '1725954003390', '123123', '2024-09-10 16:23:20', 0, -7000.00, 10);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (73, '1725978599534', '10010', '2024-09-10 22:55:17', 0, 1000.00, 28);
INSERT INTO `orders` (`oid`, `ono`, `gno`, `otime`, `ostate`, `moneys`, `rid`) VALUES (74, '1726016811589', '10010', '2024-09-11 09:06:51', 0, 2000.00, 5);
COMMIT;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `rno` int NOT NULL COMMENT '房间位置',
  `ravatar` varchar(255) DEFAULT NULL,
  `rtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '房间类型：',
  `rnum` int DEFAULT NULL COMMENT '房间人数',
  `rprice` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `rstate` int DEFAULT '0' COMMENT '房间状态\n0:空闲\n1:已预订\n2:已入住',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of room
-- ----------------------------
BEGIN;
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (1, 1001, '88deea64423b4d74a42ffd0254485fba_1.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (2, 1002, 'b5a60a40190448949c92964bce84717f_2.jpeg', '双人间', 2, 300.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (3, 1003, 'ad22f37e396b475b8b1ac2f031c29aa7_3.jpg', '豪华套房', 3, 888.00, 2);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (4, 1004, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (5, 1005, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1000.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (6, 1006, 'b04bfd57b64b41f29845024b7852216d_6.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (7, 1007, '5fe06d1fe3cf4e27b2527d87fbd0713d_7.jpg', '双人间', 2, 300.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (8, 1008, 'b0a85408acad490bbaef32bc33588b3b_8.jpg', '豪华套房', 3, 500.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (9, 1009, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (10, 1010, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1000.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (11, 1011, 'b04bfd57b64b41f29845024b7852216d_6.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (12, 1012, 'b5a60a40190448949c92964bce84717f_2.jpeg', '双人间', 2, 300.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (13, 1013, 'b0a85408acad490bbaef32bc33588b3b_8.jpg', '豪华套房', 3, 500.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (14, 1014, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (15, 1015, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1000.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (16, 1016, 'b04bfd57b64b41f29845024b7852216d_6.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (17, 1017, 'b5a60a40190448949c92964bce84717f_2.jpeg', '双人间', 2, 300.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (18, 1018, 'b0a85408acad490bbaef32bc33588b3b_8.jpg', '豪华套房', 3, 500.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (19, 1019, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (20, 1020, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1000.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (21, 1021, 'b04bfd57b64b41f29845024b7852216d_6.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (22, 1022, 'b5a60a40190448949c92964bce84717f_2.jpeg', '双人间', 2, 300.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (23, 1023, 'b0a85408acad490bbaef32bc33588b3b_8.jpg', '豪华套房', 3, 500.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (24, 1024, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (25, 1025, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1000.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (26, 1026, 'b04bfd57b64b41f29845024b7852216d_6.jpg', '单人间', 1, 200.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (27, 1027, 'b5a60a40190448949c92964bce84717f_2.jpeg', '双人间', 2, 300.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (28, 1028, 'b0a85408acad490bbaef32bc33588b3b_8.jpg', '豪华套房', 3, 500.00, 1);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (29, 1029, 'b9666d859a8d4e489ce6d5abdd332235_4.jpeg', '标准间', 3, 250.00, 0);
INSERT INTO `room` (`rid`, `rno`, `ravatar`, `rtype`, `rnum`, `rprice`, `rstate`) VALUES (30, 1030, '85b0598abf704f3d9fc2fddddd7947e6_5.jpeg', '总统套房', 4, 1200.00, 0);
COMMIT;

-- ----------------------------
-- Table structure for room_facility
-- ----------------------------
DROP TABLE IF EXISTS `room_facility`;
CREATE TABLE `room_facility` (
  `rid` int DEFAULT NULL COMMENT '房间id',
  `fid` int DEFAULT NULL COMMENT '设施ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of room_facility
-- ----------------------------
BEGIN;
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (1, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (1, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (4, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (4, 3);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (4, 6);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (8, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (8, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (8, 3);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (8, 4);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (10, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (10, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (27, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (17, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (17, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (19, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (19, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (30, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (30, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (30, 6);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (30, 3);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (16, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (16, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (18, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (18, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 3);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 4);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 5);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 10);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (3, 11);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (5, 1);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (5, 2);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (5, 3);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (5, 10);
INSERT INTO `room_facility` (`rid`, `fid`) VALUES (5, 11);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
