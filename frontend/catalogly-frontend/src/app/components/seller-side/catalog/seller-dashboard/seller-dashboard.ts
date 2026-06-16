import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';
import { HelperService } from '../../../../service/helper/helper-service';
import { DecimalPipe } from '@angular/common';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-seller-dashboard',
  imports: [DecimalPipe, RouterLink],
  templateUrl: './seller-dashboard.html',
  styleUrl: './seller-dashboard.css',
})
export class SellerDashboard {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);
  sellerHasCatalog = signal(false);
  private helperService = inject(HelperService);

  ngOnInit(){
    this.sellerService.catalogPrivateData$.subscribe(data =>{
      if(data){
        console.log(data);
        (data);
        this.catalogPrivateOfSeller.set(data);
        this.sellerHasCatalog.set(true);
      }
    })

    this.helperService.setAtualPage(0);

  }
}
