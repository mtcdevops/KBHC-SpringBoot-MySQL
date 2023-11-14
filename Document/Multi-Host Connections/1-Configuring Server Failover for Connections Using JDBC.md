# JDBC Connection을 통한 서버 장애 조치 구성

MySQL Connector/J는 서버 장애 내구성을 지원합니다. 장애 내구성은 기본적인 활성 연결에서 연결 관련 오류가 발생할 때 발생합니다. 연결 오류는 기본적으로 클라이언트에 전파되며, 클라이언트는 이를 처리하기 위해 작동 중인 객체 (Statement, ResultSet 등)를 다시 생성하고 프로세스를 다시 시작해야 합니다. 때로는 드라이버가 클라이언트 애플리케이션이 계속 실행되기 전에 자동으로 원래 호스트로 다시 전환될 수 있으며, 이 경우 호스트 전환은 투명하며 클라이언트 애플리케이션은 심지어 이를 인식하지 못할 것입니다.

장애 내구성을 지원하는 연결은 표준 연결과 마찬가지로 작동합니다. 클라이언트는 장애 내구성 프로세스 중에 어떠한 중단도 경험하지 않습니다. 이는 클라이언트가 두 개의 연속된 문이 두 개의 다른 물리적 호스트에서 실행되더라도 동일한 연결 인스턴스에 의존할 수 있다는 것을 의미합니다. 그러나 이는 클라이언트가 서버 전환을 트리거한 예외를 처리하지 않아야 하는 것을 의미하지 않습니다.

장애 내구성은 서버 연결의 초기 설정 단계에서 연결 URL로 구성됩니다
```
jdbc:mysql://[primary host][:port],[secondary host 1][:port][,[secondary host 2][:port]]...[/[database]][?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
```

---

```yml
# MySQL Connector/J를 사용하여 서버 장애 조치 구성
spring:
  datasource:
    url: jdbc:mysql://[primary host][:port],[secondary host 1][:port][,[secondary host 2][:port]]...[/[database]]
    username: your-username
    password: your-password

    # 서버 장애 조치 관련 프로퍼티 설정
    failover:
      readOnly: true # or false
      secondsBeforeRetrySource: 60
      queriesBeforeRetrySource: 10
      retriesAllDown: 3
      autoReconnect: true # or false
      autoReconnectForPools: true # or false
```

위의 YAML 코드는 Spring Boot 애플리케이션의 application.yml 파일에 작성할 MySQL Connector/J를 사용하여 서버 장애 조치 구성 예제입니다. 이 설정은 주된 호스트(primary host)와 여러 보조 호스트(secondary host)를 포함하는 연결 URL을 설정하고, 서버 장애 조치에 관련된 여러 프로퍼티를 지정합니다.

주의: your-username 및 your-password를 실제 데이터베이스 자격 증명으로 대체해야 합니다. 또한, failover와 관련된 프로퍼티 값은 프로덕션 환경에 맞게 조절해야 합니다.