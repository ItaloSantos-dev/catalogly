import { ItemResponseDTO } from "../item/item-response";

export interface OrderItemResponseDTO{
    id:string;
    priceUnique:number;
    amount:number;
    priceFinal:number;
    item:ItemResponseDTO;
}