import { ItemResponseDTO } from "../item/item-response";

export interface SupplierItemResponseDTO{
    id:string;
    supplierName:string;
    item:ItemResponseDTO;
    cprod:string;
    lastPrice:number
}