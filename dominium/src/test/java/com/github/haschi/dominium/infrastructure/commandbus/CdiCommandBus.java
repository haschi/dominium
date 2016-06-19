package com.github.haschi.dominium.infrastructure.commandbus;

import com.github.haschi.dominium.testdomaene.generiert.Command;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

public final class CdiCommandBus {

    @Inject
    private BeanManager beanManager;

    public void send(final Command message) {
        this.beanManager.fireEvent(message.command());
    }
}
