import { CategoryNameAndId } from "../category/category-name-and-id";
import { SellerResponseDTO } from "../seller/seller-response";
import { CatalogDashboard } from "./dashboard/catalog-dashboard";

export interface CatalogPrivateResponseDTO {
    id:string;
    name:string;
    slug: string;
    slogan: string;
    about: string;
    fisicAddress: string;
    phone: string;
    imageIconUrl: string;
    imageBannerUrl: string;
    sellerData:SellerResponseDTO;
    catalogDashboard:CatalogDashboard;
    categoryNamesAndIds:CategoryNameAndId[];
}