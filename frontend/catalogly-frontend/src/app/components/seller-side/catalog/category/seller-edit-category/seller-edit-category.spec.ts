import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerEditCategory } from './seller-edit-category';

describe('SellerEditCategory', () => {
  let component: SellerEditCategory;
  let fixture: ComponentFixture<SellerEditCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerEditCategory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerEditCategory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
