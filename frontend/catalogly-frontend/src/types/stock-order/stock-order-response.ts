import { StockOrderStatus } from "../enums/stock-order-status";
import { StockOrderItemResponseDTO } from "../stock-order-item/stock-order-item-response";

export interface StockOrderResponseDTO {
  id:string;
  sellerId: string;
  sellerName: string;
  supplierId: string;
  supplierName: string;
  itemsAmount: number;
  status: StockOrderStatus;
  priceFinal ?: number;
  priceEstimated ?: number;
  invoiceXmlurl ?: string;
  supplierItems ?: StockOrderItemResponseDTO[];
}