import { TieItemInvoice } from "./tie-item-invoice";
import { TieItemStockOrder } from "./tie-item-stock-order"

export interface SupplierItemWithCprodResponseDTO{
    stockOrderItems:TieItemStockOrder[];
    invoiceItems:TieItemInvoice[];
    xmlLink:string;
}