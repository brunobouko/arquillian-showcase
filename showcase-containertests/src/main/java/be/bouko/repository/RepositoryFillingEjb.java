package be.bouko.repository;

import be.bouko.model.JpaEntity;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton @Startup
public class RepositoryFillingEjb {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private Repository repository;

    @PostConstruct
    public void postConstruct(){
        System.out.println("in time out");
        entityManager.createQuery("delete from JpaEntity").executeUpdate();
        repository.create(new JpaEntity("Simpson", "Homer"));
        repository.create(new JpaEntity("Bouvier", "Marge"));
        repository.create(new JpaEntity("Simpson", "Bart"));
        repository.create(new JpaEntity("Simpson", "Lisa"));
        repository.create(new JpaEntity("Simpson", "Maggy"));
    }
}
