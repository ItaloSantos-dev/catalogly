import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowCoupons } from './seller-show-coupons';

describe('SellerShowCoupons', () => {
  let component: SellerShowCoupons;
  let fixture: ComponentFixture<SellerShowCoupons>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowCoupons]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowCoupons);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
