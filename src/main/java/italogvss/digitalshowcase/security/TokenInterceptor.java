package italogvss.digitalshowcase.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

  @Autowired
  TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getParameter("token");
    var login = tokenService.validateToken(token);
    if (token == null || login == null) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.getWriter().write("Token invalido");
      response.getWriter().flush();
      return false;
    }
    return true;
  }
}
