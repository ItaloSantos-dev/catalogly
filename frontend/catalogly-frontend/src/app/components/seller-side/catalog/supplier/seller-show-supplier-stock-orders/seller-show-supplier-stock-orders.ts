import { Component, computed, inject, signal } from '@angular/core';
import { StockOrderResponseDTO } from '../../../../../../types/stock-order/stock-order-response';
import { StockOrderStatus } from '../../../../../../types/enums/stock-order-status';
import { SupplierService } from '../../../../../service/supplier/supplier-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-seller-show-supplier-stock-orders',
  imports: [],
  templateUrl: './seller-show-supplier-stock-orders.html',
  styleUrl: './seller-show-supplier-stock-orders.css',
})
export class SellerShowSupplierStockOrders {
  stockOrders = signal(<StockOrderResponseDTO[]>[]);
  private supplierService = inject(SupplierService);

  constructor (private route:ActivatedRoute){}

  stockOrdersMock: StockOrderResponseDTO[] = [
  {
    sellerId: "d290f1ee-6c54-4b01-90e6-d701748f0851",
    sellerName: "Mercado Central",
    supplierId: "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
    supplierName: "Distribuidora Alpha",
    itemsAmount: 15,
    status: StockOrderStatus.CONCLUED,
    priceFinal: 1250.5,
    invoiceXmlurl: "https://example.com/invoices/invoice-001.xml",
    supplierItems: [
      {
        amount: 10,
        priceUnique: 50,
        priceFinal: 500,
      },
      {
        amount: 5,
        priceUnique: 150.1,
        priceFinal: 750.5,
      },
    ],
  },
  {
    sellerId: "123e4567-e89b-12d3-a456-426614174000",
    sellerName: "Super Loja",
    supplierId: "550e8400-e29b-41d4-a716-446655440000",
    supplierName: "Fornecedor Beta",
    itemsAmount: 8,
    status: StockOrderStatus.PENDING,
    priceFinal: 899.9,
    invoiceXmlurl: "",
    supplierItems: [
      {
        amount: 3,
        priceUnique: 99.9,
        priceFinal: 299.7,
      },
      {
        amount: 5,
        priceUnique: 120.04,
        priceFinal: 600.2,
      },
    ],
  },
  {
    sellerId: "7f4f1b60-c7b5-4c3b-b6d2-f1c9c6c3d1f1",
    sellerName: "Atacado Brasil",
    supplierId: "5e7d8c11-31a7-4bcb-95c0-92d2f7f1f1b3",
    supplierName: "Fornecedor Gama",
    itemsAmount: 20,
    status: StockOrderStatus.CANCELED,
    priceFinal: 0,
    invoiceXmlurl: "",
    supplierItems: [
      {
        amount: 20,
        priceUnique: 45,
        priceFinal: 900,
      },
    ],
  },
];

  ngOnInit(){
    const id  = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.supplierService.getStockOrdersOfSupplierById(id).subscribe({
        next:(data) =>{
          this.stockOrders.set(data)
        },
        error: (err) => {
          console.error(err);
        }
      })
    }

  }


  OrderStatus = StockOrderStatus;

  // Nome do fornecedor baseado no histórico
  supplierName = computed(() => {
    const list = this.stockOrders();
    return list.length > 0 ? list[0].supplierName : 'Fornecedor';
  });

  // KPI: Total investido/gasto em pedidos CONCLUÍDOS
  totalInvested = computed(() => {
    return this.stockOrders()
      .filter(o => o.status === StockOrderStatus.CONCLUED)
      .reduce((acc, order) => acc + order.priceFinal, 0);
  });

  // KPI: Quantidade de pedidos aguardando faturamento/entrega
  pendingCount = computed(() => {
    return this.stockOrders().filter(o => o.status === StockOrderStatus.PENDING).length;
  });
}
