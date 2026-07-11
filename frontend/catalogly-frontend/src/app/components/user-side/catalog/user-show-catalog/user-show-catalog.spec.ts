import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserShowCatalog } from './user-show-catalog';

describe('UserShowCatalog', () => {
  let component: UserShowCatalog;
  let fixture: ComponentFixture<UserShowCatalog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserShowCatalog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserShowCatalog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
