package behaviourtests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import behaviourtests.DTO.CustomerReportDTO;
import behaviourtests.DTO.ManagerReport;
import behaviourtests.DTO.MerchantReportDTO;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class ReportingSteps {

    ReportingService Reportingservice = new ReportingService();

    ManagerReport managerReport;
    List<CustomerReportDTO> customerReportDTOList;
    List<MerchantReportDTO> merchantReportDTOList;
    List<Payment> testPayments;
    List<CustomerReportDTO> expectedCustomerReport;
    List<MerchantReportDTO> expectedMerchantReport;
    DTUPayService dtuPayService = new DTUPayService();

    @Given("a set of payments in the payment repository")
    public void FillRepository() {

        Payment payment1 = new Payment();
        payment1.setAmount(100);
        payment1.setMerchantId("sælgermanden1");
        payment1.setCustomerId("købermanden2");
        payment1.setToken(new Token("123"));

        Payment payment2 = new Payment();
        payment2.setAmount(200);
        payment2.setMerchantId("sælgermanden2");
        payment2.setCustomerId("købermanden1");
        payment2.setToken(new Token("321"));

        testPayments = new ArrayList<>();

        testPayments.add(payment1);
        testPayments.add(payment2);


        dtuPayService.pay(payment1);
        dtuPayService.pay(payment2);

    }

    @And("a manager report generation request is received")
    public void aManagerReportGenerationRequestIsReceived() {

        managerReport = Reportingservice.getManagerReport();
        
    }

    @Then("a manager report is generated and returned with the same payments as those in the repository")
    public void aManagerReportIsGeneratedAndReturnedWithTheSamePaymentsAsThoseInTheRepository() {

        assertEquals(testPayments, managerReport.getPaymentList());

        assertEquals(testPayments.size(), managerReport.getPaymentList().size());

        assertTrue(testPayments.stream()
                .allMatch(testPayment -> managerReport.getPaymentList().stream()
                        .anyMatch(reportPayment ->
                                        testPayment.getAmount() == reportPayment.getAmount() &&
                                                testPayment.getMerchantId().equals(reportPayment.getMerchantId()) &&
                                                testPayment.getCustomerId().equals(reportPayment.getCustomerId()) &&
                                                testPayment.getToken().getRfid().equals(reportPayment.getToken().getRfid())
                        )));


    }

    @And("a customer report generation request is received")
    public void aCustomerReportGenerationRequestIsReceived() {

        customerReportDTOList = Reportingservice.getCustomerReport("købermanden1");

    }

    @Then("a customer report is generated and returned with the relevant payments as those in the repository")
    public void aCustomerReportIsGeneratedAndReturnedWithTheRelevantPaymentsAsThoseInTheRepository() {

        expectedCustomerReport = new ArrayList<>();

        expectedCustomerReport.add(new CustomerReportDTO(new Token("321"),100,"sælgermanden2"));

        assertEquals(expectedCustomerReport, customerReportDTOList);
    }

    @And("a merchant report generation request is received")
    public void aMerchantReportGenerationRequestIsReceived() {
        merchantReportDTOList = Reportingservice.getMerchantReport("sælgermanden1");
    }

    @Then("a merchant report is generated and returned with the relevant payments as those in the repository")
    public void aMerchantReportIsGeneratedAndReturnedWithTheRelevantPaymentsAsThoseInTheRepository() {

        expectedMerchantReport = new ArrayList<>();

        expectedMerchantReport.add(new MerchantReportDTO(new Token("123"),200));

        assertEquals(expectedMerchantReport, merchantReportDTOList);

    }
}
