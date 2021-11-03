Техническое задание:

На базе сервлетов реализовать java API для выполнения функций:
1)      Поиск в репозитории аккаунта пользователя по имени (имя — уникальный атрибут);
2)      Изменения у учётной записи любого атрибута.
3)      Тестовый набор данных (загрузка скрипта sql)
4)      Получить список всех пользователей, а затем с помощью stream api найти:
        а) имена всех пользователей, которые младше 20 лет;
        б) посчитать количество пользователей, у которых фамилия оканчивается на "ov";

В качестве репозитория использовать СУБД H2, взаимодействие с базой должно идти через jdbc.
На реализацию должны быть написаны unit-тесты и интеграционные тесты с использованием Mockito.

# Http Api

1) Create database
   - OPTIONS /app/api/new
2) Create user
   - GET /api/new/{login}?name=string&date=long&age=int
3) Show users 
   - GET /app/api/users 
   - GET /app/api/users/{login}
4) Edit user data 
   - GET /api/edit/{login}?name=string&date=long&age=int 
5) Delete user from database 
   - DELETE /api/edit/{login}


### Test tasks
    Get number users where user.lastname is 'en'
    GET /api/task/1
    
    Get all user lastnames where user.age < 20
    GET /api/task/2



