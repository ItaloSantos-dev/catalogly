import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateCoupon } from './seller-create-coupon';

describe('SellerCreateCoupon', () => {
  let component: SellerCreateCoupon;
  let fixture: ComponentFixture<SellerCreateCoupon>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateCoupon]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateCoupon);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
