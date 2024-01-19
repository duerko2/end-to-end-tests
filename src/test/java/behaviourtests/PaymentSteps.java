package behaviourtests;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentSteps {

    String customerName = "name";
    String customerLastName = "lastname";
    String customerCPR = "9999999";
    String merchantName = "merchantName";
    String merchantLastName = "merchantlastname";
    String merchantCPR = "1111111";
    String customerBankId;
    String customerDTUPayId;
    String merchantBankId;
    String merchantDTUPayId;
    boolean successful;
    String errorMessage;

    AccountRegistrationService accountRegistrationService = new AccountRegistrationService();
    DTUPayService dtuPayService = new DTUPayService();
    List<Token> tokens;
    private Token token;
    TokenService tokenService = new TokenService();

    @Given("a customer with a bank account with balance {int}")
    public void aCustomerWithABankAccountWithBalance(int balance) throws BankServiceException_Exception {
        // CreateBankAccount
        customerBankId = dtuPayService.registerBankAccount(customerName , customerLastName, customerCPR, balance );
        assertEquals(balance ,dtuPayService.getBankAccount(customerBankId).getBalance().intValue());
    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDTUPay() throws AccountAlreadyExists, NoSuchAccountException{
        Account account = new Account();
        account.setName(customerName);
        account.setLastname(customerLastName);
        account.setCpr(customerCPR);
        account.setBankId(customerBankId);
        account.setType(new AccountType("customer"));

        customerDTUPayId = accountRegistrationService.register(account);

        assertEquals(customerDTUPayId,accountRegistrationService.getAccount(customerDTUPayId).getAccountId());
    }

    @Given("a merchant with a bank account with balance {int}")
    public void aMerchantWithABankAccountWithBalance(int balance) throws BankServiceException_Exception {
        // CreateBankAccount mock
        merchantBankId = dtuPayService.registerBankAccount(merchantName , merchantLastName, merchantCPR, balance );
        assertEquals(balance ,dtuPayService.getBankAccount(merchantBankId).getBalance().intValue());
    }

    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDTUPay() throws AccountAlreadyExists, NoSuchAccountException{
        Account account = new Account();
        account.setName(merchantName);
        account.setLastname(merchantLastName);
        account.setCpr(merchantCPR);
        account.setBankId(merchantBankId);
        account.setType(new AccountType("merchant"));

        merchantDTUPayId = accountRegistrationService.register(account);

        assertEquals(merchantDTUPayId,accountRegistrationService.getAccount(merchantDTUPayId).getAccountId());
    }

    @When("the merchant initiates a payment for {int} kr with the customer token")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int arg0) throws Throwable {
        Payment p = new Payment();
        p.setAmount(arg0);
        p.setMerchantId(merchantDTUPayId);
        p.setToken(token);

        successful = dtuPayService.pay(p);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(int balance) {

        try {
            assertEquals(balance ,dtuPayService.getBankAccount( customerBankId).getBalance().intValue());
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }

    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(int balance) {
        try {
            assertEquals(balance, dtuPayService.getBankAccount(merchantBankId).getBalance().intValue());
        }catch (BankServiceException_Exception e){

        }

    }

    @Given("a customer has at least {int} token")
    public void aCustomerHasAtLeastToken(int arg0) throws NoSuchAccountException{
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        tokens = tokenService.getTokens(customerDTUPayId);
        System.out.println("tokens.size() = " + tokens.size());
        assertTrue(tokens.size() >= arg0);
        token = tokens.get(0);
    }

    @After
    public void tearDown(){
        accountRegistrationService.deleteAccount(customerDTUPayId);
        accountRegistrationService.deleteAccount(merchantDTUPayId);
        // Delete bank accounts
        try {
            dtuPayService.deleteBankAccount(customerBankId);
        }catch (Exception e){

        }
        try {
            dtuPayService.deleteBankAccount(merchantBankId);
        }catch (Exception e){

        }
    }
    @When("the merchant initiates {int} payments sequentially for {int} kr with a customer token")
    public void theMerchantInitiatesPaymentsSequentiallyForKrWithACustomerToken(int arg0, int arg1) {
        for (int i = 0; i < arg0; i++) {
            System.out.println("i = " + i);
            token = tokens.get(i);
            Payment p = new Payment();
            p.setAmount(arg1);
            p.setMerchantId(merchantDTUPayId);
            p.setToken(token);

            successful = dtuPayService.pay(p);
            if(!successful){
                return;
            }
        }
    }
    @Then("all payments are successful")
    public void allPaymentsAreSuccessful() {
        assertTrue(successful);
    }


    @When("the merchant initiates {int} payments concurrently for {int} kr with a customer token")
    public void theMerchantInitiatesPaymentsConcurrentlyForKrWithACustomerToken(int arg0, int arg1) throws InterruptedException {
        successful = true;

        // start arg0 threads and join them all
        Thread[] threads = new Thread[arg0];
        for (int i = 0; i < arg0; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                Payment p = new Payment();
                p.setAmount(arg1);
                p.setMerchantId(merchantDTUPayId);
                p.setToken(tokens.get(finalI));

                boolean b = dtuPayService.pay(p);
                if(!b){
                    successful = false;
                } else {
                    System.out.println("Payment in conccurent successful "+ finalI);
                }

            });
            threads[i].start();
        }
        for (int i = 0; i < arg0; i++) {
            threads[i].join();
        }

    }

    @Given("a customer with no bank account")
    public void aCustomerWithNoBankAccount() {
        customerBankId = "invalid bank account";
    }

    @Then("the payment is unsuccessful")
    public void thePaymentIsUnsuccessful() {
        assertTrue(!successful);
    }

    @When("the merchant initiates a payment for {int} kr with an invalid token")
    public void theMerchantInitiatesAPaymentForKrWithAnInvalidToken(int arg0) {
        Payment p = new Payment();
        p.setAmount(arg0);
        p.setMerchantId(merchantDTUPayId);
        p.setToken(new Token("invalid token"));

        successful = dtuPayService.pay(p);
    }

    @And("that the customer is not registered with DTU Pay")
    public void thatTheCustomerIsNotRegisteredWithDTUPay() {

    }
}
