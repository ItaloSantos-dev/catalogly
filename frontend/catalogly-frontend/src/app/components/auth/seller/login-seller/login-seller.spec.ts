import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginSeller } from './login-seller';

describe('LoginSeller', () => {
  let component: LoginSeller;
  let fixture: ComponentFixture<LoginSeller>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginSeller]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginSeller);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
