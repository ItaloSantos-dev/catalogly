package com.italo.catalogy.service;

import com.italo.catalogy.dto.nfe_io.createServiceInvoice.CreateServiceInvoiceRequestDTO;
import com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse.ServiceInvoiceResponseDTO;
import com.italo.catalogy.infra.NfeIoConfig;
import com.italo.catalogy.mapper.InvoiceMapper;
import com.italo.catalogy.model.InvoiceModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.respository.InvoiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final NfeIoConfig nfeIoConfig;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, NfeIoConfig nfeIoConfig, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.nfeIoConfig = nfeIoConfig;
        this.invoiceMapper = invoiceMapper;
    }

    //FLUXO
    /*
        Envio request nfe.io
        depois crio a invoice
     */
    public InvoiceModel createInvoices(PaymentModel paymentModel){
        UUID invoiceModelId = UUID.randomUUID();

        Integer lastRps  = this.invoiceRepository.findLastRps();
        
        CreateServiceInvoiceRequestDTO createServiceInvoiceRequestDTO = this.invoiceMapper.dataToCreateServiceInvoice(paymentModel, invoiceModelId, lastRps+1);

        ResponseEntity<ServiceInvoiceResponseDTO> response = this.nfeIoConfig.inssueInvoiceService(createServiceInvoiceRequestDTO);

        if (response.getStatusCode()!= HttpStatus.OK &&
            response.getStatusCode()!=HttpStatus.ACCEPTED
        )
            throw new RuntimeException("Deu ruin: " + response.getStatusCode());

        InvoiceModel invoiceModel = this.invoiceMapper.responseServiceInvoiceToInvoiceModel(response.getBody(), paymentModel, invoiceModelId, createServiceInvoiceRequestDTO);

        return this.invoiceRepository.save(invoiceModel);
    }
}
