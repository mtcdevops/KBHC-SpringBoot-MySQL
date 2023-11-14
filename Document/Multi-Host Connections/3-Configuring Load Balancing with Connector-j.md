# Connector/J를 사용한 Load Balancing 구성


Connector/J는 클러스터 또는 소스-소스 복제 배치를 위해 여러 MySQL 서버 인스턴스에 대한 읽기/쓰기 부하를 효과적으로 분산하는 방법을 오랜 기간 제공해왔습니다. 서비스 중단 없이 로드 밸런스된 연결을 동적으로 구성할 수 있습니다. 프로세스 트랜잭션은 손실되지 않으며 특정 서버 인스턴스를 사용하려는 어떤 애플리케이션이든 어떠한 애플리케이션 예외도 발생하지 않습니다.

```yml
jdbc:mysql:loadbalance://[host1][:port],[host2][:port][,[host3][:port]]...[/[database]]
```

로드 밸런싱은 연결 URL에서 설정되며, `loadbalance://` 스키마를 사용합니다. 이를 통해 여러 호스트를 명시하고, 연결이 실패하면 다른 호스트로 재시도합니다. 두 가지 중요한 구성 속성은 loadBalanceConnectionGroup과 ha.enableJMX입니다. 전자는 연결을 그룹화하여 관리하는 데 사용되며, 후자는 JMX를 활성화하여 외부에서 연결을 관리할 수 있게 합니다.

```
spring:
  datasource:
    # MySQL 클러스터 또는 다중 소스 배포에 대한 로드 밸런싱 구성
    load-balancing:
      # 로드 밸런싱을 위한 JDBC URL
      url: jdbc:mysql:loadbalance://localhost:3306,localhost:3310/test?loadBalanceConnectionGroup=first&ha.enableJMX=true
      
      # 로드 밸런싱을 위한 속성 설정
      properties:
        # 로드 밸런싱을 위한 연결 그룹 설정
        loadBalanceConnectionGroup: first
        
        # JMX를 통한 로드 밸런싱 연결 관리를 활성화
        ha.enableJMX: true
```
위 YAML 코드는 Spring Boot 애플리케이션의 application.yml 파일에 작성할 `데이터베이스 로드 밸런싱 설정`을 나타냅니다.    
설정은 MySQL 클러스터나 다중 소스 배포에서의 로드 밸런싱을 지원하며, 특히 loadBalanceConnectionGroup와 ha.enableJMX 등의 속성을 통해 세부적인 동작을 조절할 수 있습니다. 설정 후 JMX를 통해 로드 밸런싱 연결을 관리하고 모니터링할 수 있습니다.