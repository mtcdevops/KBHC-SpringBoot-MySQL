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

# SKILL STACK
- 개발도구
      - JDK 11
      - SpringBoot 2.7.16
      - Gradle 8.2.1
      - Azure Database For MySQL
            - read/write 구성
      - war

# 1. 의존성 추가
```build.gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.3.1'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.mysql:mysql-connector-j'
	runtimeOnly 'org.postgresql:postgresql'
	annotationProcessor 'org.projectlombok:lombok'
	providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.3.1'

	/*추가 의존성*/
	
	// JSP 관련 의존성 추가
	implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
	implementation 'javax.servlet:jstl'
	
	// JUnit5
	testImplementation("org.junit.platform:junit-platform-launcher:1.5.2") 
	testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

	// Qeury Log 설정
	implementation "org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4:1.16"
	
	// Json타입 설정
	implementation 'com.fasterxml.jackson.core:jackson-databind:2.13.0'
	
	// 화면 자동 반영
	implementation 'org.springframework.boot:spring-boot-devtools'
}
```

2. application.yml 설정
Database read replication 관련 내용으로는 아래 링크를 참고 바랍니다.
[링크](https://github.com/mtcdevops/KBHC-SpringBoot-MySQL/tree/master/Document)
```yml
spring:
  # MySQL DB 설정
  datasource:
    # Resd replica 구성
    master: # Write(Insert, Update, Delete)
      driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
      jdbc-url: jdbc:log4jdbc:mysql://<master db url>:<port>/test?serverTimezone=UTC
      read-only: false
      username: <id>
      password: <password>
#      
    slave:  # Read(Select)
      driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
      jdbc-url: jdbc:log4jdbc:mysql:loadbalance://<slave1 db url>:<port>,<slave2 db url>:<port>/test?serverTimezone=UTC
      read-only: true # Slave DB는 Read만
      username: <id>
      password: <password>
```