package behaviourtests;

import behaviourtests.Token;
/**
 * @Author: Rasmus Bo Thomsen
 * Mob programming, all members
 */
public class Payment {

    String merchantId;
    int amount;
    Token token;
    String customerId;

    public Payment() {
    }

    public int getAmount() {
        return amount;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setAccountId(String accountId) {
        this.customerId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
