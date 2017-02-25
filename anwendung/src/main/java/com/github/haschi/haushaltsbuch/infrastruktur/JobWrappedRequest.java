package com.github.haschi.haushaltsbuch.infrastruktur;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class JobWrappedRequest extends ServletRequestWrapper
{
    private Map<String, String[]> allParameters = null;

    public JobWrappedRequest(final ServletRequest servletRequest, final UUID jobId)
    {
        super(servletRequest);

        final TreeMap<String, String[]> p = new TreeMap<>();
        p.putAll(servletRequest.getParameterMap());
        p.put("job", new String[]{jobId.toString()});

        this.allParameters = Collections.unmodifiableMap(p);
    }

    @Override
    public String getParameter(final String name)
    {
        final String[] strings = getParameterMap().get(name);
        if (strings != null)
        {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
        return this.allParameters;
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name)
    {
        return getParameterMap().get(name);
    }
}
