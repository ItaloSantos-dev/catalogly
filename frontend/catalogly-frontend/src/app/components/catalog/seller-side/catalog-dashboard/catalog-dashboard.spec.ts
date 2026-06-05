import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogDashboard } from './catalog-dashboard';

describe('CatalogDashboard', () => {
  let component: CatalogDashboard;
  let fixture: ComponentFixture<CatalogDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
