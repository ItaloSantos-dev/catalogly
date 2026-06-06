import { SellerResponseDTO } from "../seller/seller-response";
import { CatalogDashboard } from "./dashboard/catalog-dashboard";

export interface CatalogPrivateResponseDTO {
    name:string;
    slug: string;
    slogan: string;
    about: string;
    fisicAddress: string;
    phone: string;
    imageIconUrl: string;
    imageBannerUrl: string;
    sellerData:SellerResponseDTO;
    dashboard:CatalogDashboard;
}