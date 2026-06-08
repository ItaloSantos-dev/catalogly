import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';
import { RouterLink } from "@angular/router";
import { HelperService } from '../../../../service/helper/helper-service';
import { UserRole } from '../../../../../types/enums/user-role';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-catalog-side-menu',
  imports: [RouterLink, CommonModule],
  templateUrl: './catalog-side-menu.html',
  styleUrl: './catalog-side-menu.css',
})
export class CatalogSideMenu {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);
  sellerHasCatalog = signal(false);
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
