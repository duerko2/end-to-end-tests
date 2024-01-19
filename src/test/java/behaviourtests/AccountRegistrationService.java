package behaviourtests;

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.quarkus.resteasy.common.runtime.jackson.QuarkusObjectMapperContextResolver;
import org.eclipse.persistence.oxm.record.JsonBuilderRecord;

import javax.json.JsonBuilderFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class AccountRegistrationService {
	Client client = ClientBuilder.newClient();
	WebTarget r = client.target("http://localhost:8080/");

	public String register(Account c) throws AccountAlreadyExists {
		AccountDTO requestBody = new AccountDTO(c.getName(), c.getLastname(), c.getType().getType(), c.getCpr(), c.getBankId());

		var response = r.path("accounts").request().post(Entity.entity(requestBody, "application/json"));

		switch(response.getStatus()) {
			case 200:
				return response.readEntity(String.class);
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
			var a =  response.readEntity(AccountDTO.class);
			var account = new Account();
			account.setAccountId(a.getAccountId());
			account.setName(a.getName());
			account.setLastname(a.getLastName());
			account.setType(new AccountType(a.getType()));
			account.setCpr(a.getCpr());
			account.setBankId(a.getBankId());
			return account;
		} else {
			throw new NoSuchAccountException("Account doesn't exist");
		}
	}

}
