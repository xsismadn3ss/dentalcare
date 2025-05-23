package com.dentalcare.g5.main.config.monitoring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestTimingFilter extends OncePerRequestFilter {
    private static final String RESPONSE_TIME_HEADER = "X-Response-Time-ms";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try{
            filterChain.doFilter(request, response);
        }finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            if(!response.isCommitted()){
                response.setHeader(RESPONSE_TIME_HEADER, String.valueOf(duration));
            }

            logger.info(String.format("Request to %s took %d ms", request.getRequestURI(), duration));
        }
    }
}
