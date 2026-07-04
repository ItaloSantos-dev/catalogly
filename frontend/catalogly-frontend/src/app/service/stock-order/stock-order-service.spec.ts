import { TestBed } from '@angular/core/testing';

import { StockOrderService } from './stock-order-service';

describe('StockOrderService', () => {
  let service: StockOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StockOrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
