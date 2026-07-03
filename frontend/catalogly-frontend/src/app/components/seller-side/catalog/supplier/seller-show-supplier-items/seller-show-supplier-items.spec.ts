import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowSupplierItems } from './seller-show-supplier-items';

describe('SellerShowSupplierItems', () => {
  let component: SellerShowSupplierItems;
  let fixture: ComponentFixture<SellerShowSupplierItems>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowSupplierItems]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowSupplierItems);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
