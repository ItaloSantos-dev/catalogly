import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogSideMenu } from './catalog-side-menu';

describe('CatalogSideMenu', () => {
  let component: CatalogSideMenu;
  let fixture: ComponentFixture<CatalogSideMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogSideMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogSideMenu);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
