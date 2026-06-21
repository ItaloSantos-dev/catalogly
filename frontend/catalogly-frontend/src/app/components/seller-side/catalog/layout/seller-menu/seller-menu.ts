import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { HelperService } from '../../../../../service/helper/helper-service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-seller-menu',
  imports: [RouterLink, CommonModule],
  templateUrl: './seller-menu.html',
  styleUrl: './seller-menu.css',
})
export class SellerMenu {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);
  sellerHasCatalog = signal(true);
  private helperService = inject(HelperService);

  currentPage = signal(0);

  ngOnInit(){
    this.sellerService.catalogPrivateData$.subscribe(data =>{
      if(data){
        this.catalogPrivateOfSeller.set(data);
        this.sellerHasCatalog.set(true);
      }
    })
    this.helperService.atualPage$.subscribe(page =>{
      this.currentPage.set(page)
    })   
  }
}
