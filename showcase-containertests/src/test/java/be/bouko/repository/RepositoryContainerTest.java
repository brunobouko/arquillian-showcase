package be.bouko.repository;

import be.bouko.export.Export;
import be.bouko.model.JpaEntity;
import org.hamcrest.core.IsCollectionContaining;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class RepositoryContainerTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Repository.class.getPackage())
                .addPackage(JpaEntity.class.getPackage())
                .addPackage(Export.class.getPackage())
                .addAsResource("persistence-test.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private Repository repository;
    @EJB
    private Export export;
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserTransaction userTransaction;

    @Before
    public void assureThatEverythingIsClearedBeforeTestsExecute() throws Exception{
        userTransaction.begin();
        entityManager.joinTransaction();
        entityManager.createQuery("delete from JpaEntity").executeUpdate();
        userTransaction.commit();
        entityManager.clear();
    }

    @Test
    public void create_should_persist_a_JpaEntity() throws MalformedURLException {
        System.out.println(new File("target").toURI().toURL());
        assertNotNull(repository);
        JpaEntity jpaEntity = new JpaEntity("name", "firstName");
        assertNotNull(repository.create(jpaEntity));
        assertThat(repository.findAll(), IsCollectionContaining.hasItem(jpaEntity));
    }

    @Test
    public void export_should_have_correct_exportUrl() {
        assertNotNull(export);
        assertNotNull(export.getExportUrl());
    }

    @Test
    public void should_be_able_to_save_JpaEntities_and_export_them_to_file_to_given_URL() throws IOException {
        repository.create(new JpaEntity("Simpson", "Homer"));
        repository.create(new JpaEntity("Bouvier", "Marge"));
        repository.create(new JpaEntity("Simpson", "Bart"));
        repository.create(new JpaEntity("Simpson", "Lisa"));
        repository.create(new JpaEntity("Simpson", "Maggy"));

        List<JpaEntity> simpsons = repository.findAll();

        export.export("exportedSimpsons", simpsons);

        File exported = new File("target/exportedSimpsons.txt");
        assertThatFileWasExportedCorrectly(exported);


    }

    private void assertThatFileWasExportedCorrectly(File exported) throws IOException {
        assertTrue(exported.exists());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(exported)));
        try {
            assertEquals("7 | Homer | Simpson", bufferedReader.readLine());
            assertEquals("8 | Marge | Bouvier", bufferedReader.readLine());
            assertEquals("9 | Bart | Simpson", bufferedReader.readLine());
            assertEquals("10 | Lisa | Simpson", bufferedReader.readLine());
            assertEquals("11 | Maggy | Simpson", bufferedReader.readLine());
        } finally {
            bufferedReader.close();
        }
    }

}
