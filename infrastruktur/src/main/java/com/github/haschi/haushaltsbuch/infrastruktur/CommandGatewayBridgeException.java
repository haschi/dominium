package com.github.haschi.haushaltsbuch.infrastruktur;

public class CommandGatewayBridgeException extends Exception
{
    private final ErrorCode error;

    public CommandGatewayBridgeException(final ErrorCode error, final String nachricht)
    {
        super(nachricht);
        this.error = error;
    }

    public ErrorCode getError()
    {
        return error;
    }
}
