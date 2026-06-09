import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowItems } from './seller-show-items';

describe('SellerShowItems', () => {
  let component: SellerShowItems;
  let fixture: ComponentFixture<SellerShowItems>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowItems]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowItems);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
