import { CategoryRespondeDTO } from "../category/category-response";
import { CouponResponseDTO } from "../coupon/coupon-response";
import { ItemResponseDTO } from "../item/item-response";

export interface CatalogPublicResponseDTO {
    id:string;
    sellerName: string;
    name: string;
    slug: string;
    slogan: string;
    about: string;
    fisicAddress: string;
    phone: string;
    imageIconUrl: string;
    imageBannerUrl: string;
    items: ItemResponseDTO[];
    categorys:CategoryRespondeDTO[];
    coupons:CouponResponseDTO[];
}