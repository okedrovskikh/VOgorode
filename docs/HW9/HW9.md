## HW 9

Для сбора статистики в сервисе Landscape были созданы соответствующие контроллеры и сервисы.
Они находятся в пакете statistics. Landscape общается с Rancher и Handyman по grpc.
С помощью написанных gradle тасок выполняются запросы на создание fields, fielders, bankAccounts, users.
Добавил индексы для bank_accounts, accounts, fielders.

## Метрики по запросам в RancherService:

![/metrics_photo/crud_create/rancher/crud_create1.jpg](./metrics_photo/crud_create/rancher/crud_create1.jpg)
![/metrics_photo/crud_create/rancher/crud_create2.jpg](./metrics_photo/crud_create/rancher/crud_create2.jpg)

Изначально имеем высокую загруженность процессора из-за того, что приложение было не прогрето.
Соответственно и более высокую длительность ответа. Падение нагрузки на процессор в конце загрузки данных
обусловлен тем, как gradle очищает память в запущенных им процессах.

## Метрики по запросам в HandymanService:

![/metrics_photo/crud_create/handyman/crud_create1.jpg](./metrics_photo/crud_create/handyman/crud_create1.jpg)
![/metrics_photo/crud_create/handyman/crud_create2.jpg](./metrics_photo/crud_create/handyman/crud_create2.jpg)

Здесь приложение было прогрето, тк до этого были неудачные попытки загрузить данные.
Падение нагрузки на процессор обусловлено теми же причинами, что и в Rancher'е.
Увеличение длительности обработки запросов по /users связано с увеличением числа банковских аккаунтов.
Тк при сохранении очередного пользователя необходимо найти аккаунты переданные в запросе и обновить их поле userId.
Увеличение длительности запросов по /accounts не знаю даже с чем и связать, как вариант с уменьшением оперативной памяти
на машине, тк к концу теста gradle таска хранила в памяти почти весь файл с sql запросами в accounts.

## Метрики статистики без индексов

![/metrics_photo/crud_create/not_indexed/accounts-stat.jpg](./metrics_photo/not_indexed/accounts-stat.jpg)

Особо большой разницу между запросами нет, тк в любом случае мы проходим по всем строкам и смотрим их дату создания.
Индексы в данной ситуации ни на что не влияют. Тк мы выбираем все строки, ни как не различая их.

![/metrics_photo/crud_create/not_indexed/banks-stat.jpg](./metrics_photo/not_indexed/banks-stat.jpg)

Индексы в этом случае также не нужны тк мы просматриваем все строки для получения банков.

## Метрики статистики с индексами
