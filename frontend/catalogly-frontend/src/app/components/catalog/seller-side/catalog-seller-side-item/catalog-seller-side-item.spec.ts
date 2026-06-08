import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogSellerSideItem } from './catalog-seller-side-item';

describe('CatalogSellerSideItem', () => {
  let component: CatalogSellerSideItem;
  let fixture: ComponentFixture<CatalogSellerSideItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogSellerSideItem]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogSellerSideItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
