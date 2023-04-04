## Используемые переменные окружения и их назначение

Зачастую под дефолтной переменной имеется в виду переменная определенная в application.yml сервиса, которая будет использоваться если сама переменная окружения не определена.

Все сервисы используют переменную **SERVER_PORT**. Переменная для назначения порта на котором будет запущен сервис.

    Дефолтные значения переменной в сервисах: 

        HandymanService - 8080
    
        LandscapeService - 8082
    
        RancherService - 8081


RancherService и HandymanService используют переменную **GRPC_SERVER_PORT** - порт на котором будет запущен gRPC сервер.

    Дефолтные значения переменной в сервисах:

        HandymanService - 9090

        Rancherservice - 9091

LanscapeService использует переменные **RANCHER_GRPC_SERVER_ADDRESS** и **HANDYMAN_GRPC_SERVER_ADDRESS**.

**RANCHER_GRPC_SERVER_ADDRESS** - адрес на котором будет ожидаться RancherService gRPC сервер.

**HANDYMAN_GRPC_SERVER_ADDRESS** - адрес на котором будет ожидаться HandymanService gRPC сервер.

    Дефолтные значения переменных в сервисе:

        RANCHER_GRPC_SERVER_ADDRESS - localhost:9090

        HANDYMAN_GRPC_SERVER_ADDRESS - localhost:9091

Все сервисы используют postgres в качестве базы данных. Для подключения к ней используются следующие переменные: 
**POSTGRES_DB_URL**, **POSTGRES_USER**, **POSTGRES_PASSWORD**.

    Дефолтные значения переменных в сервисах:

        POSTGRES_DB_URL - jdbc:postgresql://localhost:5432/vogorode

        POSTGRES_USER - postgres

        POSTGRES_PASSWORD - 123
