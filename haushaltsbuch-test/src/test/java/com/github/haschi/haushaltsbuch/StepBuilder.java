package com.github.haschi.haushaltsbuch;

@FunctionalInterface
public interface StepBuilder
{
    AbstractAktuellesInventarSteps create(AbstractInventarSteps steps);
}
