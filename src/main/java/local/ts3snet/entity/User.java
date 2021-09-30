package local.ts3snet.entity;

import com.google.gson.Gson;

import java.sql.Date;

public class User {
    private Integer id;
    private String login;
    private String name;
    private Date data;
    private Integer age;

    public User () {
        this.login = "login";
        this.name = "name";
        this.data = new Date(System.currentTimeMillis());
        this.age = 0;
    }
    public User(String login, String name, long data, int age) {
        this.login = login;
        this.name = name;
        this.data = new Date(data);
        this.age = age;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
       /* return '{' +
                "\"id\": " + id +
                ", \"login\": \"" + login + "\"" +
                ", \"name\": \"" + name + "\"" +
                ", \"data\": \"" + data + "\"" +
                ", \"age\": " + age +
                '}'
        */
    }

    public void build(String k, Object v) {
        try {
            switch (k) {
                case "name": {
                    this.name = (String) v;
                    break;
                }
                case "data": {
                    this.data = new Date((long) v);
                    break;
                }
                case "age": {
                    this.age = (int) v;
                    break;
                }
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
