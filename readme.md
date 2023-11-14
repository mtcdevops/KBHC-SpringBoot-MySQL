# application.yml
- loadbalance
```yml
url: jdbc:log4jdbc:mysql:loadbalance://kms-mysql.mysql.database.azure.com:3306,kms-mysql-slave2.mysql.database.azure.com:3306,kms-mysql-slave.mysql.database.azure.com:3306/test?serverTimezone=UTC
      jdbc-url: jdbc:log4jdbc:mysql:loadbalance://kms-mysql.mysql.database.azure.com:3306,kms-mysql-slave.mysql.database.azure.com:3306,kms-mysql-slave.mysql2.database.azure.com:3306/test?serverTimezone=UTC
```

## Database Table Schema
- test.`data` definition
```mysql
CREATE TABLE `data` (
  `num` int NOT NULL AUTO_INCREMENT,
  `contents` text,
  `date` timestamp NULL DEFAULT NULL,
  `rw` varchar(2) NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=492563 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

- test.datainfo definition
```mysql
CREATE TABLE `datainfo` (
  `num` int NOT NULL AUTO_INCREMENT,
  `db_read` int DEFAULT NULL,
  `db_write` int DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

- test.serverlist definition
```mysql
CREATE TABLE `serverlist` (
  `server_num` int NOT NULL AUTO_INCREMENT,
  `server_id` varchar(50) DEFAULT NULL,
  `server_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`server_num`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```