package ucb.edu.bo.internship.internship_backend.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import ucb.edu.bo.internship.internship_backend.dto.UsuarioRegistroDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;

import java.util.List;

public interface IKeycloakService {
    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> findUsersByUsername(String username);
    UserRepresentation createUser(UsuarioRegistroDto userDto);
    void deleteUser(String userId);
    UserRepresentation updateUser(String userId, UsuarioRegistroDto userDto);
    UserRepresentation updateUserRoles(String userId, List<String> roles);
    UserRepresentation findUserBySubject(String subject);
    UserRepresentation addRoleToUser(String userId, String roleName);
    UserRepresentation addRealmRoleToUser(String userId, String roleName);
}
