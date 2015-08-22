# KrasEvent
Агрегатор событий, с возможностью создания собственных публичных и приватных(для друзей) событий

Идея заключается в создании агрегатора событий, который бы собирал информацию о событиях в городе с различных порталов/афиш, а также имел возможность создания собственных событий пользователями.

Для сбора информации о событиях требуется разработка соответствующей структуры базы данных(Рис. 1), для импорта информации с сторонних ресурсов требуется разработать парсеры, которые бы осуществляли импорт данных в БД агрегатора).

![Реализованные компоненты](http://s017.radikal.ru/i422/1508/d8/c7f29e663b2e.jpg)

## Система должна иметь следующие основные функции:

### USER:
- Авторизация/регистрация пользователей;
- Определение локали пользователя;
- Возможность подписки на аккаунты других пользователей;
- Возможность отмечаться на событиях как "пойду" "не пойду";
- Лента новостей пользователя с фильтрами событий.

### EVENT:
- Возможность задания координат события или адреса, также даты/времени начала/конца события и т.д.;
- "Типизирование" на приватные и публичные метки;
- Разделение событий по категориям(спорт, призентация, концерт и т.д.);
- ТЭГирование событий;
- Подгрузка изображений в событие с google street view если не пользователем изображение не указанно.

### MAP:
- Кастомизированные маркеры на карте;
- Группировка маркеров при малом масштабе;
- Фильтрация маркеров(событий) на карте;
- Использование Google map API;
- Добавление карт 2gis и использование 2gis API;

