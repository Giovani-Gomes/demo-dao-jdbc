package model.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private Date bitrhDate;
    private Double baseSalary;

    private Departament departament;

    public Seller(){}

    public Seller(Integer id, String name, String email, Date bitrhDate, Double baseSalary, Departament departament) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bitrhDate = bitrhDate;
        this.baseSalary = baseSalary;
        this.departament = departament;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBitrhDate() {
        return bitrhDate;
    }

    public void setBitrhDate(Date bitrhDate) {
        this.bitrhDate = bitrhDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bitrhDate=" + bitrhDate +
                ", baseSalary=" + baseSalary +
                ", departament=" + departament +
                '}';
    }
}
