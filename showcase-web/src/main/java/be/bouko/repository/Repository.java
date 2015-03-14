package be.bouko.repository;

import be.bouko.model.JpaEntity;

import javax.persistence.EntityManager;
import java.util.List;

public interface Repository {
    EntityManager getEntityManager();

    JpaEntity create(JpaEntity jpaEntity);

    List<JpaEntity> findAll();
}
