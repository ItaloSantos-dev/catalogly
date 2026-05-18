package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.nfe_io.ActivityEvent;
import com.italo.catalogy.dto.nfe_io.Address;
import com.italo.catalogy.dto.nfe_io.City;
import com.italo.catalogy.dto.nfe_io.Location;
import com.italo.catalogy.dto.nfe_io.createServiceInvoice.ApproximateTaxCreate;
import com.italo.catalogy.dto.nfe_io.createServiceInvoice.BorrowerCreate;
import com.italo.catalogy.dto.nfe_io.createServiceInvoice.CreateServiceInvoiceRequestDTO;
import com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse.ServiceInvoiceResponseDTO;
import com.italo.catalogy.infra.NfeIoConfig;
import com.italo.catalogy.model.InvoiceModel;
import com.italo.catalogy.model.PaymentModel;
import com.italo.catalogy.model.SellerModel;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.InvoiceStatus;
import com.italo.catalogy.model.enums.SefazEnvironment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InvoiceMapper {

    private final NfeIoConfig nfeIoConfig;

    public InvoiceMapper(NfeIoConfig nfeIoConfig) {
        this.nfeIoConfig = nfeIoConfig;
    }

    //Nota de serviço catalogly <-> seller
    public CreateServiceInvoiceRequestDTO dataToCreateServiceInvoice(PaymentModel paymentModel, UUID externalId, Integer rpsNumber){
        SellerModel seller = paymentModel.getOrder().getSeller();

        City city = new City("2304400", "Fortaleza");

        Address address = new Address("BRA", "60000000", "Rua mesmo", "100", "Apto 101", "Centro", city ,"CE");



        BorrowerCreate borrowerCreate = new BorrowerCreate(
                "NaturalPerson",
                seller.getUser().getFirstName() + " " + seller.getUser().getLastName(),
                seller.getCpf(),
                "",
                "Isento",
                //Depois pedir numero no registro
                "99981587631",
                seller.getUser().getEmail(),
                //Depois fazer mapeamento de endereço
                address
        );

        ApproximateTaxCreate approximateTaxCreate = new ApproximateTaxCreate(
                "IBPT",
                "25.1A",
                BigDecimal.ZERO
        );

        Location location = new Location(
                "CE",
                "BRA",
                "60025-061",
                "Rua Barão do Rio Branco",
                "2050",
                "José Bonifácio",
                "",
                city
        );

        ActivityEvent activityEvent = new ActivityEvent(
                "Prestação de serviço de software",
                Instant.now(),
                Instant.now(),
                "001"
        );

        return new CreateServiceInvoiceRequestDTO(
                externalId.toString(),
                borrowerCreate,
                "0107",
                "1.07",
                "6201501",
                "",
                "Licenciamento de programas de computador customizáveis",
                paymentModel.getAmountTotal(),
                "TESTE",
                Instant.now(),
                rpsNumber,
                "None",
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                approximateTaxCreate,
                "Nota fiscal de teste",
                location,
                activityEvent
        );
    }

    public InvoiceModel responseServiceInvoiceToInvoiceModel(ServiceInvoiceResponseDTO serviceInvoiceResponseDTO, PaymentModel paymentModel, UUID invoiceId, CreateServiceInvoiceRequestDTO createServiceInvoiceRequestDTO){
        UserModel customer = paymentModel.getOrder().getBuyer();

        BigDecimal amountTotal = paymentModel.getAmountCommission().subtract(serviceInvoiceResponseDTO.approximateTax().totalAmount());

        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setId(invoiceId);
        invoiceModel.setExternalId(serviceInvoiceResponseDTO.id());
        invoiceModel.setCustomer(customer);
        invoiceModel.setOrder(paymentModel.getOrder());
        invoiceModel.setPayment(paymentModel);
        invoiceModel.setInvoiceNumber(serviceInvoiceResponseDTO.number().toString());
        invoiceModel.setSeries(serviceInvoiceResponseDTO.rpsSerialNumber());
        //Ajustar e separar NFS e NF
        invoiceModel.setKey(UUID.randomUUID().toString());
        invoiceModel.setModel("NFE/NF");
        invoiceModel.setRpsNumber(serviceInvoiceResponseDTO.rpsNumber());
        invoiceModel.setStatus(InvoiceStatus.DRAFT);
        invoiceModel.setSefazEnvironment(SefazEnvironment.DEVELOPMENT);
        invoiceModel.setInssuerCnpj(nfeIoConfig.getInssuerCnpj());
        invoiceModel.setInssuerName(nfeIoConfig.getInssuerTradeName());
        invoiceModel.setInssuerStateRegistration(nfeIoConfig.getInssuerStateRegistration());
        invoiceModel.setInssuerTradeName(nfeIoConfig.getInssuerTradeName());
        invoiceModel.setCustomerCpfCnpj(customer.getCpf());
        invoiceModel.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
        invoiceModel.setCustomerEmail(customer.getEmail());
        invoiceModel.setBillingAddress("Endereço da cobrança");
        invoiceModel.setShippingAddress("Endereço da entrega");
        invoiceModel.setSubtotalAmount(paymentModel.getAmountCommission());
        invoiceModel.setDiscountAmount(BigDecimal.ZERO);
        invoiceModel.setShippingAmount(BigDecimal.ZERO);
        invoiceModel.setTaxAmount(serviceInvoiceResponseDTO.approximateTax().totalAmount());
        invoiceModel.setTotalAmount(amountTotal);
        invoiceModel.setXmlSend(createServiceInvoiceRequestDTO.toString());
        invoiceModel.setXmlAuthorized(serviceInvoiceResponseDTO.toString());
        invoiceModel.setSefazAuthorizationDate(LocalDateTime.now());
        invoiceModel.setSefazStatusCode(
                serviceInvoiceResponseDTO.status()==null?
                        InvoiceStatus.DRAFT:
                        serviceInvoiceResponseDTO.status()

        );
        invoiceModel.setCreatedAt(LocalDateTime.now());
        invoiceModel.setUpdatedAt(LocalDateTime.now());

        return invoiceModel;


    }
}
