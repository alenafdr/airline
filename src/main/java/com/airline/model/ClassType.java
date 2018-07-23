package com.airline.model;

import java.util.Objects;

public class ClassType {
    private Long id;
    private String name;

    public ClassType() {
    }

    public ClassType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassType{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassType classType = (ClassType) o;
        return Objects.equals(id, classType.id) &&
                Objects.equals(name, classType.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
