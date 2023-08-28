import com.demo.MainApplication;
import com.demo.domain.Person;
import com.demo.service.PersonService;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(initializers = Initializer.class)
class TestExampleIntegrationUsingTestContainers {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PersonService personService;

    @Test
    @Transactional
    void test1() {
        final EntityManagerFactory emf = em.getEntityManagerFactory();
        final Map<String, Object> emfProperties = emf.getProperties();
        final HikariDataSource hikariDataSource = (HikariDataSource) emfProperties.get("hibernate.connection.datasource");
        Assert.assertEquals("org.postgresql.Driver", hikariDataSource.getDriverClassName());
        Assert.assertTrue(hikariDataSource.getJdbcUrl().contains("postgresql"));

        final String firstName = "Ahmet";
        final int nationalId = 99;
        personService.add(firstName, nationalId);

        final List<Person> personList = personService.loadAll();

        Assert.assertFalse(personList.isEmpty());
        Assert.assertEquals(firstName, personList.get(0).getFirstName());
        Assert.assertEquals(nationalId, personList.get(0).getNationalId());
    }
}