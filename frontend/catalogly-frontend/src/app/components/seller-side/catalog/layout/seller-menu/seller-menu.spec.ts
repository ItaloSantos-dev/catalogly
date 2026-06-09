import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerMenu } from './seller-menu';

describe('SellerMenu', () => {
  let component: SellerMenu;
  let fixture: ComponentFixture<SellerMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerMenu);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
