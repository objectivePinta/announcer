package net.uranus.astra.auth.model.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
  private static Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    LOG.info(req.getAuthType());
    Enumeration<String> headers = req.getHeaderNames();
    for (final Enumeration<String> values = req.getHeaderNames(); values.hasMoreElements();) {
      final String nextElement = values.nextElement();
      LOG.info("{}:{}", nextElement, req.getHeader(nextElement));
    }
    chain.doFilter(req, res);
  }
}
