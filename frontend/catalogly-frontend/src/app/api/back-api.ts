import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { CreateSellerRequestDTO } from "../../types/seller/create-seller-request";
import { Observable } from "rxjs";
import { SellerResponseDTO } from "../../types/seller/seller-response";
import { CatalogPrivateResponseDTO } from "../../types/catalog/catalog-private-response";
import { LoginRequestDTO } from "../../types/auth/login-request";
import { ItemResponseDTO } from "../../types/item/item-response";
import { CreateCatalogRequestDTO } from "../../types/catalog/create-catalog-request";
import { CategoryRespondeDTO } from "../../types/category/category-response";
import { CreateCategoryRequestDTO } from "../../types/category/create-category-request";
import { CouponResponseDTO } from "../../types/coupon/coupon-response";
import { CreateCouponRequestDTO } from "../../types/coupon/create-coupon-request";
import { SupplierResponseDTO } from "../../types/supplier/supplier-response";
import { CreateSupplierRequestDTO } from "../../types/supplier/create-supplier-request";

@Injectable({
    providedIn: 'root'
})

export class BackApi {
    private baseUrl = 'http://localhost:8080/';

    private httpClient = inject(HttpClient);

    registerSeller(data:CreateSellerRequestDTO):Observable<SellerResponseDTO>{
        return this.httpClient.post<SellerResponseDTO>(this.baseUrl + "seller", data);
    }

    login(data:LoginRequestDTO):Observable<string>{
        return this.httpClient.post(this.baseUrl + "auth/login", data, {responseType:'text'});
    }

    getMyCatalog():Observable<CatalogPrivateResponseDTO>{
        return this.httpClient.get<CatalogPrivateResponseDTO>(this.baseUrl + "seller/catalog");
    }

    getItemsOfCatalog():Observable<ItemResponseDTO[]>{
        return this.httpClient.get<ItemResponseDTO[]>(this.baseUrl + "catalog/items");
    }

    createCatalog(data:FormData){
        return this.httpClient.post(this.baseUrl + "catalog", data);
    }

    createItem(data:FormData){
        return this.httpClient.post(this.baseUrl + "item", data);
    }

    getCategorysOfCatalogById(id:string):Observable<[CategoryRespondeDTO]>{
        return this.httpClient.get<[CategoryRespondeDTO]>(this.baseUrl+"catalog/"+id+"/categorys");
    }

    createCategory(data:CreateCategoryRequestDTO){
        return this.httpClient.post(this.baseUrl + "category", data);
    }

    deleteCategoryById(id:string):Observable<void>{
        return this.httpClient.delete<void>(this.baseUrl + "category/" + id);
    }

    getItensOfCategoryById(id:string):Observable<ItemResponseDTO[]>{
        return this.httpClient.get<ItemResponseDTO[]>(this.baseUrl + "category/"+id+"/items");
    }

    getCouponsOfCatalogById(id:string):Observable<CouponResponseDTO[]>{
        return this.httpClient.get<CouponResponseDTO[]>(this.baseUrl + "catalog/" + id + "/coupons")
    }

    createCoupon(data:CreateCouponRequestDTO):Observable<any>{
        return this.httpClient.post(this.baseUrl + "coupon", data);
    }

    getSuppliersOfCatalog():Observable<SupplierResponseDTO[]>{
        return this.httpClient.get<SupplierResponseDTO[]>(this.baseUrl + "catalog/suppliers");
    }

    createSupplier(data:CreateSupplierRequestDTO):Observable<any>{
        return this.httpClient.post<any>(this.baseUrl + "supplier", data)
    }

    getItemById(id:string):Observable<ItemResponseDTO>{
        return this.httpClient.get<ItemResponseDTO>(this.baseUrl + "item/" + id);
    }
}