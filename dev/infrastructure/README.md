## Запуск сервисов без docker-compose

1) Запустить docker-compose в инфраструктурном режиме.

2) После собрать проект с использованием.

        ./gradlew build

   ![./gradle_photo/gradle.jpg](./gradle_photo/gradle.jpg)

3) Затем запустить jar-файл получившийся в результате сборки.

        java -jar ./<Название сервиса>/build/libs/<Название jar файла сервиса>.jar

   ![./gradle_photo/java_jar.jpg](./gradle_photo/java_jar.jpg)
   ![./gradle_photo/java_ok.jpg](./gradle_photo/java_ok.jpg)
