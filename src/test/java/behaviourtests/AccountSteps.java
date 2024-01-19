package behaviourtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: Marcus Jacobsen
 * Mob programming, all members
 */
public class AccountSteps {
    private Account account;
    private String name = "FirstName";
    private String lastName = "LastName";
    private String cpr = "1234567890";
    private String error;

    AccountRegistrationService accountRegistrationService = new AccountRegistrationService();
    private List<Account> accounts = new ArrayList<>();
    private List<Account> registeredAccounts = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();


    @Given("an unregistered {string}")
    public void anUnregisteredCustomer(String string) {
        // Write code here that turns the phrase above into concrete actions
        account = new Account();
        account.setName(name);
        account.setLastname(lastName);
        account.setType(new AccountType(string));
        account.setCpr(cpr);

    }

    @When("the account is being registered")
    public void theAccountIsBeingRegistered() {
        // Write code here that turns the phrase above into concrete actions
        try {
            account.setAccountId(accountRegistrationService.register(account));
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    @Then("the account is registered")
    public void theAccountIsRegistered() {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(account.getAccountId());
        // The reason for waiting is that the tokens are not created instantly
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("has {int} tokens")
    public void hasTokens(Integer int1) throws NoSuchAccountException {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(int1,accountRegistrationService.getTokens(account.getAccountId()).size());
    }




    @Then("should get an error saying {string}")
    public void shouldGetAnErrorSaying(String error) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(error, this.error);
    }

    @Given("a list of {int} unregistered {string}s")
    public void aListOfUnregistered(int arg0, String arg1) {

        for (int i = 0; i < arg0; i++) {
            account = new Account();
            account.setName(name + i);
            account.setLastname(lastName + i);
            account.setType(new AccountType(arg1));
            account.setCpr(cpr + i);
            accounts.add(account);
        }
    }

    @When("the accounts are being registered concurrently")
    public void theAccountsAreBeingRegistered() {
        for (Account a : accounts) {
            threads.add(new Thread(() -> {
                try {
                    a.setAccountId(accountRegistrationService.register(a));
                    registeredAccounts.add(a);
                } catch (AccountAlreadyExists e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        for (Thread t : threads) {
            t.start();
        }

    }

    @Then("the {int} accounts are registered")
    public void theAccountsAreRegistered(int arg0) throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }

        for (Account a : registeredAccounts) {
            assertNotNull(a.getAccountId());
        }

        assertEquals(arg0, registeredAccounts.size());
    }

    @And("all have {int} tokens")
    public void allHaveTokens(int arg0) {
        for (Account a : registeredAccounts) {
            assertEquals(arg0, accountRegistrationService.getTokens(a.getAccountId()).size());
        }
    }
    @After
    public void cleanUp() {
        if (account == null) return;
        accountRegistrationService.deleteAccount(account.getAccountId());

        for(Account a : registeredAccounts){
            accountRegistrationService.deleteAccount(a.getAccountId());
        }
    }
}

