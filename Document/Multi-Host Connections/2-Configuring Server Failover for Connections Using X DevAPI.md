# X DevAPI를 사용한 연결의 서버 Failover 구성


X Protocol을 사용할 때 Connector/J는 세션을 설정하기 위한 클라이언트 측 장애 내구성 기능을 지원합니다. 연결 URL에 여러 호스트가 지정된 경우 Connector/J는 목록에 있는 호스트에 연결하지 못할 때 다른 호스트에 연결을 시도합니다. 클라이언트 측 장애 내구성을 구성하는 샘플 X DevAPI URL은 다음과 같습니다:
```yml
mysqlx://sandy:mypassword@[host1:33060,host2:33061]/test
```

클라이언트 측 장애 내구성이 구성되면 연결을 설정하는 데 실패할 때 Connector/J는 호스트 목록 중 하나에 계속 연결을 시도합니다. 호스트에 대한 연결 시도 순서는 다음과 같습니다:

각 호스트에 대해 연결 URL에서 설정된 우선순위 속성이 있는 경우, 호스트에 대한 우선순위에 따라 시도됩니다. 이는 각 호스트에 0에서 100 사이의 숫자로 지정된 호스트의 우선순위에 따라 설정되며, 숫자가 클수록 더 높은 우선순위를 나타냅니다.
```yml
mysqlx://sandy:mypassword@[(address=host1:33060,priority=2),(address=host2:33061,priority=1)]/test
```

이 예에서는 새 세션이 생성될 때 항상 host1이 host2보다 먼저 시도됩니다.

모든 호스트에 대해 우선순위 속성이 설정되지 않은 경우, 호스트는 무작위로 하나씩 시도됩니다.

X DevAPI에 대한 서버 장애 내구성 기능은 Connector/J가 연결을 설정하려고 시도할 때만 장애 내구성을 허용하며 이미 연결된 후의 작업 중에는 허용하지 않습니다.

X DevAPI를 사용하는 연결 풀링. X DevAPI를 사용하는 경우 Connector/J는 연결을 실패한 호스트를 추적하고 실패한 후 잠시 동안 해당 호스트에 대한 세션 생성 또는 검색 중에 해당 호스트에 연결하지 않도록 합니다. 그러나 다른 모든 호스트가 이미 시도되었을 경우, 제외된 호스트는 기다리지 않고 다시 시도됩니다. 모든 호스트가 시도되고 연결을 설정할 수 없는 경우, Connector/J는 com.mysql.cj.exceptions.CJCommunicationsException을 throw하고 "Unable to connect to any of the target hosts" 메시지를 반환합니다.