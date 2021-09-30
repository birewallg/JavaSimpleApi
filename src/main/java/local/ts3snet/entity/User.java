package local.ts3snet.entity;

import java.sql.Date;

public class User {
    private Integer id;
    private String login;
    private String name;
    private Date data;
    private Integer age;

    public User () {}
    public User(String login, String name, long data, int age) {
        this.login = login;
        this.name = name;
        this.data = new Date(data);
        this.age = age;
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\": " + id +
                ", \"login\": \"" + login + "\"" +
                ", \"name\": \"" + name + "\"" +
                ", \"data\": \"" + data + "\"" +
                ", \"age\": " + age +
                '}';
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
