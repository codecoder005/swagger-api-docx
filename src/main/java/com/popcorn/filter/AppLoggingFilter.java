package com.popcorn.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class AppLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // Generate or extract a trace ID
            String traceId = UUID.randomUUID().toString();

            // Optionally extract user ID from request (e.g., JWT or session)
            String userId = httpRequest.getHeader("X-User-Id");

            // Put values in MDC
            MDC.put("traceId", traceId);
            if (userId != null) {
                MDC.put("userId", userId);
            }

            chain.doFilter(request, response);
        } finally {
            // Clear MDC to prevent data leakage
            MDC.clear();
        }
    }
}
