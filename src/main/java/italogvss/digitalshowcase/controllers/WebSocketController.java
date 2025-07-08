package italogvss.digitalshowcase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import italogvss.digitalshowcase.DTO.ValidationDTO;
import italogvss.digitalshowcase.repositories.PlaylistRepository;

@Controller
public class WebSocketController {

    @Autowired
    PlaylistRepository repository;

    // O cliente envia uma mensagem para "/app/send-playlist"
    @MessageMapping("/playlist/validade-code")
    @SendTo("/topic/is-code-valid") // Todos os inscritos nesse tópico recebem a resposta
    public ValidationDTO processPlaylist(String message) {
        Boolean isValid = repository.findByCode(message) != null;
        return new ValidationDTO(isValid);
    }
}
