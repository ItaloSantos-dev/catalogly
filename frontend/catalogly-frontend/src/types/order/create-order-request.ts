import { ItemQuantity } from "./item-quantity";

export interface CreateOrderRequestDTO{
    catalogId:string;
    couponSlug?:string | null;
    items:ItemQuantity[]
}