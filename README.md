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
    Get number users where user.age > 20
    GET /api/task/1
    
    Get all user lastnames where user.lastname is 'en'
    GET /api/task/2



