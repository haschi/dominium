package com.github.haschi.haushaltsbuch;

import java.util.List;

public interface AbstractInventarSteps
{
    AbstractAktuellesInventarSteps anlegen(List<Vermögenswert> vermögenswerte);
}
