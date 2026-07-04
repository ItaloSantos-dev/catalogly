import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateStockOrder } from './seller-create-stock-order';

describe('SellerCreateStockOrder', () => {
  let component: SellerCreateStockOrder;
  let fixture: ComponentFixture<SellerCreateStockOrder>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateStockOrder]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateStockOrder);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
