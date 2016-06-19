package com.github.haschi.dominium.testdomaene.generiert;

public interface Command {
    void apply(CommandDispatcher dispatcher);
}
