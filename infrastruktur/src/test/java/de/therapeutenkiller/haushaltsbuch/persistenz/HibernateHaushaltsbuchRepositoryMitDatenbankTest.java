package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@TestControl(projectStage = ProjectStage.IntegrationTest.class)
@Transactional
public class HibernateHaushaltsbuchRepositoryMitDatenbankTest {

    //@Inject
    //private EntityManager entityManager; // NOPMD

    @Inject
    private HaushaltsbuchRepository repository; // NOPMD

    @Test
    @Transactional
    public void haushaltsbuch_wird_persistiert() {
        assertThat(this.repository).isNotNull();
    }
}
