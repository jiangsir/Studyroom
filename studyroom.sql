-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 建立日期: Sep 16, 2014, 12:58 PM
-- 伺服器版本: 5.1.44
-- PHP 版本: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 資料庫: `studyroom`
--

-- --------------------------------------------------------

--
-- 資料表格式： `appconfigs`
--

CREATE TABLE IF NOT EXISTS `appconfigs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `header` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `pagesize` int(11) NOT NULL,
  `defaultlogin` varchar(100) NOT NULL,
  `authdomains` varchar(255) NOT NULL,
  `checktype` varchar(50) NOT NULL,
  `checkhost` varchar(50) NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) NOT NULL,
  `redirect_uri` varchar(255) NOT NULL,
  `bookingbegin` time NOT NULL,
  `bookingend` time NOT NULL,
  `signinbegin` time NOT NULL,
  `signinend` time NOT NULL,
  `signinip` varchar(100) NOT NULL,
  `announcement` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `attendances`
--

CREATE TABLE IF NOT EXISTS `attendances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `studentid` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `status` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `bookings`
--

CREATE TABLE IF NOT EXISTS `bookings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `studentid` varchar(100) NOT NULL,
  `seatid` int(11) NOT NULL,
  `date` date NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'occupied',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `studentid` (`studentid`,`date`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `roomstatuss`
--

CREATE TABLE IF NOT EXISTS `roomstatuss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `status` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `upfiles`
--

CREATE TABLE IF NOT EXISTS `upfiles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `filetype` varchar(200) NOT NULL,
  `bytes` longblob NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

-- --------------------------------------------------------

--
-- 資料表格式： `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) NOT NULL,
  `passwd` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `violations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `studentid` varchar(20) NOT NULL,
  `reason` varchar(20) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;


/*
ALTER TABLE  `studyroom`.`bookings` DROP INDEX  `studentid` ,
ADD UNIQUE  `seatid_date_status` (  `seatid` ,  `date` ,  `status` )
*/

ALTER TABLE  `appconfigs` ADD  `workingseatids` VARCHAR( 200 ) NOT NULL AFTER  `signinip`;
ALTER TABLE  `appconfigs` ADD  `punishingthreshold` INT NOT NULL AFTER  `signinend`;
ALTER TABLE  `appconfigs` ADD  `punishingdays` INT NOT NULL AFTER  `punishingthreshold`;


ALTER TABLE  `appconfigs` CHANGE  `workingstudents`  `workingseatids` VARCHAR( 200 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

