package com.italo.catalogy.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.italo.catalogy.dto.invoice_xml.InvoiceXmlDTO;
import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.SupplierItemWithCprodResponseDTO;
import com.italo.catalogy.mapper.StockOrderMapper;
import com.italo.catalogy.mapper.SupplierItemMapper;
import com.italo.catalogy.model.*;
import com.italo.catalogy.model.enums.StockOrderStatus;
import com.italo.catalogy.respository.SellerRepository;
import com.italo.catalogy.respository.StockOrderRepository;
import com.italo.catalogy.respository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class StockOrderService {
    private final StockOrderItemService stockOrderItemService;
    private final SellerRepository sellerRepository;
    private final StockOrderMapper stockOrderMapper;
    private final SupplierRepository supplierRepository;
    private final StockOrderRepository stockOrderRepository;
    private final XmlService xmlService;
    private final SupplierItemMapper supplierItemMapper;

    private final String EXTENSION_ACCEPTED = ".xml";

    public StockOrderService(StockOrderItemService stockOrderItemService, SellerRepository sellerRepository, StockOrderMapper stockOrderMapper, SupplierRepository supplierRepository, StockOrderRepository stockOrderRepository, XmlService xmlService, SupplierItemMapper supplierItemMapper) {
        this.stockOrderItemService = stockOrderItemService;
        this.sellerRepository = sellerRepository;
        this.stockOrderMapper = stockOrderMapper;
        this.supplierRepository = supplierRepository;
        this.stockOrderRepository = stockOrderRepository;
        this.xmlService = xmlService;
        this.supplierItemMapper = supplierItemMapper;
    }


    public StockOrderModel createStockOrder(CreateStockOrderRequestDTO createStockOrderRequestDTO, UserModel userModel){
        SellerModel sellerModel = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        //Busco pelo id and seller id | Confirmo que o supplier seja realmente do seller
        SupplierModel supplierModel = this.supplierRepository.findByIdAndSellerId(createStockOrderRequestDTO.supplierId(), sellerModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));


        StockOrderModel stockOrderModel = this.stockOrderMapper.createToModel(sellerModel, supplierModel);
        //Criar stockOrderItems
        List<StockOrderItemModel> stockOrderItems = this.stockOrderItemService.createStockOrderItems(createStockOrderRequestDTO, stockOrderModel);

        int itemsKnow = 0;
        int itemsAmount = 0;
        BigDecimal priceStimated = BigDecimal.ZERO;
        for (StockOrderItemModel  stockOrderItemModel: stockOrderItems){
            itemsAmount += stockOrderItemModel.getAmount();
            if (stockOrderItemModel.getSupplierItem().getSupplierItemCode()!=null){
                itemsKnow++;
                priceStimated = priceStimated.add(stockOrderItemModel.getSupplierItem().getLastPrice());
            }
        }

        stockOrderModel.setItems(stockOrderItems);
        stockOrderModel.setItemsKnow(itemsKnow);
        stockOrderModel.setPriceEstimated(priceStimated);
        stockOrderModel.setItemsAmount(itemsAmount);



        return this.stockOrderRepository.save(stockOrderModel);

    }


    public SupplierItemWithCprodResponseDTO inputInvoiceXml(UserModel userModel, MultipartFile invoiceXml, UUID stockOrderId){

        String fileName = invoiceXml.getOriginalFilename();

        if (fileName == null || !fileName.toLowerCase().endsWith(this.EXTENSION_ACCEPTED))
            throw new RuntimeException("Arquivo inválido");

        XmlMapper mapper = new XmlMapper();
        InvoiceXmlDTO invoiceXmlDTO;
        try{
            invoiceXmlDTO = mapper.readValue(invoiceXml.getBytes(), InvoiceXmlDTO.class);
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }

        SellerModel sellerModel = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        StockOrderModel stockOrderModel = this.stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        //Exibir uma mensagem de alerta caso o invoice xml tenha uma quantidade de itens diferente da lista de stockOrder 

        if (stockOrderModel.getInvoice_xml_path()!=null)
            throw new RuntimeException("Deu ruin");

        if (!stockOrderModel.getSellerModel().getId().equals(sellerModel.getId()))
            throw new RuntimeException("Deur ruin");

        if (!stockOrderModel.getSupplierModel().getCnpj().equals(invoiceXmlDTO.NFe().infNFe().emit().cnpj()))
            throw new RuntimeException("Deu ruin");

        stockOrderModel.setInvoice_xml_path(this.saveXml(invoiceXml));
        stockOrderModel.setStatus(StockOrderStatus.CONCLUED);
        this.stockOrderRepository.save(stockOrderModel);

        System.out.println(invoiceXmlDTO);

        return this.supplierItemMapper.listToCprodAndSupplierItemWithCprodResponseDTO(invoiceXmlDTO.NFe().infNFe().det(), stockOrderModel);
    }

    private String saveXml(MultipartFile invoiceXml){
        UUID objectId = UUID.randomUUID();
        String extension = "xml";
        String path = "catalog/" + "stock-order-invoice-xml" + "/" + objectId + "." + extension;
        this.xmlService.uploadXml(invoiceXml, path);
        return path;
    }
}
