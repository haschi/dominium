package com.github.haschi.dominium.persistenz.atom;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

public class Eintrag {
    public String title;
    public URI id;
    public ZonedDateTime updated;
    public Author author;
    public String summary;
    public List<Links> links;
}
