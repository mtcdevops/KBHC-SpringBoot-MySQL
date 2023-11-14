# 고급 로드 밸런싱 및 장애 내구성 구성

Connector/J는 MySQL 클러스터 또는 다중 소스 배포에 유용한 로드 밸런싱 구현을 제공합니다. 이는 "Connector/J를 사용한 Load Balancing 구성"에서 설명한 것과 동일한 구현으로 읽기 전용 레플리카 간의 로드를 균형있게 분산하는 데 사용됩니다.

```yml
spring:
  datasource:
    # MySQL 클러스터 또는 다중 소스 배포에 대한 로드 밸런싱 및 페일오버 구성
    load-balancing:
      # 페일오버 및 로드 밸런싱을 위한 JDBC URL
      url: jdbc:mysql:replication://[소스 호스트][:포트],[레플리카 호스트 1][:포트][,[레플리카 호스트 2][:포트]]...[/[데이터베이스]]
      
      # 로드 밸런싱 및 페일오버를 위한 속성 설정
      properties:
        # 페일오버에 대한 사용자 정의 예외 확인자 클래스
        loadBalanceExceptionChecker: com.example.NdbLoadBalanceExceptionChecker
        
        # SQLState 코드에 따라 페일오버를 트리거하는 SQLState 코드 접두사 목록
        loadBalanceSQLStateFailover: 00,12345
        
        # 특정 SQLException 하위 클래스가 페일오버를 트리거하도록 하는 클래스 목록
        loadBalanceSQLExceptionSubclassFailover: java.sql.SQLTransientConnectionException
        
        # 자동 커밋이 활성화된 경우 일정 횟수의 명령문 실행 후 서버 연결을 다시 균형잡히게 함
        loadBalanceAutoCommitStatementThreshold: 3
        
        # 일치하는 명령문을 식별하기 위한 정규 표현식
        loadBalanceAutoCommitStatementRegex: .*test.*
```

위 YAML 코드는 Spring Boot 애플리케이션의 application.yml 파일에 작성할 데이터베이스 연결과 로드 밸런싱 설정을 나타냅니다. 설정은 MySQL 클러스터나 다중 소스 배포에서의 로드 밸런싱 및 페일오버를 지원하기 위한 것으로, 특히 loadBalanceExceptionChecker, loadBalanceSQLStateFailover, loadBalanceSQLExceptionSubclassFailover, loadBalanceAutoCommitStatementThreshold, loadBalanceAutoCommitStatementRegex 등의 속성을 통해 세부적인 동작을 조절할 수 있습니다.