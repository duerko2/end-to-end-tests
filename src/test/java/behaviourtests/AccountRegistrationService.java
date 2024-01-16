package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class AccountRegistrationService {
	Client client = ClientBuilder.newClient();
	WebTarget r = client.target("http://localhost:8080/");

	public Account register(Account c) throws AccountAlreadyExists {


		var response = r.path("accounts").request().post(Entity.entity(c, "application/json"));

		switch(response.getStatus()) {
			case 200:
				return response.readEntity(Account.class);
			case 409:
				throw new AccountAlreadyExists("that account already exists");
			default:
				return null;
		}
	}

	public void deleteAccount(String accountId) {
		if(accountId==null){
			return;
		}

		r.path("accounts").path(accountId).request().delete();
	}

	public Account getAccount(String accountId) throws NoSuchAccountException {

		var response = r.path("accounts").path(accountId).request().get();
		if(response.getStatus() == 200) {
			return response.readEntity(Account.class);
		} else {
			throw new NoSuchAccountException("Account doesn't exist");
		}
	}
}
