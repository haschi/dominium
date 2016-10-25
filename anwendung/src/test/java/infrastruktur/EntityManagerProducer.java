package infrastruktur;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SuppressWarnings("checkstyle:designforextension")
@Exclude(ifProjectStage = ProjectStage.Production.class)
public class EntityManagerProducer
{

    @Inject
    @PersistenceUnitName("test")
    private EntityManagerFactory factory;

    @Produces
    @Default
    @RequestScoped
    // @ApplicationScoped
    // @TransactionScoped
    public EntityManager entityManager()
    {
        return this.factory.createEntityManager();
    }

    public void close(@Disposes @Any final EntityManager entityManager)
    {
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }
}


