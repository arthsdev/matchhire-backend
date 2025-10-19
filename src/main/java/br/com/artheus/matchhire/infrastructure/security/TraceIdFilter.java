package br.com.artheus.matchhire.infrastructure.security;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * HTTP filter that generates a unique traceId for every incoming request.
 * <p>
 * The traceId is used to link logs that belong to the same request.
 * It's stored in the MDC (Mapped Diagnostic Context) so it appears automatically in log entries,
 * and added to the response header ("X-Trace-Id") for external tracking.
 * </p>
 */
@Component
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID_KEY = "traceId";
    private static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // Generate a unique identifier for this request
            String traceId = UUID.randomUUID().toString();

            // Store it in MDC so all logs for this request include the same traceId
            MDC.put(TRACE_ID_KEY, traceId);

            // Add traceId to the response header for external correlation
            if (response instanceof HttpServletResponse httpResponse) {
                httpResponse.setHeader(TRACE_HEADER, traceId);
            }

            // Continue processing the request
            chain.doFilter(request, response);

        } finally {
            // Clear the traceId after the request completes to prevent leaks between threads
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
