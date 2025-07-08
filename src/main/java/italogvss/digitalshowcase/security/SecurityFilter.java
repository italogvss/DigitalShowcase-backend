package italogvss.digitalshowcase.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import italogvss.digitalshowcase.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ComponentScan(basePackages = "com.example.auth.repositories.UserRepository")
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);
        if (token != null) {
            try {
                var login = tokenService.validateToken(token);
                if (login != null) {
                    UserDetails user = userRepository.findByUsername(login);
                    if (user != null) {

                        // Criar um objeto de autenticação com as autoridades do usuário
                        var authentication = new UsernamePasswordAuthenticationToken(user, null,
                                user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } else {
                        System.out.println("USER INEXISTENTE");
                        // Logar que o usuário não foi encontrado
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write("Token invalido");
                        response.getWriter().flush();

                        return; // Interromper a cadeia de filtros
                    }
                } else {
                    // Logar que o token não é válido
                    System.out.println("TOKEN INVALIDO");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write("Token invalido");
                        response.getWriter().flush();
                    return; // Interromper a cadeia de filtros
                }
            } catch (Exception e) {
                System.out.println("ERRO AUTHORIZATION");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write("Token invalido");
                        response.getWriter().flush();
                return; // Interromper a cadeia de filtros
            }
        }

        filterChain.doFilter(request, response); // Continuar a cadeia de filtros se tudo estiver OK
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
