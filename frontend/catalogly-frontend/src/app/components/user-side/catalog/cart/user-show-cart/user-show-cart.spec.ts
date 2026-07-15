import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserShowCart } from './user-show-cart';

describe('UserShowCart', () => {
  let component: UserShowCart;
  let fixture: ComponentFixture<UserShowCart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserShowCart]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserShowCart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
