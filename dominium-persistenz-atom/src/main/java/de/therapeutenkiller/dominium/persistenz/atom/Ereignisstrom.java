package de.therapeutenkiller.dominium.persistenz.atom;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Ereignisstrom {
    public String title;
    public URI id;
    public ZonedDateTime updated;
    public UUID streamId;
    public Author author;
    public boolean headOfStream;
    public URI selfUrl;
    @SuppressWarnings("checkstyle:membername")
    public String eTag;
    public List<Links> links;
    public List<Eintrag> entries;
}
