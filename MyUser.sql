DROP TABLE IF EXISTS `my_user`;
CREATE TABLE `my_user` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `password` varchar(100) DEFAULT NULL,
                          `username` varchar(100) NOT NULL,
                          `authorities` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
