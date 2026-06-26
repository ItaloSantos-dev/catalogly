import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowItem } from './seller-show-item';

describe('SellerShowItem', () => {
  let component: SellerShowItem;
  let fixture: ComponentFixture<SellerShowItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowItem]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
