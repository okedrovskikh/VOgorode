## Запуск сервисов с использованием Docker

Про используемые переменные окружения можно прочитать в [env_var.md](./env_var.md)

## HandymanService

[HandymanService Dockerfile](../HandymanService/Dockerfile)

1) Для запуска сервиса необходимо определить 2 переменные окружения **SERVER_PORT** и **GRPC_SERVER_PORT**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./HandymanService

![./docker_photo/handyman_build.jpg](./docker_photo/handyman_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название image'а>

![./docker_photo/handyman_run.jpg](./docker_photo/handyman_run.jpg)

## LandscapeService

[LandscapeService Dockerfile](../LandscapeService/Dockerfile)

1) Для запуска необходимо определить 3 переменные окружения **SERVER_PORT**, **RANCHER_GRPC_SERVER_ADDRESS**, **HANDYMAN_GRPC_SERVER_ADDRESS**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./LandscapeService

![./docker_photo/landscape_build.jpg](./docker_photo/landscape_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название image'а>

![./docker_photo/landscape_run.jpg](./docker_photo/landscape_run.jpg)

## RancherService

[RancherService Dockerfile](../RancherService/Dockerfile)

1) Для запуска сервиса необходимо определить 2 переменные окружения **SERVER_PORT** и **GRPC_SERVER_PORT**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./RancherService

![./docker_photo/rancher_build.jpg](./docker_photo/rancher_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название image'а>

![./docker_photo/rancher_run.jpg](./docker_photo/rancher_run.jpg)

## VOgorode

**Запуск с использованием docker-compose**

1) Для запуска приложения обязательным является переопределение **RANCHER_GRPC_SERVER_ADDRESS** и **HANDYMAN_GRPC_SERVER_ADDRESS** в сервисе LandscapeService.
В docker-compose они переопределены как 'rancher-service:9091' и 'handyman-service:9090' соответсвенно. 

2) После командами cd ./dev и docker-compose up запустить сервис.

**Запуск с использованием Docker**

1) С использованием команды docker network create <Название сети> создать сеть к которой подключаться сервисы.

![./docker_photo/create_net.jpg](./docker_photo/create_net.jpg)
    
2) Затем при запуске контейнеров с сервисами необходимо использовать флаг --network <Название сети>. При таком запуске надо переопределить адреса gRPC серверов в LanscapeService как описано выше.
Если контейнер был запущен без подключения к сети использовать команду docker network <Название сети> <Название контейнера>

![./docker_photo/add_grpc_servers.jpg](./docker_photo/add_grpc_servers.jpg)
![./docker_photo/add_grpc_client.jpg](./docker_photo/add_grpc_client.jpg)
     
**Запуск без использования Docker**

1) Просто запустить сервисы как описано, используя вместо команды запуска Docker container'а (шага 3) java -jar ./<Название сервиса>/build/libs/<Название jar файла сервиса>.jar, а вместо команд сборки Docker image'а (шага 2) команду ./gradlew build.

![./gradle_photo/gradle.jpg](./gradle_photo/gradle.jpg)
![./gradle_photo/java_jar.jpg](./gradle_photo/java_jar.jpg)
