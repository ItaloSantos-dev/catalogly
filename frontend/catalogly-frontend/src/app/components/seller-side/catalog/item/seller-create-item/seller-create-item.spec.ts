import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateItem } from './seller-create-item';

describe('SellerCreateItem', () => {
  let component: SellerCreateItem;
  let fixture: ComponentFixture<SellerCreateItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateItem]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
