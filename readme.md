# application.yml
- loadbalance
```
url: jdbc:log4jdbc:mysql:loadbalance://kms-mysql.mysql.database.azure.com:3306,kms-mysql-slave2.mysql.database.azure.com:3306,kms-mysql-slave.mysql.database.azure.com:3306/test?serverTimezone=UTC
      jdbc-url: jdbc:log4jdbc:mysql:loadbalance://kms-mysql.mysql.database.azure.com:3306,kms-mysql-slave.mysql.database.azure.com:3306,kms-mysql-slave.mysql2.database.azure.com:3306/test?serverTimezone=UTC
```

