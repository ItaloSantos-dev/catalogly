package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.invoice_xml.InvoiceXmlDTO;
import com.italo.catalogy.model.StockOrderInvoiceModel;
import com.italo.catalogy.model.StockOrderItemModel;
import com.italo.catalogy.model.StockOrderModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StockOrderInvoiceMapper {
    public StockOrderInvoiceModel dataToModel(StockOrderModel stockOrderModel, InvoiceXmlDTO invoiceXmlDTO, String xmlPath){
        StockOrderInvoiceModel stockOrderInvoiceModel = new StockOrderInvoiceModel();
        stockOrderInvoiceModel.setStockOrderModel(stockOrderModel);
        stockOrderInvoiceModel.setInvoiceXmlDTO(invoiceXmlDTO);
        stockOrderInvoiceModel.setCreatedAt(LocalDateTime.now());
        stockOrderInvoiceModel.setUpdatedAt(LocalDateTime.now());
        stockOrderInvoiceModel.setInvoice_xml_path(xmlPath);
        return stockOrderInvoiceModel;
    }
}
