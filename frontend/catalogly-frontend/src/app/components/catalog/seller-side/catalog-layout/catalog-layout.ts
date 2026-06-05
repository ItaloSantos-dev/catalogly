import { Component, inject, signal } from '@angular/core';
import { CatalogSideMenu } from '../catalog-side-menu/catalog-side-menu';
import { RouterOutlet } from '@angular/router';
import { SellerService } from '../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../types/catalog/catalog-private-response';

@Component({
  selector: 'app-catalog-layout',
  imports: [CatalogSideMenu, RouterOutlet],
  templateUrl: './catalog-layout.html',
  styleUrl: './catalog-layout.css',
})
export class CatalogLayout {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO | null>null);

  ngOnInit(){
    this.sellerService.getMyCatalog().subscribe({
      next: (data) => {
        this.catalogPrivateOfSeller.set(data);
        this.sellerService.setCatalogPrivateData(data);
      },
      error: (err) => {
        console.error('Error fetching catalog private data:', err);
      }
    });
  }

}
