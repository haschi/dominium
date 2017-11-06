package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;

import java.text.MessageFormat;

public final class LoggingCallback implements CommandCallback<Object, Object>
{
    private final Logger logger = LoggerFactory.getLogger(LoggingCallback.class);

    public static final LoggingCallback INSTANCE = new LoggingCallback();

    private LoggingCallback()
    {
    }

    @Override
    public void onSuccess(final CommandMessage message, final Object result)
    {
        logger.info(
                MessageFormat.format(
                        "[{0}] {1} => OK {2}",
                        message.getCommandName(),
                        message.getIdentifier(),
                        result));
    }

    @Override
    public void onFailure(final CommandMessage<?> message, final Throwable cause)
    {
        logger.warn(
                MessageFormat.format(
                        "[{0}] {1} => OK {2}",
                        message.getCommandName(),
                        message.getIdentifier(),
                        cause.getLocalizedMessage()));
    }
}
