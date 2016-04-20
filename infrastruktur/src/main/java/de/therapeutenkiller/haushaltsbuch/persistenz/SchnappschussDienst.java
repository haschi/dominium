package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;
import de.therapeutenkiller.haushaltsbuch.system.Logged;
import org.jboss.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Logged
@SuppressWarnings("checkstyle:designforextension")
@Singleton
public class SchnappschussDienst {

    @Inject
    HibernateHaushaltsbuchRepository repository;

    @Inject
    JpaHaushaltsbuchSchnappschussLager lager;

    @Inject
    Logger logger;

    @Inject
    EntityManager entityManager;

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void schnappschüsseErzeugen() {

        this.logger.info("schnappschuss erzeugen (1)");
        this.logger.infof("Entity Manager: %s", this.entityManager != null ? "vorhanden" : "fehlt");

        try {
            final TypedQuery<UUID> query = this.entityManager.createQuery(
                "SELECT i.identitätsmerkmal FROM JpaEreignisstrom i",
                UUID.class);
            this.logger.info("schnappschuss erzeugen (2)");
            final List<UUID> resultList = query.getResultList();
            this.logger.info("schnappschuss erzeugen (3)");
            this.logger.info("schnappschuss erzeugen (4)");
            for (final UUID haushaltsbuchId : resultList) {
                try {
                    final Haushaltsbuch haushaltsbuch = this.repository.suchen(haushaltsbuchId);
                    final HaushaltsbuchSchnappschuss schnappschuss = haushaltsbuch.schnappschussErstellen();
                    this.lager.schnappschussHinzufügen(schnappschuss);

                    this.logger.infof("Schnappschuss erstellt für Aggregat %s", haushaltsbuchId.toString());

                } catch (final AggregatNichtGefunden aggregatNichtGefunden) {
                    this.logger.errorf(
                        aggregatNichtGefunden,
                        "Schnappschuss kann nicht erstellt werden. Aggregat wurde nicht gefunden: %s",
                        haushaltsbuchId.toString());
                }
            }
        } catch (final Exception e) {
            this.logger.error(e);
        }
    }
}
