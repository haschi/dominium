package com.github.haschi.haushaltsbuch;

public class ErwartetesEreignisIstNichtEingetreten extends RuntimeException
{
    public ErwartetesEreignisIstNichtEingetreten(final RuntimeException ausnahme)
    {
        super(ausnahme);
    }
}
