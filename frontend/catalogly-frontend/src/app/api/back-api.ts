import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { CreateSellerRequestDTO } from "../../types/seller/create-seller-request";
import { Observable } from "rxjs";
import { SellerResponseDTO } from "../../types/seller/seller-response";
import { CatalogPrivateResponseDTO } from "../../types/catalog/catalog-private-response";

@Injectable({
    providedIn: 'root'
})

export class BackApi {
    private baseUrl = 'http://localhost:8080/';

    private httpClient = inject(HttpClient);

    registerSeller(data:CreateSellerRequestDTO):Observable<SellerResponseDTO>{
        return this.httpClient.post<SellerResponseDTO>(this.baseUrl + "seller", data);
    }

    getMyCatalog():Observable<CatalogPrivateResponseDTO>{
        return this.httpClient.get<CatalogPrivateResponseDTO>(this.baseUrl + "seller/catalog");
    }
}