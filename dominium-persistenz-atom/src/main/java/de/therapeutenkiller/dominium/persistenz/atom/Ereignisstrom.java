package de.therapeutenkiller.dominium.persistenz.atom;

import de.therapeutenkiller.coding.aspekte.ValueObject;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ValueObject
public final class Ereignisstrom {

    public static final Ereignisstrom LEER = new Ereignisstrom();

    public String title;
    public URI id;
    public ZonedDateTime updated;
    public UUID streamId;
    public Author author;
    public boolean headOfStream = true;
    public URI selfUrl;
    @SuppressWarnings("checkstyle:membername")
    public String eTag;
    public List<Links> links = new ArrayList<>();
    public List<Eintrag> entries = new ArrayList<>();

    public boolean hatVorgänger() {
        return this.links.stream()
            .anyMatch(link -> link.relation.equals("previous"));
    }
}
