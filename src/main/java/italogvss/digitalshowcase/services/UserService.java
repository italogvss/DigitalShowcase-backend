package italogvss.digitalshowcase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import italogvss.digitalshowcase.DTO.PasswordChangeRequest;
import italogvss.digitalshowcase.DTO.UserDTO;
import italogvss.digitalshowcase.mapper.UserMapper;
import italogvss.digitalshowcase.model.User;
import italogvss.digitalshowcase.model.UserRole;
import italogvss.digitalshowcase.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    @Lazy
    private MediaService mediaService;

    @Autowired
    BCryptPasswordEncoder bce;

    // Criar um usuário
    public UserDTO create(UserDTO dto) {

        if ((repository.findByUsername(dto.username()) != null)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username já existe");
        }

        User owner = null;
        if ("ADMIN".equals(dto.role().toString())) {
            owner = null;
        } else if (dto.ownerId() != null) {
            owner = repository.findById(dto.ownerId()).orElse(null);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precisa ter um owner");
        }

        String encryptedPassword = bce.encode(dto.password());

        User newUser = new User(dto.username(),
                dto.name(),
                encryptedPassword,
                dto.role(),
                dto.isActive(),
                dto.cpf(),
                dto.telephone(),
                dto.email(),
                dto.city(),
                dto.state(),
                dto.address(),
                dto.validUntil(),
                owner);

        User savedUser = repository.save(newUser);
        return UserMapper.toDTO(savedUser);
    }

    // Listar todos os usuários
    public List<UserDTO> findAll(Long ownerId) {
        User user = repository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No records found for this ID"));
        if (user.getRole() == UserRole.ADMIN) {
            return repository.findAll().stream().map(UserMapper::toDTO).toList();
        }
        return repository.findAllByOwnerId(ownerId).stream().map(UserMapper::toDTO).toList();
    }

    public User findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No records found for this ID"));
        return user;
    }

    public UserDTO updateProtected(UserDTO dto) {
        User user = repository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No records found for this ID"));

        if (dto.name() != null)
            user.setName(dto.name());
        if (dto.username() != null)
            user.setUsername(dto.username());
        if (dto.isActive() != null)
            user.setIsActive(dto.isActive());
        if (dto.cpf() != null)
            user.setCpf(dto.cpf());
        if (dto.telephone() != null)
            user.setTelephone(dto.telephone());
        if (dto.email() != null)
            user.setEmail(dto.email());
        if (dto.city() != null)
            user.setCity(dto.city());
        if (dto.state() != null)
            user.setState(dto.state());
        if (dto.address() != null)
            user.setAdress(dto.address());
        if (dto.validUntil() != null)
            user.setValidUntil(dto.validUntil());

        User newUser = repository.save(user);
        return UserMapper.toDTO(newUser);
    }

    public void updatePassword(PasswordChangeRequest dto) {
        User user = repository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No records found for this ID"));
        String encryptedPassword = bce.encode(dto.newPassword());
        user.setPassword(encryptedPassword);
        User newUser = repository.save(user);
    }

    // Deletar um usuário
    @Transactional
public void delete(Long id) {
    User user = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No records found for this ID"));

    if (user.getRole() == UserRole.ADMIN) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete an ADMIN user");
    }

    // Encontrar novo dono para os usuários que pertencem a esse user
    User newOwner = repository.findFirstByRole(UserRole.ADMIN)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ADMIN user found to transfer ownership"));

    // Buscar todos os usuários que possuem esse usuário como owner
    List<User> usersToUpdate = repository.findAllByOwnerId(user.getId());

    // Atualizar o owner desses usuários para o novo dono
    for (User u : usersToUpdate) {
        u.setOwner(newOwner);
    }

    // Salvar todos os usuários atualizados
    repository.saveAll(usersToUpdate);

    // Remover todas as mídias associadas
    mediaService.deleteByUserId(user.getId().toString());

    // Agora pode excluir
    repository.delete(user);
}


}
