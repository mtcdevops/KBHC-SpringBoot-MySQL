# Connector/J를 사용한 Source/Replica 복제 구성

```yml
spring:
  datasource:
    replication:
      # MySQL 서버 연결 설정
      url: jdbc:mysql:replication://[소스 호스트][:포트],[레플리카 호스트 1][:포트][,[레플리카 호스트 2][:포트]]...[/[데이터베이스]]
      
      # 연결 속성 설정
      properties:
        # 소스 호스트에 연결이 불가능한 경우에도 연결 허용
        allowSourceDownConnections: true
        
        # 레플리카 호스트에 연결이 불가능한 경우에도 연결 허용
        allowReplicasDownConnections: true
        
        # 레플리카 호스트가 없을 때 소스 호스트에 연결 허용
        readFromSourceWhenNoReplicas: true

      # 로드 밸런싱 설정
      loadBalance:
        # 자동 재연결 활성화
        autoReconnect: true
        
        # 레플리카 호스트 간 라운드 로빈 로드 밸런싱 활성화
        roundRobinLoadBalance: true

      # 데이터베이스 사용자 정보
      username: databaseid
      password: password

```
위 YAML 코드는 Spring Boot 애플리케이션의 application.yml 파일에 작성할 데이터베이스 연결 및 복제 구성을 나타냅니다.

- jdbc:mysql:replication://: MySQL 서버의 `replica` 구성을 나타내는 연결 URL.
- allowSourceDownConnections: 소스 호스트에 연결이 불가능한 경우에도 연결을 허용하는 속성.
- allowReplicasDownConnections: 레플리카 호스트에 연결이 불가능한 경우에도 연결을 허용하는 속성.
- readFromSourceWhenNoReplicas: 레플리카 호스트가 없을 때 소스 호스트에 연결을 허용하는 속성.
- autoReconnect: 자동 재연결을 활성화하는 로드 밸런싱 속성.
- roundRobinLoadBalance: 레플리카 호스트 간 라운드 로빈 로드 밸런싱을 활성화하는 로드 밸런싱 속성.
- username: MySQL 데이터베이스 사용자 이름.
- password: MySQL 데이터베이스 사용자 비밀번호.