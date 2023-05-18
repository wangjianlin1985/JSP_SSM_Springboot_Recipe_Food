/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : cooking_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-25 15:35:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_cookclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_cookclass`;
CREATE TABLE `t_cookclass` (
  `cookClassId` int(11) NOT NULL auto_increment COMMENT '分类id',
  `className` varchar(20) NOT NULL COMMENT '分类名称',
  `cookClassDesc` varchar(800) NOT NULL COMMENT '烹饪分类介绍',
  PRIMARY KEY  (`cookClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cookclass
-- ----------------------------
INSERT INTO `t_cookclass` VALUES ('1', '川菜', '四川的菜');
INSERT INTO `t_cookclass` VALUES ('2', '湘菜', '湖南菜');
INSERT INTO `t_cookclass` VALUES ('3', '粤菜', '粤菜');
INSERT INTO `t_cookclass` VALUES ('4', '东北菜', '东北菜');
INSERT INTO `t_cookclass` VALUES ('5', '江西菜', '江西菜');
INSERT INTO `t_cookclass` VALUES ('6', '鲁菜', '鲁菜');
INSERT INTO `t_cookclass` VALUES ('7', '浙菜', '浙菜');
INSERT INTO `t_cookclass` VALUES ('8', '闽菜', '闽菜');

