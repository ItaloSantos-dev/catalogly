import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkItensAndCategory } from './link-itens-and-category';

describe('LinkItensAndCategory', () => {
  let component: LinkItensAndCategory;
  let fixture: ComponentFixture<LinkItensAndCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LinkItensAndCategory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LinkItensAndCategory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
