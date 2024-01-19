package behaviourtests;


public class PaymentDTO {
    int amount;
    String token;
    String merchantId;

    public PaymentDTO(int amount, String token, String merchantId){
        this.amount=amount;
        this.token=token;
        this.merchantId=merchantId;
    }

    public int getAmount() {
        return amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getToken() {
        return token;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
