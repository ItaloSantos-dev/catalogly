export interface CreateCouponRequestDTO {
  slug: string;
  amount: string;
  amountMinimum: string | null;
  amountDiscountMaximum: string | null;
}