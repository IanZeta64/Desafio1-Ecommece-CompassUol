package Entities;

import enums.Role;

import java.util.UUID;

public class Employee {

    private Integer id;
    private final String name;
    private final String register;
    private final Role role;

    public Employee(String name, Role role) {
        this.name = name;
        this.role = role;
        this.register = UUID.randomUUID().toString();
    }
    public Employee(Integer id, String name, String register, Role role) {
        this.id = id;
        this.name = name;
        this.register = register;
        this.role = role;
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
    public String getRegister() {
        return register;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public String toString() {
        return String.format("Employee: %n" +
                        "id: %s%n" +
                        "name: %s%n" +
                        "register: %s%n" +
                        "role: %s",
                id, name, register, role.toString());
    }
}
