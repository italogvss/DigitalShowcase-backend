package italogvss.digitalshowcase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import italogvss.digitalshowcase.DTO.UserDTO;
import italogvss.digitalshowcase.model.User;

public class UserMapper {
    
  public static UserDTO toDTO(User user) {
      return new UserDTO(
          user.getName(),
          user.getUsername(),
          null, // Senha não deve ser retornada no DTO
          user.getRole(),
          user.getIsActive(),
          user.getCpf(),
          user.getTelephone(),
          user.getEmail(),
          user.getCity(),
          user.getState(),
          user.getAdress(),
          user.getValidUntil(),
          user.getOwner() != null ? user.getOwner().getId() : null,
          user.getId()
      );
  }

  public static List<UserDTO> toDTOList(List<User> userList) {
      return userList.stream().map(UserMapper::toDTO).collect(Collectors.toList());
  }

  public static User toEntity(UserDTO dto) {
      User user = new User();
      user.setName(dto.name());
      user.setUsername(dto.username());
      user.setRole(dto.role());
      user.setIsActive(dto.isActive());
      user.setCpf(dto.cpf());
      user.setTelephone(dto.telephone());
      user.setEmail(dto.email());
      user.setCity(dto.city());
      user.setState(dto.state());
      user.setAdress(dto.address());
      user.setValidUntil(dto.validUntil());
      return user;
  }
}

