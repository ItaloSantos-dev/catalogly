import { OrderStatus } from "../enums/order-status";
import { OrderItemResponseDTO } from "../order-item/order-item-response";

export interface OrderResponseDTO{
    id:string;
    catalogName:string;
    orderStatus:OrderStatus;
    couponId?:string;
    couponSlug?:string;
    couponDiscount?:number;
    priceInitial:number;
    priceFinal:number;
    paymentUrl?:string;
    items:OrderItemResponseDTO[];
}