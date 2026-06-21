import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowItemsOfCategory } from './seller-show-items-of-category';

describe('SellerShowItemsOfCategory', () => {
  let component: SellerShowItemsOfCategory;
  let fixture: ComponentFixture<SellerShowItemsOfCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowItemsOfCategory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowItemsOfCategory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
