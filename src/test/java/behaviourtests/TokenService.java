package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class TokenService {
    Client client = ClientBuilder.newClient();
    WebTarget r = client.target("http://localhost:8080/");

    public List<Token> getTokens(String accountId) {
        var response = r.path("accounts/"+accountId).path("/tokens").request().get();
        if(response.getStatus() == 200) {
            GenericType<List<Token>> listOfTokensType = new GenericType<>() {
            };
            return response.readEntity(listOfTokensType);
        } else {
            return List.of();
        }
    }
    public void requestTokens(String accountId, int amount) {
        r.path("accounts/"+accountId).path("tokens").path(""+amount).request().post(Entity.entity(amount, MediaType.TEXT_PLAIN_TYPE));
    }
}
