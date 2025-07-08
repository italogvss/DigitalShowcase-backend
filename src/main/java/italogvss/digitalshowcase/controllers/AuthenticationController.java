package italogvss.digitalshowcase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.AuthenticationDTO;
import italogvss.digitalshowcase.DTO.LoginResponseDTO;
import italogvss.digitalshowcase.DTO.UserDTO;
import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.security.TokenService;
import italogvss.digitalshowcase.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService service;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var user = (User) auth.getPrincipal();
            if (!user.getIsActive())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario desabilitado");
            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(user.getId(), user.getName(), user.getRole(), token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO data) {

        try {
            return ResponseEntity.ok(service.create(data));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }

    }

    /*
     * @PutMapping("/update")
     * public ResponseEntity<Object> updatePassword(@RequestBody @Valid
     * UpdatePasswordDTO data) {
     * // Recupera o usuário pelo login
     * User user = repository.findById(data.id())
     * .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
     * "User not found"));
     * 
     * // Opcional: autentica o usuário com a senha atual
     * var currentAuth = new UsernamePasswordAuthenticationToken(data.login(),
     * data.currentPassword());
     * authenticationManager.authenticate(currentAuth);
     * 
     * // Atualiza a senha
     * user.setPassword(new BCryptPasswordEncoder().encode(data.newPassword()));
     * repository.save(user);
     * // Atualiza o contexto de segurança com as novas credenciais
     * SecurityContextHolder.getContext().setAuthentication(
     * new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
     * 
     * // Gera um novo token JWT para o usuário
     * var newToken = tokenService.generateToken(user);
     * 
     * return ResponseEntity.ok(newToken);
     * }
     */

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        String subject = tokenService.validateToken(token);
        if (!subject.isEmpty()) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
