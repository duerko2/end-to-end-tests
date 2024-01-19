package behaviourtests.DTO;


import behaviourtests.Payment;

import java.util.List;

public class ManagerReport {
    private List<Payment> paymentList;
    public List<Payment> getPaymentList() {return paymentList;}
    public void setPaymentList(List<Payment> paymentList) {this.paymentList = paymentList;}

}