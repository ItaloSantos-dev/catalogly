import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerTieItemWithCprod } from './seller-tie-item-with-cprod';

describe('SellerTieItemWithCprod', () => {
  let component: SellerTieItemWithCprod;
  let fixture: ComponentFixture<SellerTieItemWithCprod>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerTieItemWithCprod]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerTieItemWithCprod);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
