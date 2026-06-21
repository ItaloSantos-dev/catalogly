import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateCategory } from './seller-create-category';

describe('SellerCreateCategory', () => {
  let component: SellerCreateCategory;
  let fixture: ComponentFixture<SellerCreateCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateCategory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateCategory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
