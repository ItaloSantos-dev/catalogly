import { Component, computed, inject, signal } from '@angular/core';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerService } from '../../../../../service/seller/seller-service';
import { CouponResponseDTO } from '../../../../../../types/coupon/coupon-response';
import { HelperService } from '../../../../../service/helper/helper-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-seller-show-coupons',
  imports: [RouterLink],
  templateUrl: './seller-show-coupons.html',
  styleUrl: './seller-show-coupons.css',
})
export class SellerShowCoupons {
  private catalogService = inject(CatalogService)
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{})
  couponsOfCatalog = signal(<CouponResponseDTO[]>[]);
  private sellerService = inject(SellerService);
  private helperService = inject(HelperService);
  showInativeCoupons = signal(false);


  private couponsMock: CouponResponseDTO[] = [
  {
  id: '1fbf361c-05b2-45a4-b99a-1e94e7a09bd7',
  slug: 'NATAL-10',
  amount: 0.2,
  amountMinimum: 0,
  amountDiscountMaximum: 0,
  active: true
},
    {
    id: '550e8400-e29b-41d4-a716-446655440000',
    slug: 'BEMVINDO10',
    amount: 10,
    amountMinimum: 50,
    amountDiscountMaximum: 20,
    active:false
  },
  {
    id: '6ba7b810-9dad-11d1-80b4-00c04fd430c8',
    slug: 'FRETEGRATIS',
    amount: 15,
    amountMinimum: 100,
    amountDiscountMaximum: 30,
    active:true
  },
  {
    id: '123e4567-e89b-12d3-a456-426614174000',
    slug: 'BLACKFRIDAY',
    amount: 25,
    amountMinimum: 200,
    amountDiscountMaximum: 100,
    active:true
  },
  {
    id: '987fcdeb-51a2-43d7-b654-123456789abc',
    slug: 'NATAL2026',
    amount: 20,
    amountMinimum: 150,
    amountDiscountMaximum: 50,
    active:true
  },
  {
    id: 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
    slug: 'CLIENTEVIP',
    amount: 30,
    amountMinimum: 300,
    amountDiscountMaximum: 120,
    active:true
  }
];


  // Quantidade total de cupons ativos na lista
  totalCouponsCount = computed(() => this.couponsOfCatalog().length);

  totalCouponsActivated = computed(() => this.couponsOfCatalog().filter(c => c.active).length);

  // Média do valor dos descontos oferecidos pelos cupons ativos
  averageDiscountValue = computed(() => {
    const activeCoupons = this.couponsOfCatalog().filter(c => c.active);

    if (activeCoupons.length === 0) return 0;

    return activeCoupons.reduce((acc, c) => acc + c.amount, 0) / activeCoupons.length;
  });


  ngOnInit(){
    this.sellerService.getMyCatalog().subscribe((data) =>{
      if (data) {
        this.catalogPrivateOfSeller.set(data);
        this.catalogService.getCouponsOfCatalogById(this.catalogPrivateOfSeller().id).subscribe({
          next: (coupons) =>{
            this.couponsOfCatalog.set(coupons)
            console.log(this.couponsOfCatalog());
          }
        })
      }
    })
    this.helperService.setAtualPage(3);
    
    
  }

}
