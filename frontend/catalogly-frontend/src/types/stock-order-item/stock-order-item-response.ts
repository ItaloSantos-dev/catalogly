import { SupplierItemResponseDTO } from "../supplier-item/supplier-item-response";

export interface StockOrderItemResponseDTO {
  supplierItem:SupplierItemResponseDTO;
  amount: number;
  priceUnique?: number;
  priceFinal?: number;
}