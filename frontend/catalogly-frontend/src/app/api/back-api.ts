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
import { UpdateItemRequestDTO } from "../../types/item/update-item-request";
import { UpdateCategoryrequestDTO } from "../../types/category/update-category-request";
import { SupplierItemResponseDTO } from "../../types/supplier-item/supplier-item-response";
import { StockOrderResponseDTO } from "../../types/stock-order/stock-order-response";
import { CreateStockOrderRequestDTO } from "../../types/stock-order/create-stock-order-request";

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

    updateItemById(id:string, data:FormData):Observable<ItemResponseDTO>{
        return this.httpClient.put<ItemResponseDTO>(this.baseUrl + "item/" + id, data);
    }

    deleteItemById(id:string){
        console.log("Fazendo essa desgraça");
        return this.httpClient.delete(this.baseUrl + "item/" + id);
    }

    getCategoryByid(id:string):Observable<CategoryRespondeDTO>{
        return this.httpClient.get<CategoryRespondeDTO>(this.baseUrl + "category/" + id);
    }

    updateCategoryById(id:string, data:UpdateCategoryrequestDTO):Observable<any>{
        return this.httpClient.put<any>(this.baseUrl + "category/" + id, data);
    }

    deleteCouponById(id:string):Observable<any>{
        return this.httpClient.delete<any>(this.baseUrl + "coupon/" + id);
    }

    updateActiveSupplierById(id:string):Observable<void>{
        return this.httpClient.delete<void>(this.baseUrl + "supplier/" + id);
    }

    getItemOfSupplierById(id:string):Observable<SupplierItemResponseDTO[]>{
       return this.httpClient.get<SupplierItemResponseDTO[]>(this.baseUrl + "supplier/" + id + "/items"); 
    }

    getStockOrdersOfSupplierById(id:string):Observable<StockOrderResponseDTO[]>{
        return this.httpClient.get<StockOrderResponseDTO[]>(this.baseUrl + "supplier/" + id + "/stock-orders");
    }

    createStockOrder(data:CreateStockOrderRequestDTO):Observable<any>{
        return this.httpClient.post<any>(this.baseUrl + "stock-order", data);
    }

    getstockORderById(id:string):Observable<StockOrderResponseDTO>{
        return this.httpClient.get<StockOrderResponseDTO>(this.baseUrl + "stock-order/" + id);
    }

    updateInvoiceXmlOfStockOrderById(data:FormData):Observable<any>{
        return this.httpClient.post<any>(this.baseUrl + "stock-order/input-invoice-xml", data);
    }
}