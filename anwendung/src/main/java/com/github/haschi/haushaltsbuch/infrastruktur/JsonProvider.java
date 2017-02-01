package com.github.haschi.haushaltsbuch.infrastruktur;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.TimeZone;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonProvider implements ContextResolver<ObjectMapper>
{

    private final ObjectMapper objectMapper;

    public JsonProvider()
    {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new Jdk8Module());
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        this.objectMapper.setTimeZone(TimeZone.getDefault());
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final SimpleModule halModule = new SimpleModule("HAL Module");
        halModule.addDeserializer(HalResource.class, new HalResourceDeserializer());
        halModule.addSerializer(HalResource.class, new HalResourceSerializer());
        this.objectMapper.registerModule(halModule);
    }

    @Override
    public ObjectMapper getContext(final Class<?> clazz)
    {
        return this.objectMapper;
    }
}
