package org.test;

import java.util.Objects;

public class Entity {
    private String name;
    private String label;

    public Entity() {
    }
    public Entity(String name, String label) {
        this.name = name;
        this.label = label;
    }
    public Entity(String name) {
        this.name = name;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int hashCode() {
        return Objects.hash(label, name);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity other = (Entity) obj;
        return Objects.equals(label, other.label) && Objects.equals(name, other.name);
    }
    @Override
    public String toString() {
        return "Entity [name=" + name + ", label=" + label + "]";
    }
    
}
