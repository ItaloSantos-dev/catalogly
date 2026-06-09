import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerMenu } from '../seller-menu/seller-menu';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-seller-layout',
  imports: [RouterOutlet, SellerMenu],
  templateUrl: './seller-layout.html',
  styleUrl: './seller-layout.css',
})
export class SellerLayout {
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
