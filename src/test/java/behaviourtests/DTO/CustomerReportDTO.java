package behaviourtests.DTO;


import behaviourtests.Token;

import java.util.Objects;

public class CustomerReportDTO {
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    private Token token;
    private int amount;
    private String merchantId;

    public CustomerReportDTO(Token token, int amount, String merchantId) {
        this.token = token;
        this.amount = amount;
        this.merchantId = merchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerReportDTO)) return false;
        CustomerReportDTO that = (CustomerReportDTO) o;
        return amount == that.amount &&
                Objects.equals(token, that.token) &&
                Objects.equals(merchantId, that.merchantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, amount, merchantId);
    }
}
