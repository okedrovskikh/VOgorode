## Запуск сервисов с использованием Docker


Про используемые переменные окружения можно прочитать в [ENV_VAR.md](../docs/ENV_VAR/ENV_VAR.md)

Для всех сервисов необходимо определить подключение к базе данных с помощью описанных в файле выше переменных.


## HandymanService

[HandymanService Dockerfile](../HandymanService/Dockerfile)

1) Для запуска сервиса необходимо определить 2 переменные окружения **SERVER_PORT** и **GRPC_SERVER_PORT**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./HandymanService

![./docker/docker_photo/handyman_build.jpg](./docker/docker_photo/handyman_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название
   image'а>

![./docker/docker_photo/handyman_run.jpg](./docker/docker_photo/handyman_run.jpg)
![./docker/docker_photo/handyman_ok.jpg](./docker/docker_photo/handyman_ok.jpg)

## LandscapeService

[LandscapeService Dockerfile](../LandscapeService/Dockerfile)

1) Для запуска необходимо определить 3 переменные окружения **SERVER_PORT**, **RANCHER_GRPC_SERVER_ADDRESS**, *
   *HANDYMAN_GRPC_SERVER_ADDRESS**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./LandscapeService

![./docker/docker_photo/landscape_build.jpg](./docker/docker_photo/landscape_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название
   image'а>

![./docker/docker_photo/landscape_run.jpg](./docker/docker_photo/landscape_run.jpg)
![./docker/docker_photo/landscape_ok.jpg](./docker/docker_photo/landscape_ok.jpg)

## RancherService

[RancherService Dockerfile](../RancherService/Dockerfile)

1) Для запуска сервиса необходимо определить 2 переменные окружения **SERVER_PORT** и **GRPC_SERVER_PORT**.

2) Далее необходимо выполнить команду docker build -t <Название image'а> ./RancherService

![./docker/docker_photo/rancher_build.jpg](./docker/docker_photo/rancher_build.jpg)

3) После выполнить docker run -p <Порт которые проброситься во вне>:<Порт определнный в **SERVER_PORT**> <Название
   image'а>

![./docker/docker_photo/rancher_run.jpg](./docker/docker_photo/rancher_run.jpg)
![./docker/docker_photo/rancher_ok.jpg](./docker/docker_photo/rancher_ok.jpg)

## VOgorode

[Запуск с использованием docker-compose](./docker/README.md)

[Запуск сервисов без docker-compose](./infrastructure/README.md)
