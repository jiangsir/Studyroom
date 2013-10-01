-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 建立日期: Oct 01, 2013, 11:47 PM
-- 伺服器版本: 5.1.44
-- PHP 版本: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 資料庫: `studyroom`
--
CREATE DATABASE `studyroom` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `studyroom`;

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

--
-- 列出以下資料庫的數據： `upfiles`
--


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

--
-- 列出以下資料庫的數據： `users`
--

INSERT INTO `users` (`account`, `passwd`, `name`, `role`) VALUES('admin', MD5('nimda'), '管理員', 'ADMIN');

CREATE USER 'studyroom'@'localhost' IDENTIFIED BY  '***';
GRANT USAGE ON * . * TO  'studyroom'@'localhost' IDENTIFIED BY  '***' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON  `studyroom` . * TO  'studyroom'@'localhost' WITH GRANT OPTION ;
SET PASSWORD FOR  'studyroom'@'localhost' = PASSWORD( 'ashsashs' );
