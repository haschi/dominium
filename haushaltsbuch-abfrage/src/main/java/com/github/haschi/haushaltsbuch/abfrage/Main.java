package com.github.haschi.haushaltsbuch.abfrage;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.jgroups.JGroupsFraction;
import org.wildfly.swarm.spi.api.Fraction;

public class Main
{
    public static void main(String... args) throws Exception
    {
//        final Fraction jgroupsFraction = JGroupsFraction
//                .defaultFraction();
//

        Swarm swarm = createSwarm(args);
        swarm.start();
        swarm.deploy();
    }

    public static Swarm createSwarm(String... args) throws Exception
    {
        Swarm swarm = new Swarm(args);
        swarm.fraction(haushaltsbuchJgroupsFraction());
        swarm.fraction(EJBFraction.createDefaultFraction());
        // swarm.fraction(ManagementFraction.createDefaultFraction());

        // swarm.fraction(new ManagementConsoleFraction().contextRoot("/admin"));
        return swarm;
    }

    public static Fraction haushaltsbuchJgroupsFraction() {
        return new JGroupsFraction()
                .defaultChannel("haushaltsbuch")
                .stack( "udp", (s)->{
                    s.transport( "UDP", (t)->{
                        t.socketBinding("jgroups-udp");
                    });
                    s.protocol("TCP", (p -> {
                        p.property("bind_port", "9090");
                    }));
                    s.protocol("TCPPING", (p)-> {
                        p.property("initial_hosts", "localhost[9090],localhost[9091],localhost[9092],localhost[9093]")
                                .property("port_range", "4")
                                .property("timeout", "3000")
                                .property("num_initial_members", "4");
                    });
                    s.protocol( "FD_SOCK", (p)->{
                        p.socketBinding( "jgroups-udp-fd" );
                    });
                    s.protocol( "FD_ALL" );
                    s.protocol( "VERIFY_SUSPECT" );
                    s.protocol( "pbcast.NAKACK2" );
                    s.protocol( "UNICAST3" );
                    s.protocol( "pbcast.STABLE" );
                    s.protocol( "pbcast.GMS" );
                    s.protocol( "UFC" );
                    s.protocol( "MFC" );
                    s.protocol( "FRAG2" );
                    s.protocol( "RSVP" );
                    s.protocol( "COUNTER"/*, (p) -> {
                        p.property("bypass_bundling", "true")
                                .property("timeout", "5000");
                    }*/);
                })
                .channel( "haushaltsbuch", (c)->{
                    c.stack( "udp" );
                });
    }
}
