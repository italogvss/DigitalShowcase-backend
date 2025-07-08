package italogvss.digitalshowcase.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VerifyUpToDateUsers {

  private static final Logger logger = LoggerFactory.getLogger(VerifyUpToDateUsers.class);

  @Autowired
  private UserRepository repository;

  // Expressão cron para executar todos os dias às 00:00:00
  @Scheduled(cron = "0 0 0 * * ?")
  //@Scheduled(fixedRate = 10000)
  public void verificarContasVencidas() {
      logger.info("Verificando contas vencidas...");

      // Lógica para verificar contas vencidas
      List<User> allUsers = repository.findAll();

      // Obter a data atual
      LocalDate hoje = LocalDate.now();

      // Filtrar usuários com contas vencidas
      List<User> usuariosVencidos = allUsers.stream()
          .filter(user -> user.getValidUntil() != null && user.getValidUntil().isBefore(hoje))
          .collect(Collectors.toList());

      // Desativar as contas vencidas
      for (User user : usuariosVencidos) {
          user.setIsActive(false);
      }

      // Salvar as alterações no banco de dados
      repository.saveAll(usuariosVencidos);

      // Log the date and number of deactivated users
      logger.info("Scheduled task ran on: " + hoje + ". Number of users deactivated: " + usuariosVencidos.size());
  }
}
