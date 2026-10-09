package ucb.edu.bo.internship.internship_backend.service.impl;

import jakarta.ws.rs.core.Response;
import org.apache.catalina.User;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dto.UsuarioRegistroDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;
import ucb.edu.bo.internship.internship_backend.service.IKeycloakService;
import ucb.edu.bo.internship.internship_backend.util.KeycloakProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class KeycloakServiceImpl implements IKeycloakService {

    private final Logger logger = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    @Value("${KEYCLOAK_CLIENT_ID}")
    private String keycloak_client_id;
    @Value("${KEYCLOAK_REALM}")
    private String keycloak_realm;

    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource().users().list();
    }

    @Override
    public List<UserRepresentation> findUsersByUsername(String username) {
        return KeycloakProvider.getRealmResource().users().searchByUsername(username, true);
    }

    @Override
    public UserRepresentation createUser(@NonNull UsuarioRegistroDto userDto) {
        int status = 0;
        UsersResource userResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDto.getPrimerNombre());
        userRepresentation.setLastName(userDto.getApellidoPaterno());
        userRepresentation.setEmail(userDto.getCorreo());
        userRepresentation.setUsername(userDto.getNombreUsuario());
        userRepresentation.setEnabled(true);

        Response response = userResource.create(userRepresentation);
        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf('/') + 1);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDto.getPassword());

            userResource.get(userId).resetPassword(credentialRepresentation);
            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> roleRepresentations = null;

            if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
                realmResource.clients().findByClientId(keycloak_client_id).forEach(
                        clientRepresentation -> {
                            List<RoleRepresentation> finalRoleRepresentation = new ArrayList<>();
                            userDto.getRoles().forEach(role -> {
                                finalRoleRepresentation.add(realmResource.clients().get(clientRepresentation.getId())
                                        .roles().get(role).toRepresentation());
                            });
                            realmResource.users().get(userId).roles().clientLevel(clientRepresentation.getId())
                                    .add(finalRoleRepresentation);
                        });
            }

            return userResource.get(userId).toRepresentation();
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String userId) {
        KeycloakProvider.getUserResource().get(userId).remove();
    }

    @Override
    public UserRepresentation updateUser(String userId, UsuarioRegistroDto userDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        if (userDto.getPrimerNombre() != null) {
            userRepresentation.setFirstName(userDto.getPrimerNombre());
        }
        if (userDto.getApellidoPaterno() != null) {
            userRepresentation.setLastName(userDto.getApellidoPaterno());
        }
        if (userDto.getCorreo() != null) {
            userRepresentation.setEmail(userDto.getCorreo());
        }
        if (userDto.getNombreUsuario() != null) {
            userRepresentation.setUsername(userDto.getNombreUsuario());
        }
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        if (userDto.getPassword() != null) {
            credentialRepresentation.setValue(userDto.getPassword());
            userRepresentation.setCredentials(List.of(credentialRepresentation));
        }

        UserResource userResource = KeycloakProvider.getUserResource().get(userId);
        userResource.update(userRepresentation);

        return userResource.toRepresentation();
    }

    @Override
    public UserRepresentation updateUserRoles(String userId, List<String> roles) {
        RealmResource realmResource = KeycloakProvider.getRealmResource();
        UserResource userResource = KeycloakProvider.getUserResource().get(userId);
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        realmResource.clients().findByClientId(keycloak_client_id).forEach(clientRepresentation -> {
            List<RoleRepresentation> finalRoleRepresentations = new ArrayList<>();
            roles.forEach(role -> {
                finalRoleRepresentations.add(
                        realmResource.clients().get(clientRepresentation.getId()).roles().get(role).toRepresentation());
            });
            userResource.roles().clientLevel(clientRepresentation.getId()).add(finalRoleRepresentations);
        });
        userResource.roles().realmLevel().add(roleRepresentations);
        return userResource.toRepresentation();
    }

    @Override
    public UserRepresentation addRealmRoleToUser(String userId, String role_name) {
        String client_id = keycloak_client_id;

        UserResource user = KeycloakProvider
                .getRealmResource()
                .users()
                .get(userId);

        ClientsResource client = KeycloakProvider.getRealmResource().clients();
        ArrayList<ClientRepresentation> clients = new ArrayList<ClientRepresentation>();
        client.findAll().forEach(clients::add);

        // show all clients
        for (ClientRepresentation clientRepresentation : clients) {

            if (clientRepresentation.getClientId().equals(keycloak_client_id)) {
               
                // show all roles of the client
                RolesResource role = KeycloakProvider.getRealmResource().clients().get(clientRepresentation.getId())
                        .roles();
                ArrayList<RoleRepresentation> roles = new ArrayList<RoleRepresentation>();
                role.list().forEach(roles::add);
                for (RoleRepresentation roleRepresentation : roles) {
                    if (roleRepresentation.getName().equals(role_name)) {
                        List<RoleRepresentation> roleToAdd = new LinkedList<>();
                        roleToAdd.add(roleRepresentation);
                        user.roles().clientLevel(clientRepresentation.getId()).add(roleToAdd);
                        return user.toRepresentation();
                    }
                }

            }
        }
        return user.toRepresentation();
    }

    @Override
    public UserRepresentation findUserBySubject(String subject) {
        List<UserRepresentation> users = KeycloakProvider.getRealmResource().users().list();
        Optional<UserRepresentation> userOptional = users.stream()
                .filter(user -> user.getId().equals(subject))
                .findFirst();
        return userOptional.orElse(null);
    }

    @Override
    public UserRepresentation addRoleToUser(String userId, String roleName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRoleToUser'");
    }
}
