export interface CouponResponseDTO {
    id: string; // UUID
    slug: string;
    amount: number;
    amountMinimum: number;
    amountDiscountMaximum: number;
    active:boolean
}