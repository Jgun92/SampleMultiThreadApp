package com.mbicycle.config;


import com.mbicycle.config.request.RequestWrapper;
import com.mbicycle.model.entity.RequestLog;
import com.mbicycle.jpa.RequestLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RequestLoggingFilterConfig extends OncePerRequestFilter {

    @Autowired
    RequestLogRepository requestLogDao;

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        logRequest(httpServletRequest);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void logRequest(final HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        msg.append("request id=").append(counter.incrementAndGet()).append("; ");

        if (request.getMethod() != null) {
            msg.append("method=").append(request.getMethod()).append("; ");
        }
        if (request.getContentType() != null) {
            msg.append("content type=").append(request.getContentType()).append("; ");
        }
        msg.append("uri=").append(request.getRequestURI());
        if (request.getQueryString() != null) {
            msg.append('?').append(request.getQueryString());
        }

        if (request instanceof RequestWrapper &&
                !isMultipart(request) &&
                !isBinaryContent(request)) {

            RequestWrapper requestWrapper = (RequestWrapper) request;
            try {
                String charEncoding = requestWrapper.getCharacterEncoding() != null
                        ? requestWrapper.getCharacterEncoding()
                        : "UTF-8";
                msg.append("; payload=").append(new String(requestWrapper.toByteArray(), charEncoding));
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse request payload", e);
            }
        }
        log.info(msg.toString());
        requestLogDao.save(new RequestLog(msg.toString()));
    }


    private boolean isBinaryContent(final HttpServletRequest request) {
        return request.getContentType() != null && (request.getContentType().startsWith("image") ||
                request.getContentType().startsWith("video") || request.getContentType().startsWith("audio"));
    }

    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }
}
