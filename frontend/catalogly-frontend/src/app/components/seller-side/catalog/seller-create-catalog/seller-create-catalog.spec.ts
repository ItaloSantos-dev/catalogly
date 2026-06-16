import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerCreateCatalog } from './seller-create-catalog';

describe('SellerCreateCatalog', () => {
  let component: SellerCreateCatalog;
  let fixture: ComponentFixture<SellerCreateCatalog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerCreateCatalog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerCreateCatalog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
