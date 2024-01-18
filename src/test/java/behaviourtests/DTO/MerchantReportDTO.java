package behaviourtests.DTO;

import behaviourtests.Token;
import java.util.Objects;

public class MerchantReportDTO {
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

    private Token token;
    private int amount;

    public MerchantReportDTO(Token token, int amount) {
        this.token = token;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MerchantReportDTO)) return false;
        MerchantReportDTO that = (MerchantReportDTO) o;
        return amount == that.amount &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, amount);
    }

}
