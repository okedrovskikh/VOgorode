## VOgorode

## Запуск и настройка
1) Для запуска приложения необходимо настроить порты, в application.yml каждого сервиса. По дефолту сервисы имеют следующие порты: 

        HandymanService - 8080
    
    
        LandscapeService - 8082
    
    
        RancherService - 8081

    Необходимо настроить порты gRPC серверов в HandymanService и RancherService. По дефолту gRPC сервера имеют следующие порты: 

        HandymanService - 9090

        Rancherservice - 9091

    При изменении портов необходимо изменить адреса серверов в LandscapeService. Имена сервисов совпадают. 
    Если необходимо добавить дополнительные instance'ы сервисов, надо добавить адреса сервисов в LandscapeService. 
    В имени сервисов в application.yml должны быть HandymanService или RancherService соответсвенно RancherService или HandymanService instance'ам.

2) При необходимости получить код генерируемый из .proto файлов использовать ./gradlew generateProto, если это не надо, то можно пропустить этот этап.

3) Далее каждый проект необходимо собрать с использованием gradle.

4) Для запуска использовать java -jar ./"Название сервиса"/build/libs/"Название сервиса"-1.1.0.jar

## Запуск с использованием Docker

[Docker-readme](/dev/README.md)

## Описание домашних работ
[HW1](/docs/HW1/HW1.md)

[HW2](/docs/HW2/HW2.md)

[HW3](/docs/HW3/HW3.md)
