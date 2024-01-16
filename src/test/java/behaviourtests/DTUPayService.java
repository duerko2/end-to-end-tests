package behaviourtests;

import dtu.ws.fastmoney.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class DTUPayService {

    ClientBuilder builder = ClientBuilder.newBuilder().connectTimeout(2000, TimeUnit.MILLISECONDS).readTimeout(5000, TimeUnit.MILLISECONDS);
    Client client = builder.build();
    WebTarget r = client.target("http://localhost:9090/");

    public boolean pay(Payment p) {

        Response response;
        try {
            response = r.path("payments").request().post(Entity.entity(p, "application/json"));
        } catch (Exception e) {
            return false;
        }

        if (response.getStatus() == 200) {
            return true;
        } else {
            System.out.println("response.getStatus() = " + response.getStatus());
            return false;
        }
    }

    public String registerBankAccount(String customerName, String customerLastName, String customerCPR, int balance) {
        BankService bank = new BankServiceService().getBankServicePort();

        User user = new User();
        user.setCprNumber(customerCPR);
        user.setFirstName(customerName);
        user.setLastName(customerLastName);

        try {

            return bank.createAccountWithBalance(user, new BigDecimal(balance));

        }catch (BankServiceException_Exception e){
            return "NO ACCOUNT CREATED" + e.getMessage();
        }

    }

    public void deleteBankAccount(String bankId) throws BankServiceException_Exception {
        BankService bank = new BankServiceService().getBankServicePort();
        bank.retireAccount(bankId);
    }

    public dtu.ws.fastmoney.Account getBankAccount(String customerBankId) throws BankServiceException_Exception {

        BankService bank  = new  BankServiceService().getBankServicePort();
        return bank.getAccount(customerBankId);

    }
}
