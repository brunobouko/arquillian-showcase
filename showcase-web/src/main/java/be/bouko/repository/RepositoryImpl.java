package be.bouko.repository;

import be.bouko.model.JpaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by BBOUKO on 14/03/2015.
 */
@Stateless
public class RepositoryImpl implements Repository {
    @PersistenceContext(unitName = "repository")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public JpaEntity create(JpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public List<JpaEntity> findAll() {
        return entityManager.createQuery("select j from JpaEntity j").getResultList();
    }
}
