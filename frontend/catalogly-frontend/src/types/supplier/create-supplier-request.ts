import { ContactSupplierType } from "../enums/contact-supplier-type";

export interface CreateSupplierRequestDTO{
    name:string;
    cnpj:string,
    contactSupplierType:ContactSupplierType;
    contactValue:string;
    items:string[] | null
}