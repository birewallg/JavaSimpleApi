package local.ts3snet.entity;

/**
 * User Entity class
 */
public class User {
    private Integer id;
    private String login;
    private String name;
    private String lastname;
    private Integer age;

    public User () {
        this.login = "login";
        this.name = "name";
        this.lastname = "lastname";
        this.age = 0;
    }
    public User(String login, String name, String lastname, int age) {
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public boolean build(String k, Object v) {
        try {
            switch (k) {
                case "name": {
                    this.name = (String) v;
                    break;
                }
                case "data": {
                    this.lastname = (String) v;
                    break;
                }
                case "age": {
                    if (Integer.parseInt((String)v) < 0)
                        return false;
                    this.age = Integer.parseInt((String)v);
                    break;
                }
                default:
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
