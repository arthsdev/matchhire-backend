package br.com.artheus.matchhire.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Filter responsible for generating a unique traceId for every incoming HTTP request.
 * The traceId is stored in the MDC (Mapped Diagnostic Context) for log correlation
 * and also added to the HTTP response header for external tracking.
 */
@Component
public class TraceIdFilter implements Filter {

    private static final String TRACE_ID = "traceId";
    private static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // Generate a unique trace identifier (UUID)
            String traceId = UUID.randomUUID().toString();

            // Store it in MDC (so logs automatically include it)
            MDC.put(TRACE_ID, traceId);

            // Add it to the HTTP response header
            if (response instanceof HttpServletResponse httpResponse) {
                httpResponse.setHeader(TRACE_HEADER, traceId);
            }

            // Continue the request chain
            chain.doFilter(request, response);

        } finally {
            // Clear MDC to avoid leaking context between requests (important in async environments)
            MDC.remove(TRACE_ID);
        }
    }
}
