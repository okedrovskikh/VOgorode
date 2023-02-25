## VOgorode

## Запуск и настройка
1) Для запуска приложения необходимо настроить порты приложения, в application.yml каждого сервиса, либо же с помощью переменных окружения.
    Имена переменных и их назначение:

        SERVER_PORT - порт на котором будет запущен сервис

    По дефолту сервисы имеют следующие порты: 

        HandymanService - 8080
    
    
        LandscapeService - 8082
    
    
        RancherService - 8081

2) Необходимо настроить порты gRPC серверов в HandymanService и RancherService также в application.yml, либо с переменными окружения.
    Имена переменных и их назначение:

        GRPC_SERVER_PORT - необходимо настроить в RancherService и HandymanService, порт на котором запуститься gRPC сервер

        RANCHER_GRPC_SERVER_ADDRESS - адрес gRPC сервера RancherService

        HANDYMAN_GRPC_SERVER_ADDRESS - адрес gRPC сервера HandymanService

    Последние два необходимо настраивать в LandscapeService.
    
     По дефолту gRPC сервера имеют следующие порты: 

        HandymanService - 9090

        Rancherservice - 9091

    По дефолту LandscapeService будет пытаться подключиться к gRPC серверам по адресам:

        RANCHER_GRPC_SERVER_ADDRESS - localhost:9090

        HANDYMAN_GRPC_SERVER_ADDRESS - localhost:9091

    При изменении портов необходимо изменить адреса серверов в LandscapeService. Имена сервисов совпадают. 
    Если необходимо добавить дополнительные instance'ы сервисов, надо добавить адреса сервисов в LandscapeService. 
    В имени сервисов в application.yml должны быть HandymanService или RancherService соответсвенно RancherService или HandymanService instance'ам.

3) При необходимости получить код генерируемый из .proto файлов использовать ./gradlew generateProto, если это не надо, то можно пропустить этот этап.

4) Далее каждый проект необходимо собрать с использованием gradle.

5) Для запуска использовать java -jar ./"Название сервиса"/build/libs/"Название сервиса"-1.1.0.jar

## Запуск с использованием Docker

[Docker-readme](/dev/README.md)

## Описание домашних работ
[HW1](/docs/HW1/HW1.md)

[HW2](/docs/HW2/HW2.md)

[HW3](/docs/HW3/HW3.md)
