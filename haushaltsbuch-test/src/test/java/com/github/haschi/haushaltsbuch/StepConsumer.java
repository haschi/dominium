package com.github.haschi.haushaltsbuch;

@FunctionalInterface
public interface StepConsumer
{
    void apply(AbstractAktuellesInventarSteps steps);
}
