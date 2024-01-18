package behaviourtests;

import behaviourtests.DTO.CustomerReportDTO;
import behaviourtests.DTO.ManagerReport;
import behaviourtests.DTO.MerchantReportDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class ReportingService {
    Client client = ClientBuilder.newClient();
    WebTarget r = client.target("http://localhost:8181/reports/");

    public ManagerReport getManagerReport() {

        var response = r.path("manager/").request().get();
        return response.readEntity(ManagerReport.class);
    }

    public List<CustomerReportDTO> getCustomerReport(String customerId) {
        var response = r.path("customer/" + customerId).request().get();
        GenericType<List<CustomerReportDTO>> listType = new GenericType<>() {};
        return response.readEntity(listType);
    }

    public List<MerchantReportDTO> getMerchantReport(String merchantId) {
        var response = r.path("merchant/" + merchantId).request().get();
        GenericType<List<MerchantReportDTO>> listType = new GenericType<>() {};
        return response.readEntity(listType);
    }

}