-- ----------------------------
-- Table structure for `t_cookcollect`
-- ----------------------------
DROP TABLE IF EXISTS `t_cookcollect`;
CREATE TABLE `t_cookcollect` (
  `collectId` int(11) NOT NULL auto_increment COMMENT '收藏id',
  `cookingObj` int(11) NOT NULL COMMENT '收藏菜谱',
  `userObj` varchar(30) NOT NULL COMMENT '收藏用户',
  `collectionTime` varchar(20) default NULL COMMENT '收藏时间',
  PRIMARY KEY  (`collectId`),
  KEY `cookingObj` (`cookingObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_cookcollect_ibfk_1` FOREIGN KEY (`cookingObj`) REFERENCES `t_cooking` (`cookingId`),
  CONSTRAINT `t_cookcollect_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cookcollect
-- ----------------------------
INSERT INTO `t_cookcollect` VALUES ('1', '1', 'user1', '2019-08-20 15:22:23');
INSERT INTO `t_cookcollect` VALUES ('2', '1', 'user2', '2019-08-24 16:33:57');
INSERT INTO `t_cookcollect` VALUES ('3', '2', 'user1', '2019-08-25 15:30:11');

-- ----------------------------
-- Table structure for `t_cookcomment`
-- ----------------------------
DROP TABLE IF EXISTS `t_cookcomment`;
CREATE TABLE `t_cookcomment` (
  `commentId` int(11) NOT NULL auto_increment COMMENT '评论id',
  `cookingObj` int(11) NOT NULL COMMENT '被评菜谱',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `userObj` varchar(30) NOT NULL COMMENT '评论用户',
  `commentTime` varchar(20) default NULL COMMENT '评论时间',
  PRIMARY KEY  (`commentId`),
  KEY `cookingObj` (`cookingObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_cookcomment_ibfk_1` FOREIGN KEY (`cookingObj`) REFERENCES `t_cooking` (`cookingId`),
  CONSTRAINT `t_cookcomment_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cookcomment
-- ----------------------------
INSERT INTO `t_cookcomment` VALUES ('1', '1', '非常劲道', 'user1', '2019-08-20 15:13:55');
INSERT INTO `t_cookcomment` VALUES ('2', '1', '看起来很好吃的样子', 'user2', '2019-08-24 16:16:46');
INSERT INTO `t_cookcomment` VALUES ('3', '5', '喜欢吃鸡肉哦', 'user2', '2019-08-25 15:29:23');
INSERT INTO `t_cookcomment` VALUES ('4', '2', '这个菜菜不错，喜欢吃', 'user2', '2019-08-25 15:29:46');
INSERT INTO `t_cookcomment` VALUES ('5', '2', '我也挺喜欢吃这个菜菜的！', 'user1', '2019-08-25 15:30:08');

-- ----------------------------
-- Table structure for `t_cooking`
-- ----------------------------
DROP TABLE IF EXISTS `t_cooking`;
CREATE TABLE `t_cooking` (
  `cookingId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `cookClassObj` int(11) NOT NULL COMMENT '菜谱分类',
  `cookingName` varchar(60) NOT NULL COMMENT '菜谱名称',
  `cookingPhoto` varchar(60) NOT NULL COMMENT '菜谱图片',
  `gongxiao` varchar(100) NOT NULL COMMENT '菜谱功效',
  `cookingDesc` varchar(8000) NOT NULL COMMENT '菜谱介绍',
  `yongliao` varchar(5000) NOT NULL COMMENT '菜谱用料',
  `videoFile` varchar(60) NOT NULL COMMENT '做法视频',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`cookingId`),
  KEY `cookClassObj` (`cookClassObj`),
  CONSTRAINT `t_cooking_ibfk_1` FOREIGN KEY (`cookClassObj`) REFERENCES `t_cookclass` (`cookClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cooking
-- ----------------------------
INSERT INTO `t_cooking` VALUES ('1', '1', '干煸四季豆', 'upload/c4133ade-fcdc-44c7-9df4-b17c5f559185.jpg', '消食开胃,养胃消食,强壮骨骼,治贫血', '<p><strong style=\"margin: 0px; padding: 0px 3px; border: 0px; outline: 0px; font-size: 24px; vertical-align: baseline; background: rgb(255, 255, 255); color: rgb(221, 221, 221); font-family: arial; white-space: normal;\">“</strong><span style=\"color: rgb(102, 102, 102); font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">这道耳熟能详的传统川菜，是多少人去饭馆时必点的下饭菜和下酒菜。但为啥是饭馆必点呢，因为在家里不容易做啊！很多朋友尝试以后，都说失败率很高，要么四季豆“炸老”了，又焦又黑简直没眼看，还浪费好多油；要么焯熟的豆角表皮没有褶皱的感觉，后面再怎么炒也不够入味。其实啊，这道菜考验的是对火候的掌握，多一分少一分都不成。</span><br/><br/><span style=\"color: rgb(102, 102, 102); font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">豆角，是一种生吃会中毒但做熟了以后风味独特的食材。在家里做干煸四季豆，很多人会因为担心炒不熟豆角，而用大火一直猛炒，结果事半功倍，豆角的口感和外形都没法引起食欲。如何能让豆角香脆爽口而且不老入味呢？正确答案是：中小火煸炒。豆角要中小火煸炒到表皮褶皱，颜色翠绿；肉末也要中火煸炒到焦黄冒油，最后两者合体入味的时候，再改成大火“用力”炒上几分钟，一盘堪比饭店大厨的干煸四季豆就完成了。不用油炸，也不用焯水，一样能做出色香味俱全的美食</span></p>', '<p>主料四季豆400克猪肉馅50克葱1段姜1块蒜3-4瓣干辣椒5-6个花椒10颗</p><p>辅料黄记煌照烧汁1勺（15克）黄记煌藤椒汁1勺（15克）</p>', 'upload/d1673c42-dd51-4d50-aae2-665619ef22b3.mp4', '2019-08-20 17:01:46');
INSERT INTO `t_cooking` VALUES ('2', '1', '肉末鱼香茄子', 'upload/90b3b655-dc5d-46be-b63b-296a4f3a75bd.jpg', '减肥,开胃,抗衰老,软化血管,补钙补血,防癌', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">1.</em>长茄子，改刀成7、8厘米的长条</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">2.</em>锅里多放油，烧至约6、7成热，下茄条，炸约1分钟，炸透即可，捞出控油</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">3.</em>蒜末姜米葱花备用，泡红椒切碎，猪肉切成末，以水淀粉抓匀</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">4.</em>锅留底油，烧热，下肉末，炒散拨至一边，下葱姜蒜及红辣椒，炒香并出红油，和肉末一起炒散，倒入茄条，烹入少许老抽、生抽，加两勺白糖，翻炒均匀，加盐翻炒几下至汤汁收紧时，烹入少许香醋，翻勺出锅</p><p><br/></p>', '<p>主料茄子(紫皮，长)3根</p><p>辅料猪肉100g蒜6瓣姜3片</p><p>调料生抽1勺老抽适量香醋1勺白糖适量泡红椒适量</p><p><br/></p>', 'upload/d1673c42-dd51-4d50-aae2-665619ef22b3.mp4', '2019-08-25 14:56:49');
INSERT INTO `t_cooking` VALUES ('3', '1', '四川烧白', 'upload/c1a77469-d7bf-4d10-8f51-b9401d7aeb19.jpg', '补充营养', '<p><span style=\"color: rgb(102, 102, 102); font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">烧白讲究把五花肉煮好后肉片上涂上酱油，再煎到上色。有时往往爱偷懒，这次不偷懒，把肉皮面煎了再做，煎过的肉皮颜色漂亮，做出来的菜效果好。</span><strong style=\"margin: 0px; padding: 0px 3px; border: 0px; outline: 0px; font-size: 24px; vertical-align: baseline; background: rgb(255, 255, 255); color: rgb(221, 221, 221); font-family: arial; white-space: normal;\">”</strong></p>', '<p>主料五花肉300克梅干菜100克</p><p>辅料姜4片食盐4克白糖5克辣椒3个老抽15克黄酒20克花椒20粒八角1个酱油适量干辣椒4个植物油20克水适量细香葱1根</p>', 'upload/d1673c42-dd51-4d50-aae2-665619ef22b3.mp4', '2019-08-25 15:02:11');
INSERT INTO `t_cooking` VALUES ('4', '2', '豆瓣酱烧肥鱼', 'upload/961ccbbd-dd5f-4deb-b14f-753bfe67c632.jpg', '排毒,补血,健脾,增强体质,养肝养肾', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">1.</em>鲇鱼刮去涎液，去掉鳃和鳍，开膛去内脏，洗净</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">2.</em>从鱼身的中间剁断成头尾两段，将腹内脊骨稍剁开，用盐、料酒腌一下后洗净</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">3.</em>水发香菇去蒂洗净，和去壳洗净的冬笋都切成丝</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">4.</em>葱、姜、蒜切成末</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">5.</em>将油烧沸，把鲇鱼抹干水分，下入油锅炸到五成熟时捞出</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">6.</em>再将猪油烧沸，下入冬笋丝、冬菇丝、姜、蒜末和豆瓣辣酱，炒出香辣味，再放入鲇鱼、汤、酱油、糖和味精</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">7.</em>烧开后移用小火焖熟，用湿淀粉25克（淀粉13克加水12克）调稀勾芡</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">8.</em>装入鱼盘，撒上葱花，淋香油即成</p><p><br/></p>', '<p>主料鲶鱼1500克</p><p>辅料冬笋70克香菇25克</p><p>调料食盐10克酱油15克醋15克味精2克葱15克姜15克蒜15克料酒50克香油15克猪油(板油)100克豆瓣辣酱50克白砂糖10克豌豆淀粉13克</p>', 'upload/d1673c42-dd51-4d50-aae2-665619ef22b3.mp4', '2019-08-25 15:08:21');
INSERT INTO `t_cooking` VALUES ('5', '2', '胡萝卜炖鸡', 'upload/65f96e68-63a7-42a7-9532-d08dc2146daf.jpg', '明目,降糖,助消化', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">1.</em>整只鸡切一半，（一般我们都买一整只鸡，保证是活的）。另一半冰冻起来。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">2.</em>将鸡肉切小块。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">3.</em>锅中放入少量的油，油热后放入鸡块翻炒。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">4.</em>接着放入生姜，大蒜翻炒，去除腥味同时也提味。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">5.</em>将胡萝卜外皮薄薄的一层刮去，以免影响口感。将胡萝卜切成滚刀片。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">6.</em>放入适量的辣椒，待鸡肉变色后，炒出水分。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">7.</em>接着放入胡萝卜继续翻炒。放入适量的盐。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 10px 20px 10px 100px; border: 0px; outline: 0px; font-size: 14px; vertical-align: baseline; background: rgb(255, 255, 255); line-height: 24px; color: rgb(102, 102, 102); position: relative; font-family: Arial, Helvetica, sans-serif; white-space: normal;\"><em class=\"step\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; font-size: 50px; vertical-align: baseline; background: transparent; color: rgb(255, 50, 50); text-align: center; font-family: arial; position: absolute; left: 0px; top: -6px; height: 100px; width: 100px; display: block; line-height: 52px;\">8.</em>将炒好的鸡肉和胡萝卜倒入电压力锅中，放入适量的水，没过鸡肉为准。</p><p><br/></p>', '<p>主料鸡肉500g胡萝卜500g</p><p>调料食盐适量姜适量蒜适量调和油适量剁椒适量</p>', 'upload/d1673c42-dd51-4d50-aae2-665619ef22b3.mp4', '2019-08-25 15:14:07');

-- ----------------------------
-- Table structure for `t_learnrec`
-- ----------------------------
DROP TABLE IF EXISTS `t_learnrec`;
CREATE TABLE `t_learnrec` (
  `recId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `cookingObj` int(11) NOT NULL COMMENT '学会菜谱',
  `userObj` varchar(30) NOT NULL COMMENT '学习用户',
  `jifen` float NOT NULL COMMENT '获得积分',
  `learnTime` varchar(20) default NULL COMMENT '学会时间',
  PRIMARY KEY  (`recId`),
  KEY `cookingObj` (`cookingObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_learnrec_ibfk_1` FOREIGN KEY (`cookingObj`) REFERENCES `t_cooking` (`cookingId`),
  CONSTRAINT `t_learnrec_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_learnrec
-- ----------------------------
INSERT INTO `t_learnrec` VALUES ('1', '1', 'user1', '5', '2019-08-20 17:02:25');
INSERT INTO `t_learnrec` VALUES ('2', '1', 'user2', '5', '2019-08-24 16:33:59');
INSERT INTO `t_learnrec` VALUES ('3', '5', 'user2', '5', '2019-08-25 15:23:23');

-- ----------------------------
-- Table structure for `t_messageinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_messageinfo`;
CREATE TABLE `t_messageinfo` (
  `messageId` int(11) NOT NULL auto_increment COMMENT '消息id',
  `content` varchar(800) NOT NULL COMMENT '消息内容',
  `sender` varchar(30) NOT NULL COMMENT '发送人',
  `reciever` varchar(30) NOT NULL COMMENT '接收人',
  `sendTime` varchar(20) default NULL COMMENT '发送时间',
  `readState` varchar(20) NOT NULL COMMENT '阅读状态',
  PRIMARY KEY  (`messageId`),
  KEY `sender` (`sender`),
  KEY `reciever` (`reciever`),
  CONSTRAINT `t_messageinfo_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `t_userinfo` (`user_name`),
  CONSTRAINT `t_messageinfo_ibfk_2` FOREIGN KEY (`reciever`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_messageinfo
-- ----------------------------
INSERT INTO `t_messageinfo` VALUES ('1', '周末一起去撸串串啊', 'user1', 'user2', '2019-08-20 17:05:46', '已读');
INSERT INTO `t_messageinfo` VALUES ('2', '去撸吧！', 'user2', 'user1', '2019-08-20 17:06:09', '已读');
INSERT INTO `t_messageinfo` VALUES ('3', '你好啊 ', 'user1', 'user2', '2019-08-24 23:41:35', '已读');
INSERT INTO `t_messageinfo` VALUES ('4', '你好啊 我们比比厨艺如何？', 'user1', 'user2', '2019-08-24 23:43:05', '未读');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `jifen` float NOT NULL COMMENT '用户积分',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '王晓曦', '女', '2019-08-20', 'upload/ddab9f45-e41c-42eb-82a7-e771480e4d7c.jpg', '13980024352', 'wangqiang@163.com', '四川成都红星路', '5', '2019-08-20 17:00:36');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '张晓婷', '女', '2019-08-20', 'upload/2fe53fd4-f5b8-42ef-a311-7cb6534cf13a.jpg', '13908523422', 'xiaofen@163.com', '四川达州', '10', '2019-08-20 17:05:23');
INSERT INTO `t_userinfo` VALUES ('user3', '123', '王红', '女', '2019-08-15', 'upload/121002ac-9955-477e-98c8-ab4e64050434.jpg', '13085030834', 'wanghong@163.com', '四川广安射洪', '0', '2019-08-25 15:22:47');
