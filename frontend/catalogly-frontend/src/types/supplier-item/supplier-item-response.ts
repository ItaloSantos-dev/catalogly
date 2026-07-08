import { ItemResponseDTO } from "../item/item-response";

export interface SupplierItemResponseDTO{
    id:string;
    supplierName:string;
    supplierId:string;
    item:ItemResponseDTO;
    cProd?:string;
    lastPrice?:number
}