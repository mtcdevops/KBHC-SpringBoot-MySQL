# Connector/J를 사용한 Source/Replica 복제 구성

복제는 서버 연결의 초기 설정 단계에서 연결 URL로 구성되며, 이는 일반 JDBC URL과 유사하지만 특수한 형식을 갖는 MySQL 연결을 위한 것입니다. 다음은 형식입니다:
```yml
jdbc:mysql:replication://[source host][:port],[replica host 1][:port][,[replica host 2][:port]]...[/[database]] »
[?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
```
사용자는 allowSourceDownConnections=true 속성을 지정하여 소스 호스트에 연결할 수 없어도 Connection 개체를 생성할 수 있습니다. 이러한 Connection 개체는 읽기 전용임을 보고하며, isSourceConnection()은 이들에 대해 false를 반환합니다. Connection.setReadOnly(false)가 호출될 때 Connection은 사용 가능한 소스 호스트를 테스트하며, 소스에 연결을 설정할 수 없으면 SQLException을 throw하거나 호스트가 사용 가능하면 소스 연결로 전환합니다.

사용자는 allowReplicasDownConnections=true 속성을 지정하여 레플리카 호스트에 연결할 수 없어도 Connection 개체를 생성할 수 있습니다. 그런 다음 Connection은 실행 중에 Connection.setReadOnly(true) (아래 메서드에 대한 설명 참조)가 호출될 때 사용 가능한 레플리카 호스트를 테스트하며, 레플리카에 연결을 설정할 수 없으면 readFromSourceWhenNoReplicas 속성이 "true"로 설정되어 있지 않은 경우 SQLException을 throw합니다 

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