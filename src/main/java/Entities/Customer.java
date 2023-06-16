package Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {

    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String document;


    public Customer(String name, LocalDate birthDate, String document) {
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
    }

    public Customer(Integer id, String name, LocalDate birthDate, String document) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
       return String.format("Customer: %n" +
                "id: %s%n" +
                "name: %s%n" +
                "birth date: %s/%s/%s%n" +
                "document: %s%n",
                id, name,birthDate.getDayOfMonth(), birthDate.getMonth(), birthDate.getYear(), document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return getDocument().equals(customer.getDocument());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocument());
    }

    public void setId(int id) {
        this.id = id;
    }
}
