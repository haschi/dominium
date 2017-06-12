//package com.github.haschi.haushaltsbuch.abfrage;
//
//import org.axonframework.commandhandling.SimpleCommandBus;
//import org.axonframework.commandhandling.distributed.CommandDispatchException;
//import org.axonframework.commandhandling.distributed.DistributedCommandBus;
//import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
//import org.axonframework.jgroups.commandhandling.JGroupsConnector;
//import org.axonframework.serialization.JavaSerializer;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jgroups.JChannel;
//import org.jgroups.blocks.atomic.Counter;
//import org.jgroups.blocks.atomic.CounterService;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.wildfly.swarm.arquillian.DefaultDeployment;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@RunWith(Arquillian.class)
//@DefaultDeployment(testable = true)
//public class ZählerIncrementierenIT
//{
//    @Test
//    public void testHelloWithRest(final JChannel channel) throws Exception
//    {
//        assert channel != null : "channel nicht vorhanden";
//
//        // JChannel channel = new JChannel();
//        SimpleCommandBus localSegment = new SimpleCommandBus();
//        JGroupsConnector connector = new JGroupsConnector(
//                localSegment,
//                channel,
//                "haushaltsbuch-jgroups",
//                new JavaSerializer()
//        );
//
//        final DistributedCommandBus commandBus = new DistributedCommandBus(connector, connector);
//        final DefaultCommandGateway gateway = new DefaultCommandGateway(commandBus);
//        assertThatThrownBy(() -> gateway.send("Hello"))
//                .isInstanceOf(CommandDispatchException.class);
//    }
//
//    @Test
//    @Ignore
//    public void test_counter_ist_nicht_vorhanden(final JChannel channel) throws Exception
//    {
//        assert channel != null : "channel ist nicht vorhanden";
//        assertThat(channel).isNotNull();
//
//        CounterService counter_service = new CounterService(channel);
//        channel.connect("haushaltsbuch-jgroups");
//        Counter counter = counter_service.getOrCreateCounter("mycounter", 1);
//
//        assertThat(counter.get()).isEqualTo(1);
//    }
//}
