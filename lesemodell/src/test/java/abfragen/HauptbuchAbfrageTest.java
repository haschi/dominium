package abfragen;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

@RunWith(CdiTestRunner.class)
@Transactional
public class HauptbuchAbfrageTest
{

    //@Inject
    //HauptbuchAbfrage abfrage;

    @Inject
    ExampleCdiBean bean;

    //@Inject
    //AnotherCdiBean other;

    @Inject
    EntityManager entityManager;

    @Test
    public void persistenzTesten()
    {
        KontoId id = new KontoId();
        id.kontoname = "Hello";
        id.haushaltsbuch = UUID.randomUUID();

        Konto konto = new Konto();
        konto.id = id;
        konto.kontoart = 14;
        konto.währung = "EUR";
        konto.saldo = BigDecimal.ZERO;

        entityManager.persist(konto);
        //entityManager.getTransaction().begin();
        entityManager.flush();
        ;
        //entityManager.getTransaction().commit();

    }

    @Test
    public void EinTest()
    {
        bean.testTransaction();
    }
}
