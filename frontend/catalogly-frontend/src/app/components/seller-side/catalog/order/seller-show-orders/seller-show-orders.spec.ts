import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowOrders } from './seller-show-orders';

describe('SellerShowOrders', () => {
  let component: SellerShowOrders;
  let fixture: ComponentFixture<SellerShowOrders>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowOrders]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowOrders);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
