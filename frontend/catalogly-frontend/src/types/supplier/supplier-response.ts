import { ContactSupplierType } from "../enums/contact-supplier-type";
import { SupplierItemResponseDTO } from "../supplier-item/supplier-item-response";

export interface SupplierResponseDTO{
    name:string;
    cnpj:string,
    contactSupplierType:ContactSupplierType;
    contactValue:string;
    active:boolean;
    supplierItems:SupplierItemResponseDTO[];
}