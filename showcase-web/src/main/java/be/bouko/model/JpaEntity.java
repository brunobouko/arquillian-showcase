package be.bouko.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class JpaEntity implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String firstName;

    public JpaEntity() {
    }

    public JpaEntity(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaEntity jpaEntity = (JpaEntity) o;

        if (!firstName.equals(jpaEntity.firstName)) return false;
        if (!name.equals(jpaEntity.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + firstName.hashCode();
        return result;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }
}
