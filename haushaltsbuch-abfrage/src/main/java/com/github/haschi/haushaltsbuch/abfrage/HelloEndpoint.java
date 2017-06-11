package com.github.haschi.haushaltsbuch.abfrage;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("hello")
public class HelloEndpoint {

//    @Inject
//    private Configuration konfiguration;

    // @Resource(mappedName = "java:jboss/jgroups/channel/haushaltsbuch-jgroups")
    // @Resource(lookup = "java:jboss/jgroups/channel/haushaltsbuch")
    // private JChannel channel;


    private Context context;

    // @EJB
    @Resource(mappedName = "java:module/EineJavaBean")
    private EineJavaBean bean;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Haushaltsbuch query").build();
    }

    //    @GET
    //    @Produces("text/plain")
    //    //@Path("world")
    //    public Response getWorld() throws Exception
    //    {
    //        return Response.ok(
    //                MessageFormat.format("Wert: {0}", bean.getWert()))
    //                .build();
    //    }
}
