import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowSupplierStockOrders } from './seller-show-supplier-stock-orders';

describe('SellerShowSupplierStockOrders', () => {
  let component: SellerShowSupplierStockOrders;
  let fixture: ComponentFixture<SellerShowSupplierStockOrders>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowSupplierStockOrders]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowSupplierStockOrders);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
