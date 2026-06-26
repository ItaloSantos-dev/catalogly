import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkItensAndSupplier } from './link-itens-and-supplier';

describe('LinkItensAndSupplier', () => {
  let component: LinkItensAndSupplier;
  let fixture: ComponentFixture<LinkItensAndSupplier>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LinkItensAndSupplier]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LinkItensAndSupplier);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
