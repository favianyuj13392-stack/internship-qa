package ucb.edu.bo.internship.internship_backend.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloakProvider {

    static Dotenv dotenv = Dotenv.load();

    private static final String SERVER_URL = dotenv.get("KEYCLOAK_SERVER_URL");
    private static final String REALM_NAME = dotenv.get("KEYCLOAK_REALM");
    private static final String REALM_MASTER = dotenv.get("KEYCLOAK_REALM_MASTER");
    private static final String ADMIN_CLI = "admin-cli";
    private static final String USER_CONSOLE = dotenv.get("KEYCLOAK_ADMIN_USER");
    private static final String USER_PASSWORD = dotenv.get("KEYCLOAK_ADMIN_PASSWORD");

    public static RealmResource getRealmResource(){
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER)
                .username(USER_CONSOLE)
                .password(USER_PASSWORD)
                .clientId(ADMIN_CLI)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();


        return keycloak.realm(REALM_NAME);
    }

    public static UsersResource getUserResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }

}
