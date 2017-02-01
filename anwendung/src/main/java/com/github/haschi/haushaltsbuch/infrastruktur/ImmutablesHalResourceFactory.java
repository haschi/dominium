package com.github.haschi.haushaltsbuch.infrastruktur;

import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.domain.hal.HalResourceFactory;
import com.strategicgains.hyperexpress.exception.ResourceException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ImmutablesHalResourceFactory extends HalResourceFactory
{
    @Override
    /**
     * Entry-point into the deep-copy functionality.
     *
     * @param from an Object instance, never null.
     * @param to a presumably-empty Resource instance.
     */
    protected void copyProperties(final Object from, final Resource to)
    {
        copyProperties0(from.getClass(), from, to);
    }

    private void copyProperties0(final Class<?> type, final Object from, final Resource to)
    {
        if (type == null)
        {
            return;
        }
        if (Resource.class.isAssignableFrom(type))
        {
            to.from((Resource) from);
            return;
        }

        final Field[] fields = getDeclaredFields(type);

        try
        {
            for (final Field f : fields)
            {
                if (isIncluded(f))
                {
                    f.setAccessible(true);
                    final Object value = f.get(from);

                    if (value != null)
                    {
                        addProperty(to, f, value);
                    }
                }
            }
        } catch (final IllegalAccessException e)
        {
            throw new ResourceException(e);
        }

        copyProperties0(type.getSuperclass(), from, to);
    }

    public static final int IGNORED_FIELD_MODIFIERS = Modifier.STATIC | Modifier.TRANSIENT | Modifier.VOLATILE;

    private boolean isIncluded(final Field f)
    {
        return (f.getModifiers() & IGNORED_FIELD_MODIFIERS) == 0;
    }

    private Field[] getDeclaredFields(final Class<?> type)
    {
        return type.getDeclaredFields();
    }
}
