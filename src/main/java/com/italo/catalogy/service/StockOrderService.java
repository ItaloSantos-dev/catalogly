package com.italo.catalogy.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.italo.catalogy.dto.invoice_xml.Det;
import com.italo.catalogy.dto.invoice_xml.InvoiceXmlDTO;
import com.italo.catalogy.dto.stock_order.CreateStockOrderRequestDTO;
import com.italo.catalogy.dto.tie_supplier_item.supplier_item_cprod.SupplierItemWithCprodResponseDTO;
import com.italo.catalogy.dto.tie_supplier_item.update_cprod.TieCprodInvoiceWithSupplierItemId;
import com.italo.catalogy.dto.tie_supplier_item.update_cprod.UpdateCprodOfSupplierItemsRequestDTO;
import com.italo.catalogy.mapper.StockOrderInvoiceMapper;
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
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StockOrderService {
    private final StockOrderItemService stockOrderItemService;
    private final SellerRepository sellerRepository;
    private final StockOrderMapper stockOrderMapper;
    private final SupplierRepository supplierRepository;
    private final StockOrderRepository stockOrderRepository;
    private final XmlService xmlService;
    private final SupplierItemMapper supplierItemMapper;
    private final StockOrderInvoiceMapper stockOrderInvoiceMapper;
    private final String EXTENSION_ACCEPTED = ".xml";
    private final XmlMapper xmlMapper = new XmlMapper();

    public StockOrderService(StockOrderItemService stockOrderItemService, SellerRepository sellerRepository, StockOrderMapper stockOrderMapper, SupplierRepository supplierRepository, StockOrderRepository stockOrderRepository, XmlService xmlService, SupplierItemMapper supplierItemMapper, StockOrderInvoiceMapper stockOrderInvoiceMapper) {
        this.stockOrderItemService = stockOrderItemService;
        this.sellerRepository = sellerRepository;
        this.stockOrderMapper = stockOrderMapper;
        this.supplierRepository = supplierRepository;
        this.stockOrderRepository = stockOrderRepository;
        this.xmlService = xmlService;
        this.supplierItemMapper = supplierItemMapper;
        this.stockOrderInvoiceMapper = stockOrderInvoiceMapper;
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

    public SupplierItemWithCprodResponseDTO getCprodAndItemsIdOfStockOrderById(UUID id, UserModel userModel){
        StockOrderModel stockOrder = this.stockOrderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if(!stockOrder.getSellerModel().getUser().getId().equals(userModel.getId()))
            throw new RuntimeException("Deu ruin");

        if(stockOrder.getStockOrderInvoiceModel()==null)
            throw new RuntimeException("Deu ruin");

        InputStream invoiceXmlStream = this.xmlService.getInvoiceXml(stockOrder.getStockOrderInvoiceModel().getInvoice_xml_path());

        InvoiceXmlDTO invoiceXmlDTO;
        try{
            invoiceXmlDTO = xmlMapper.readValue(invoiceXmlStream, InvoiceXmlDTO.class);
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }

        return this.supplierItemMapper.listToCprodAndSupplierItemWithCprodResponseDTO(invoiceXmlDTO.NFe().infNFe().det(), stockOrder);

        
    }

    public SupplierItemWithCprodResponseDTO inputInvoiceXml(UserModel userModel, MultipartFile invoiceXml, UUID stockOrderId){

        String fileName = invoiceXml.getOriginalFilename();

        if (fileName == null || !fileName.toLowerCase().endsWith(this.EXTENSION_ACCEPTED))
            throw new RuntimeException("Arquivo inválido");

        
        InvoiceXmlDTO invoiceXmlDTO;
        try{
            invoiceXmlDTO = xmlMapper.readValue(invoiceXml.getBytes(), InvoiceXmlDTO.class);
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }

        SellerModel sellerModel = this.sellerRepository.findByUserId(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        StockOrderModel stockOrderModel = this.stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        //Exibir uma mensagem de alerta caso o invoice xml tenha uma quantidade de itens diferente da lista de stockOrder 

        if (stockOrderModel.getStockOrderInvoiceModel()!=null)
            throw new RuntimeException("Deu ruin");

        if (!stockOrderModel.getSellerModel().getId().equals(sellerModel.getId()))
            throw new RuntimeException("Deur ruin");

        if (!stockOrderModel.getSupplierModel().getCnpj().equals(invoiceXmlDTO.NFe().infNFe().emit().cnpj()))
            throw new RuntimeException("Deu ruin");



        //Criar StockOrderInvoice
        String xmlPath = this.saveXml(invoiceXml);
        stockOrderModel.setStockOrderInvoiceModel(this.stockOrderInvoiceMapper.dataToModel(stockOrderModel, invoiceXmlDTO, xmlPath));
        stockOrderModel.setStatus(StockOrderStatus.CONCLUED);
        this.stockOrderRepository.save(stockOrderModel);

        return this.supplierItemMapper.listToCprodAndSupplierItemWithCprodResponseDTO(invoiceXmlDTO.NFe().infNFe().det(), stockOrderModel);
    }

    private String saveXml(MultipartFile invoiceXml){
        UUID objectId = UUID.randomUUID();
        String extension = "xml";
        String path = "catalog/" + "stock-order-invoice-xml" + "/" + objectId + "." + extension;
        this.xmlService.uploadXml(invoiceXml, path);
        return path;
    }

    public StockOrderModel updateCprodOfSupplierItems(UUID stockOrderId, UserModel userModel, UpdateCprodOfSupplierItemsRequestDTO updateCprodOfSupplierItemsRequestDTO){

        StockOrderModel stockOrderModel = this.stockOrderRepository.findByIdAndSellerModelUserId(stockOrderId, userModel.getId())
                .orElseThrow(() -> new RuntimeException("Deu ruin"));

        if (stockOrderModel.getStockOrderInvoiceModel()==null)
            throw new RuntimeException("Deu ruin");

        Map<UUID, String > mapItemIdAndCprod = new HashMap<>();
        Map<String, BigDecimal> mapCprodAndLastPrice = new HashMap<>();

        List<String> cprodsOfInvoice = new ArrayList<>();

        for (Det det : stockOrderModel.getStockOrderInvoiceModel().getInvoiceXmlDTO().NFe().infNFe().det()){
            cprodsOfInvoice.add(det.prod().cProd());
            mapCprodAndLastPrice.put(det.prod().cProd(), det.prod().vUnCom());
        }

        List<UUID> stockOrderItemsId = stockOrderModel.getItems().stream()
                .map(stockOrderItemModel -> stockOrderItemModel.getId())
                .toList();

        for(TieCprodInvoiceWithSupplierItemId idAndCprod : updateCprodOfSupplierItemsRequestDTO.tieItems()){
            if (!cprodsOfInvoice.contains(idAndCprod.cProd()))
                throw new RuntimeException("Deu ruin");

            if (!stockOrderItemsId.contains(idAndCprod.stockOrderItemId()))
                throw new RuntimeException("Deu ruin");

            mapItemIdAndCprod.put(idAndCprod.stockOrderItemId(), idAndCprod.cProd());
        }

        System.out.println(mapCprodAndLastPrice);
        for (StockOrderItemModel stockOrderItemModel : stockOrderModel.getItems()){
            SupplierItemModel supplierItemModel = stockOrderItemModel.getSupplierItem();
            supplierItemModel.setSupplierItemCode(mapItemIdAndCprod.get(stockOrderItemModel.getId()));
            supplierItemModel.setLastPrice(mapCprodAndLastPrice.get(stockOrderItemModel.getSupplierItem().getSupplierItemCode()));
        }
        //Atualizar valores da stock order com os valores dos produtos 
        stockOrderModel.setUpdatedAt(LocalDateTime.now());

        return  this.stockOrderRepository.save(stockOrderModel);
    }

    public StockOrderModel getStockOrderById(UUID id, UserModel userModel){
        StockOrderModel stockOrderModel = this.stockOrderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());

        if (!stockOrderModel.getSellerModel().getUser().getId().equals(userModel.getId()))
            throw new RuntimeException("Deu ruin");

        return stockOrderModel;
    }       
}
