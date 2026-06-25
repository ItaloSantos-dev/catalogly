import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowSuppliers } from './seller-show-suppliers';

describe('SellerShowSuppliers', () => {
  let component: SellerShowSuppliers;
  let fixture: ComponentFixture<SellerShowSuppliers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowSuppliers]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowSuppliers);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
