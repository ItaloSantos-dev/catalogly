import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerLogin } from './seller-login';

describe('SellerLogin', () => {
  let component: SellerLogin;
  let fixture: ComponentFixture<SellerLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerLogin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellerLogin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
