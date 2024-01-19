package behaviourtests;

public class AccountDTO {
    private String accountId;
    private String name;
    private String lastName;
    private String type;
    private String cpr;
    private String bankId;
    public AccountDTO() {
    }

    public AccountDTO(String name, String lastName, String type, String cpr, String bankId) {
        this.name = name;
        this.lastName = lastName;
        this.type = type;
        this.cpr = cpr;
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
