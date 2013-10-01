CREATE DATABASE  `studyroom` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE  `studyroom`.`users` (
`id` BIGINT NOT NULL AUTO_INCREMENT ,
`account` VARCHAR( 100 ) NOT NULL ,
`passwd` VARCHAR( 100 ) NOT NULL ,
`name` VARCHAR( 100 ) NOT NULL ,
PRIMARY KEY (  `id` )
) ENGINE = MYISAM ;
ALTER TABLE  `users` ADD UNIQUE (`account`);

CREATE USER 'studyroom'@'localhost' IDENTIFIED BY  '***';
GRANT USAGE ON * . * TO  'studyroom'@'localhost' IDENTIFIED BY  '***' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON  `studyroom` . * TO  'studyroom'@'localhost' WITH GRANT OPTION ;

INSERT INTO  `studyroom`.`users` (`id` ,`account` ,`passwd` ,`name`)VALUES (NULL ,  'admin',  md5('ashsashs'),  '管理員');
