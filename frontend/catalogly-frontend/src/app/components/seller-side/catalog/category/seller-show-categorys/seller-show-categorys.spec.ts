import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerShowCategorys } from './seller-show-categorys';

describe('SellerShowCategorys', () => {
  let component: SellerShowCategorys;
  let fixture: ComponentFixture<SellerShowCategorys>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerShowCategorys]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerShowCategorys);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
