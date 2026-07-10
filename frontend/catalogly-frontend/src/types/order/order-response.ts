import { OrderStatus } from "../enums/order-status";

export interface OrderResponseDTO{
    id:string;
    orderStatus:OrderStatus;
    couponId?:string;
    couponSlug?:string;
    couponDiscount?:number;
    priceInitial:number;
    priceFinal:number;
    paymentUrl?:string;
}