//package com.github.haschi.haushaltsbuch.abfrage;
//
//import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
//import cucumber.api.java.de.Angenommen;
//import cucumber.api.java.de.Dann;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import java.util.UUID;
//
//import static io.restassured.RestAssured.get;
//
//@Singleton
//public class HaushaltsbuchSteps
//{
//
//    @Inject
//    private EventStorageEngine engine;
//
//    @Inject
//    CucumberHooks steps;
//
//    final AggregateProxy<HaushaltsbuchTestaggregat> aggregat = new AggregateProxy<>(
//            HaushaltsbuchTestaggregat.class,
//            UUID.randomUUID());
//
//
//    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
//    public void ichHabeMitDerHaushaltsbuchführungBegonnen()
//    {
//
//        final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt = ImmutableHaushaltsbuchAngelegt.of(aggregat.getIdentifier());
//        steps.haushaltsführungBegonnen(aggregat, haushaltsbuchAngelegt);
//    }
//
//    @Dann("^werde ich ein leeres Haushaltsbuch sehen$")
//    public void werdeIchEinLeeresHaushaltsbuchSehen()
//    {
//        get("http://localhost:8080/haushaltsbuch/" + aggregat.getIdentifier().toString())
//                .then()
//                .statusCode(200);
//    }
//}
