import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowStockOrder } from './seller-show-stock-order';

describe('SellerShowStockOrder', () => {
  let component: SellerShowStockOrder;
  let fixture: ComponentFixture<SellerShowStockOrder>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowStockOrder]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowStockOrder);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
