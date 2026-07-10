import { Component, computed, inject, signal } from '@angular/core';
import { StockOrderResponseDTO } from '../../../../../../types/stock-order/stock-order-response';
import { StockOrderStatus } from '../../../../../../types/enums/stock-order-status';
import { SupplierService } from '../../../../../service/supplier/supplier-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-seller-show-supplier-stock-orders',
  imports: [RouterLink],
  templateUrl: './seller-show-supplier-stock-orders.html',
  styleUrl: './seller-show-supplier-stock-orders.css',
})
export class SellerShowSupplierStockOrders {
  stockOrders = signal(<StockOrderResponseDTO[]>[]);
  private supplierService = inject(SupplierService);
  private router = inject(Router)

  constructor (private route:ActivatedRoute){}
  
  stockOrdersMock: StockOrderResponseDTO[] = [
  {
    id: "01",
    sellerId: "d290f1ee-6c54-4b01-90e6-d701748f0851",
    sellerName: "Mercado Central",
    supplierId: "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
    supplierName: "Distribuidora Alpha",
    itemsAmount: 15,
    status: StockOrderStatus.PENDING,
    invoiceXmlurl: "https://example.com/invoices/invoice-001.xml",
    supplierItems: [
      {
        amount: 10,
        supplierItem: {
          id: "si-001",
          supplierName: "Distribuidora Alpha",
          supplierId: "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
          cProd: "1001",
          lastPrice: 50,
          item: {
            id: "item-001",
            categoryId: "cat-001",
            categoryName: "Bebidas",
            name: "Refrigerante 2L",
            about: "Refrigerante sabor cola",
            price: 52,
            stock: 100,
            deleted: false,
            imagePath1: "/assets/img/refrigerante.png",
            imagePath2: null,
            imagePath3: null,
          },
        },
      },
      {
        amount: 5,
        priceUnique: 150.1,
        priceFinal: 750.5,
        supplierItem: {
          id: "si-002",
          supplierName: "Distribuidora Alpha",
          supplierId: "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
          cProd: "1002",
          lastPrice: 150.1,
          item: {
            id: "item-002",
            categoryId: "cat-002",
            categoryName: "Limpeza",
            name: "Detergente",
            about: "Detergente líquido 500ml",
            price: 155,
            stock: 50,
            deleted: false,
            imagePath1: "/assets/img/detergente.png",
            imagePath2: null,
            imagePath3: null,
          },
        },
      },
    ],
  },
  {
    id: "02",
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
        supplierItem: {
          id: "si-003",
          supplierName: "Fornecedor Beta",
          supplierId: "550e8400-e29b-41d4-a716-446655440000",
          cProd: "2001",
          lastPrice: 99.9,
          item: {
            id: "item-003",
            categoryId: "cat-003",
            categoryName: "Alimentos",
            name: "Arroz 5kg",
            about: "Arroz tipo 1",
            price: 105,
            stock: 70,
            deleted: false,
            imagePath1: "/assets/img/arroz.png",
            imagePath2: null,
            imagePath3: null,
          },
        },
      },
      {
        amount: 5,
        priceUnique: 120.04,
        priceFinal: 600.2,
        supplierItem: {
          id: "si-004",
          supplierName: "Fornecedor Beta",
          supplierId: "550e8400-e29b-41d4-a716-446655440000",
          cProd: "2002",
          lastPrice: 120.04,
          item: {
            id: "item-004",
            categoryId: "cat-004",
            categoryName: "Higiene",
            name: "Sabonete",
            about: "Sabonete 90g",
            price: 125,
            stock: 200,
            deleted: false,
            imagePath1: "/assets/img/sabonete.png",
            imagePath2: null,
            imagePath3: null,
          },
        },
      },
    ],
  },
  {
    id: "03",
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
        supplierItem: {
          id: "si-005",
          supplierName: "Fornecedor Gama",
          supplierId: "5e7d8c11-31a7-4bcb-95c0-92d2f7f1f1b3",
          cProd: "3001",
          lastPrice: 45,
          item: {
            id: "item-005",
            categoryId: "cat-005",
            categoryName: "Bebidas",
            name: "Água Mineral",
            about: "Garrafa 1,5L",
            price: 47,
            stock: 300,
            deleted: false,
            imagePath1: "/assets/img/agua.png",
            imagePath2: null,
            imagePath3: null,
          },
        },
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

  directForCreateStockORder(id:string){
    this.route
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
    .filter(order => order.status === StockOrderStatus.CONCLUED)
    .reduce((acc, order) => acc + (order.priceFinal ?? 0), 0);
  });

  // KPI: Quantidade de pedidos aguardando faturamento/entrega
  pendingCount = computed(() => {
    return this.stockOrders().filter(o => o.status === StockOrderStatus.PENDING).length;
  });
}
