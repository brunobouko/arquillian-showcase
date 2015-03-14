package be.bouko.repository;

import be.bouko.model.JpaEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by BBOUKO on 14/03/2015.
 */
public interface Repository {
    EntityManager getEntityManager();

    JpaEntity create(JpaEntity jpaEntity);

    List<JpaEntity> findAll();
}
