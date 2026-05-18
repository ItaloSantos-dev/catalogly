package com.italo.catalogy.infra;

import com.italo.catalogy.dto.nfe_io.createServiceInvoice.CreateServiceInvoiceRequestDTO;
import com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse.ServiceInvoiceResponseDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class NfeIoConfig {
    private final RestTemplate restTemplate = new RestTemplate();

    private final String urlBase = "https://api.nfe.io/v1/";

    private final String companyServiceId = "fe3b2192d1274720a33a85ec640616e6";
    @Getter
    private final String inssuerCnpj = "32058055000149";

    @Getter
    private  final String  inssuerName = "JOSE CELIO CARNEIRO BATISTA 39171604391";

    @Getter
    private final String inssuerStateRegistration = "094955472766";

    @Getter
    private final String  inssuerTradeName = "Catalogly";



    @Value("${NF_IO_TOKEN}")
    private String nfIoToken;



    private HttpHeaders setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", nfIoToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public ResponseEntity<ServiceInvoiceResponseDTO> inssueInvoiceService(CreateServiceInvoiceRequestDTO createServiceInvoiceRequestDTO){
        String urlFinal = urlBase + "companies/"+companyServiceId+"/serviceinvoices";
        HttpEntity<CreateServiceInvoiceRequestDTO> request = new HttpEntity<>(createServiceInvoiceRequestDTO, this.setHeaders());

        return this.restTemplate.postForEntity(urlFinal, request, ServiceInvoiceResponseDTO.class);
    }

}
