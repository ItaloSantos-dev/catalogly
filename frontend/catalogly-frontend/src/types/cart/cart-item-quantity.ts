import { ItemResponseDTO } from "../item/item-response";

export interface CartItemQuantity{
    item:ItemResponseDTO;
    quantity:number;
}