package model;

import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private String occupation;
    private int salary;
    private int age;
    private Date joinDate;

    public Employee() {

    }

    public Employee(int id, String name, String occupation, int salary, int age, Date joinDate) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.salary = salary;
        this.age = age;
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "model.Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", joinDate=" + joinDate +
                '}';
    }
}
