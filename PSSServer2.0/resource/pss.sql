-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2015 年 01 月 03 日 11:00
-- 服务器版本: 5.5.16
-- PHP 版本: 5.4.0beta2-dev

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: 'pss'
--

-- --------------------------------------------------------

--
-- 表的结构 'account'
--

CREATE TABLE IF NOT EXISTS account (
  `name` varchar(30) NOT NULL,
  sum int(11) NOT NULL,
  createddate datetime NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'accounttransferlist'
--

CREATE TABLE IF NOT EXISTS accounttransferlist (
  receipts_id varchar(30) NOT NULL,
  account_name varchar(30) NOT NULL,
  sum int(11) NOT NULL,
  remarks text NOT NULL,
  PRIMARY KEY (receipts_id,account_name),
  KEY receipts_id (receipts_id),
  KEY account_id (account_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'alarmorder'
--

CREATE TABLE IF NOT EXISTS alarmorder (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  comm_id int(10) unsigned NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY comm_id_2 (comm_id,createddate),
  KEY comm_id (comm_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- 表的结构 'category'
--

CREATE TABLE IF NOT EXISTS category (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  parent_id int(10) unsigned DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  hascommodity int(11) unsigned NOT NULL DEFAULT '0',
  hascategory int(11) unsigned NOT NULL DEFAULT '0',
  createddate datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=63 ;

-- --------------------------------------------------------

--
-- 表的结构 'commodity'
--

CREATE TABLE IF NOT EXISTS commodity (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  stockamount int(11) NOT NULL,
  purchasingprice int(11) NOT NULL,
  sellingprice int(11) NOT NULL,
  recentpurchasingprice int(11) NOT NULL,
  recentsellingprice int(11) NOT NULL,
  category_id int(10) unsigned NOT NULL,
  warningline int(10) unsigned NOT NULL DEFAULT '100',
  createddate datetime NOT NULL,
  PRIMARY KEY (id),
  KEY category_id (category_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- 表的结构 'customer'
--

CREATE TABLE IF NOT EXISTS customer (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` enum('进货商','销售商') NOT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `name` varchar(30) NOT NULL,
  address varchar(30) NOT NULL,
  phonenumber varchar(30) NOT NULL,
  postcode int(11) NOT NULL,
  email varchar(30) DEFAULT NULL,
  amountreceivable int(11) NOT NULL DEFAULT '0',
  receivable int(11) NOT NULL DEFAULT '0',
  payable int(11) NOT NULL DEFAULT '0',
  defaultsalesman varchar(30) NOT NULL,
  createddate datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- 表的结构 'giftorder'
--

CREATE TABLE IF NOT EXISTS giftorder (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  comm_id int(10) unsigned NOT NULL,
  amount int(11) NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL DEFAULT '草稿',
  PRIMARY KEY (id),
  UNIQUE KEY comm_id_2 (comm_id,createddate),
  KEY comm_id (comm_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- 表的结构 'inventory'
--

CREATE TABLE IF NOT EXISTS inventory (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  batch datetime NOT NULL,
  comminfo blob NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=123 ;

-- --------------------------------------------------------

--
-- 表的结构 'lossoverfloworder'
--

CREATE TABLE IF NOT EXISTS lossoverfloworder (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  comm_id int(10) unsigned NOT NULL,
  amount int(10) unsigned NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL DEFAULT '草稿',
  `type` enum('报溢单','报损单') NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY comm_id_2 (comm_id,createddate),
  KEY comm_id (comm_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- 表的结构 'paymentitem'
--

CREATE TABLE IF NOT EXISTS paymentitem (
  pay_id varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  sum int(11) NOT NULL,
  remarks text NOT NULL,
  PRIMARY KEY (pay_id,`name`),
  KEY pay_id (pay_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'paymentorder'
--

CREATE TABLE IF NOT EXISTS paymentorder (
  id varchar(30) NOT NULL,
  customer_id int(10) unsigned NOT NULL,
  user_id int(10) unsigned NOT NULL,
  account_name varchar(30) NOT NULL,
  sum int(11) NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL DEFAULT '草稿',
  PRIMARY KEY (id),
  KEY user_id (user_id),
  KEY customer_id (customer_id),
  KEY account_name (account_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'purchasingorder'
--

CREATE TABLE IF NOT EXISTS purchasingorder (
  id varchar(30) NOT NULL,
  customer_id int(11) unsigned NOT NULL,
  user_id int(10) unsigned NOT NULL,
  salesman varchar(30) NOT NULL,
  remarks text NOT NULL,
  sum int(11) NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL DEFAULT '草稿',
  createddate datetime DEFAULT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'purchasingreturnorder'
--

CREATE TABLE IF NOT EXISTS purchasingreturnorder (
  id varchar(30) NOT NULL,
  customer_id int(11) unsigned NOT NULL,
  user_id int(11) unsigned NOT NULL,
  salesman varchar(30) NOT NULL,
  remarks text NOT NULL,
  sum int(11) NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'receiptsorder'
--

CREATE TABLE IF NOT EXISTS receiptsorder (
  id varchar(30) NOT NULL,
  customer_id int(10) unsigned NOT NULL,
  user_id int(10) unsigned NOT NULL,
  sum int(11) NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL DEFAULT '草稿',
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'salesorder'
--

CREATE TABLE IF NOT EXISTS salesorder (
  id varchar(30) NOT NULL,
  customer_id int(11) unsigned NOT NULL,
  salesman varchar(30) NOT NULL,
  user_id int(10) unsigned NOT NULL,
  sumbeforediscount int(11) NOT NULL,
  discount int(11) NOT NULL,
  voucher int(11) NOT NULL,
  sumafterdiscount int(11) NOT NULL,
  remarks text NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'salesreturnorder'
--

CREATE TABLE IF NOT EXISTS salesreturnorder (
  id varchar(30) NOT NULL,
  customer_id int(11) unsigned NOT NULL,
  salesman varchar(30) NOT NULL,
  user_id int(10) unsigned NOT NULL,
  sumbeforediscount int(11) NOT NULL,
  discount int(11) NOT NULL,
  voucher int(11) NOT NULL,
  sumafterdiscount int(11) NOT NULL,
  remarks text NOT NULL,
  `status` enum('草稿','已提交','已审批') NOT NULL,
  createddate datetime NOT NULL,
  passdate datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'storage'
--

CREATE TABLE IF NOT EXISTS `storage` (
  id int(11) NOT NULL,
  comm_id int(11) NOT NULL,
  commname varchar(30) NOT NULL,
  amount int(11) NOT NULL,
  sum int(11) NOT NULL,
  `date` datetime NOT NULL,
  `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'storageinlist'
--

CREATE TABLE IF NOT EXISTS storageinlist (
  pur_id varchar(30) NOT NULL,
  comm_id int(10) unsigned NOT NULL,
  amount int(10) unsigned NOT NULL,
  price int(10) unsigned NOT NULL,
  sum int(10) unsigned NOT NULL,
  remarks text NOT NULL,
  createddate datetime NOT NULL,
  PRIMARY KEY (pur_id,comm_id),
  KEY pur_id (pur_id),
  KEY comm_id (comm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'storageinreturnlist'
--

CREATE TABLE IF NOT EXISTS storageinreturnlist (
  purreturn_id varchar(30) NOT NULL,
  comm_id int(10) unsigned NOT NULL,
  amount int(10) unsigned NOT NULL,
  price int(10) unsigned NOT NULL,
  sum int(10) unsigned NOT NULL,
  remarks text NOT NULL,
  createddate datetime NOT NULL,
  PRIMARY KEY (purreturn_id,comm_id),
  KEY purreturn_id (purreturn_id),
  KEY comm_id (comm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'storageoutlist'
--

CREATE TABLE IF NOT EXISTS storageoutlist (
  sales_id varchar(30) NOT NULL,
  comm_id int(10) unsigned NOT NULL,
  amount int(10) unsigned NOT NULL,
  price int(10) unsigned NOT NULL,
  sum int(10) unsigned NOT NULL,
  remarks text NOT NULL,
  createddate datetime NOT NULL,
  PRIMARY KEY (sales_id,comm_id),
  KEY sales_id (sales_id),
  KEY comm_id (comm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'storageoutreturnlist'
--

CREATE TABLE IF NOT EXISTS storageoutreturnlist (
  salesreturn_id varchar(30) NOT NULL,
  comm_id int(10) unsigned NOT NULL,
  amount int(10) unsigned NOT NULL,
  price int(10) unsigned NOT NULL,
  sum int(10) unsigned NOT NULL,
  remarks text NOT NULL,
  createddate datetime NOT NULL,
  PRIMARY KEY (salesreturn_id,comm_id),
  KEY comm_id (comm_id),
  KEY salesreturn_id (salesreturn_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 'user'
--

CREATE TABLE IF NOT EXISTS `user` (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  username varchar(10) NOT NULL,
  `password` varchar(16) NOT NULL,
  usertype enum('库存管理人员','进货销售人员','总经理','财务人员') NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username,usertype)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=72 ;

--
-- 限制导出的表
--

--
-- 限制表 `accounttransferlist`
--
ALTER TABLE `accounttransferlist`
  ADD CONSTRAINT accounttransferlist_ibfk_1 FOREIGN KEY (receipts_id) REFERENCES receiptsorder (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `alarmorder`
--
ALTER TABLE `alarmorder`
  ADD CONSTRAINT alarmorder_ibfk_1 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `commodity`
--
ALTER TABLE `commodity`
  ADD CONSTRAINT commodity_ibfk_1 FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `giftorder`
--
ALTER TABLE `giftorder`
  ADD CONSTRAINT giftorder_ibfk_1 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `lossoverfloworder`
--
ALTER TABLE `lossoverfloworder`
  ADD CONSTRAINT lossoverfloworder_ibfk_1 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT lossoverfloworder_ibfk_2 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `paymentitem`
--
ALTER TABLE `paymentitem`
  ADD CONSTRAINT paymentitem_ibfk_1 FOREIGN KEY (pay_id) REFERENCES paymentorder (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `paymentorder`
--
ALTER TABLE `paymentorder`
  ADD CONSTRAINT paymentorder_ibfk_1 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT paymentorder_ibfk_2 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT paymentorder_ibfk_3 FOREIGN KEY (account_name) REFERENCES account (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `purchasingorder`
--
ALTER TABLE `purchasingorder`
  ADD CONSTRAINT purchasingorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT purchasingorder_ibfk_2 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT purchasingorder_ibfk_3 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `purchasingreturnorder`
--
ALTER TABLE `purchasingreturnorder`
  ADD CONSTRAINT purchasingreturnorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT purchasingreturnorder_ibfk_2 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `receiptsorder`
--
ALTER TABLE `receiptsorder`
  ADD CONSTRAINT receiptsorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT receiptsorder_ibfk_2 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `salesorder`
--
ALTER TABLE `salesorder`
  ADD CONSTRAINT salesorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT salesorder_ibfk_2 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `salesreturnorder`
--
ALTER TABLE `salesreturnorder`
  ADD CONSTRAINT salesreturnorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT salesreturnorder_ibfk_2 FOREIGN KEY (user_id) REFERENCES `user` (id);

--
-- 限制表 `storageinlist`
--
ALTER TABLE `storageinlist`
  ADD CONSTRAINT storageinlist_ibfk_1 FOREIGN KEY (pur_id) REFERENCES purchasingorder (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT storageinlist_ibfk_2 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `storageinreturnlist`
--
ALTER TABLE `storageinreturnlist`
  ADD CONSTRAINT storageinreturnlist_ibfk_1 FOREIGN KEY (purreturn_id) REFERENCES purchasingreturnorder (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT storageinreturnlist_ibfk_2 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `storageoutlist`
--
ALTER TABLE `storageoutlist`
  ADD CONSTRAINT storageoutlist_ibfk_1 FOREIGN KEY (sales_id) REFERENCES salesorder (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT storageoutlist_ibfk_2 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `storageoutreturnlist`
--
ALTER TABLE `storageoutreturnlist`
  ADD CONSTRAINT storageoutreturnlist_ibfk_1 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT storageoutreturnlist_ibfk_2 FOREIGN KEY (salesreturn_id) REFERENCES salesreturnorder (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT storageoutreturnlist_ibfk_3 FOREIGN KEY (comm_id) REFERENCES commodity (id) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
