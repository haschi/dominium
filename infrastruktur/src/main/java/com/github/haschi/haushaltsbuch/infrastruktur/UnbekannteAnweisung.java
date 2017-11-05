package com.github.haschi.haushaltsbuch.infrastruktur;

public class UnbekannteAnweisung extends CommandGatewayBridgeException
{
    public UnbekannteAnweisung(final String command)
    {
        super(ErrorCode.UnbekannteAnweisung, command);
    }
}
