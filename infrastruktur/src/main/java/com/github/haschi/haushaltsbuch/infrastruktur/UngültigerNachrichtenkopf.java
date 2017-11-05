package com.github.haschi.haushaltsbuch.infrastruktur;

public class UngültigerNachrichtenkopf extends CommandGatewayBridgeException
{
    public UngültigerNachrichtenkopf(final String nachricht)
    {
        super(ErrorCode.UngültigerNachrichtenkopf, nachricht);
    }
}
