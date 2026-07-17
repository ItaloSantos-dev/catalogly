import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserShowOrders } from './user-show-orders';

describe('UserShowOrders', () => {
  let component: UserShowOrders;
  let fixture: ComponentFixture<UserShowOrders>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserShowOrders]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserShowOrders);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
