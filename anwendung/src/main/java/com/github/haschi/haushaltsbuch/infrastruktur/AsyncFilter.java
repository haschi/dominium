package com.github.haschi.haushaltsbuch.infrastruktur;

//
//// @WebFilter(filterName = "AsyncFilter", urlPatterns = {"/*"}, asyncSupported = true)
//public class AsyncFilter implements Filter
//{
//    @Override
//    public void init(final FilterConfig filterConfig) throws ServletException
//    {
//
//    }
//
//    @Override
//    public void doFilter(
//            final ServletRequest servletRequest,
//            final ServletResponse servletResponse,
//            final FilterChain filterChain)
//            throws IOException, ServletException
//    {
//        final UUID jobId = UUID.randomUUID();
//        final JobWrappedRequest jobRequest = new JobWrappedRequest(servletRequest, jobId);
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy()
//    {
//
//    }
//}
