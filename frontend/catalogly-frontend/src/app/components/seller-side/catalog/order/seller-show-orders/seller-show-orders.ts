import { Component, computed, inject, signal } from '@angular/core';
import { HelperService } from '../../../../../service/helper/helper-service';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { OrderStatus } from '../../../../../../types/enums/order-status';
import { OrderResponseDTO } from '../../../../../../types/order/order-response';

@Component({
  selector: 'app-seller-show-orders',
  imports: [],
  templateUrl: './seller-show-orders.html',
  styleUrl: './seller-show-orders.css',
})
export class SellerShowOrders {
  private helperService = inject(HelperService);
  private catalogService = inject(CatalogService);
  orders = signal(<OrderResponseDTO[]>[]);

  // Exposição do Enum para validações diretas no HTML
  OrderStatus = OrderStatus;

  // KPI 1: Faturamento Líquido Total (Apenas pedidos aprovados/pagos/concluídos)
  totalRevenue = computed(() => {
    return this.orders()
      .filter(o => o.orderStatus === OrderStatus.PAID || 
                   o.orderStatus === OrderStatus.PROCESSING || 
                   o.orderStatus === OrderStatus.COMPLETED)
      .reduce((acc, order) => acc + order.priceFinal, 0);
  });

  // KPI 2: Pedidos que precisam de atenção imediata de logística/envio
  ordersToShipCount = computed(() => {
    return this.orders().filter(o => o.orderStatus === OrderStatus.PROCESSING).length;
  });

  // KPI 3: Pedidos aguardando a compensação do cliente
  pendingPaymentCount = computed(() => {
    return this.orders().filter(o => o.orderStatus === OrderStatus.PENDING_PAYMENT).length;
  });

  ORDERS_MOCK: OrderResponseDTO[] = [
  {
    id: "1",
    orderStatus: OrderStatus.CREATED,
    priceInitial: 99.90,
    priceFinal: 99.90
  },
  {
    id: "2",
    orderStatus: OrderStatus.PENDING_PAYMENT,
    couponId: "coupon-1",
    couponSlug: "BEMVINDO10",
    couponDiscount: 10,
    priceInitial: 200,
    priceFinal: 180,
    paymentUrl: "https://pagamentos.exemplo.com/checkout/2"
  },
  {
    id: "3",
    orderStatus: OrderStatus.PAID,
    priceInitial: 350,
    priceFinal: 350
  },
  {
    id: "4",
    orderStatus: OrderStatus.PROCESSING,
    priceInitial: 420,
    priceFinal: 420
  },
  {
    id: "5",
    orderStatus: OrderStatus.COMPLETED,
    priceInitial: 89.90,
    priceFinal: 89.90
  },
  {
    id: "6",
    orderStatus: OrderStatus.CANCELED,
    priceInitial: 159.90,
    priceFinal: 159.90
  }
];

  ngOnInit(){
    this.helperService.setAtualPage(1);
    this.catalogService.getOrdersOfCatalog().subscribe({
      next:(data) =>{
        console.log(data);
        
        this.orders.set(data);
      }
    })
  }
}
