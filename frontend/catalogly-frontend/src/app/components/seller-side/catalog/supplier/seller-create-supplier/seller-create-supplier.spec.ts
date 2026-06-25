import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateSupplier } from './seller-create-supplier';

describe('SellerCreateSupplier', () => {
  let component: SellerCreateSupplier;
  let fixture: ComponentFixture<SellerCreateSupplier>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateSupplier]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateSupplier);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
