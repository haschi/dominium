package testsupport;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import javaslang.collection.Stream;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventhandling.annotation.EventHandler;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public final class DieWelt
{

    private final Map<String, List<? super Object>> ereignisse = new HashMap<>();
    private Aggregatkennung aktuelleHaushaltsbuchId;

    public Stream<? super Object> ereignisstrom()
    {
        return Stream.ofAll(aktuellerEreignisstrom());
    }

    public <T> List<? super T> aktuellerEreignisstrom()
    {
        return this.getStream(this.getAktuelleHaushaltsbuchId());
    }

    private <T> List<? super T> getStream(final Aggregatkennung haushaltsbuchId)
    {
        return this.ereignisse.get(haushaltsbuchId.toString());
    }

    public Aggregatkennung getAktuelleHaushaltsbuchId()
    {
        return this.aktuelleHaushaltsbuchId;
    }

    public void setAktuelleHaushaltsbuchId(final Aggregatkennung aktuelleHaushaltsbuchId)
    {
        this.aktuelleHaushaltsbuchId = aktuelleHaushaltsbuchId;
    }

    @EventHandler
    public void falls(final Object ereignis, final DomainEventMessage message)
    {

        final String aggregateIdentifier = message.getAggregateIdentifier().toString();

        if (!this.ereignisse.containsKey(aggregateIdentifier))
        {
            this.ereignisse.put(aggregateIdentifier, new ArrayList<>());
        }

        final List<? super Object> haushaltsbuchEreignises = this.ereignisse.get(aggregateIdentifier);
        haushaltsbuchEreignises.add(ereignis);
    }
}
