## HW 2

Создал gRPC сервера в сервисах HandymanService и RancherService с использованием библиотеки net.devh:grpc-server-spring-boot-starter:2.13.1.RELEASE и gRPC клиента в сервисе LandscapeService с использованием библиотеки net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE. 
Для генерации кода использовал плагин io.grpc:protoc-gen-grpc-java:1.42.2. 
Создал gRPC service унаследовавшись от ServiceStatusGrpc.ServiceStatusImplBase и переопределив методы getVersion и getReadiness.
Для создание stub'ов на клиенте использовал GrpcChannelFactory и создаваемые им каналы. Информация о подключении к сервисам доступна на endpoint'е /services/status
