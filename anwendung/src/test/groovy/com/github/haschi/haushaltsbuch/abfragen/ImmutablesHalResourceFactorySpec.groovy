package com.github.haschi.haushaltsbuch.abfragen

import com.github.haschi.haushaltsbuch.infrastruktur.ImmutablesHalResourceFactory
import com.strategicgains.hyperexpress.domain.Resource
import spock.lang.Specification
import testsupport.Data
import testsupport.ImmutableData

class ImmutablesHalResourceFactorySpec extends Specification {


    def "Alle Felder kopieren"() {
        given:
        ImmutablesHalResourceFactory factory = new ImmutablesHalResourceFactory();
        Data immutable = ImmutableData.builder()
                .name("matthias")
                .version(1)
                .build();

        when:
        Resource resource = factory.createResource(immutable)

        then:
        resource.getProperty("name") == "matthias"
        resource.getProperties().size() == 2
    }
}