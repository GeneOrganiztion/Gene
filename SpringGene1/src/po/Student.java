package po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "student")
public class Student extends BaseEntity {

    private static final long serialVersionUID = 7453701020607510295L;
    @Id
    private Integer id;
    private Integer age;
    private String name;
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
