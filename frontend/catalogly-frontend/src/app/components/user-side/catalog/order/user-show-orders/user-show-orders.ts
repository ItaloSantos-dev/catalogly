import { Component, inject, signal } from '@angular/core';
import { OrderResponseDTO } from '../../../../../../types/order/order-response';
import { OrderStatus } from '../../../../../../types/enums/order-status';
import { OrderService } from '../../../../../service/order/order-service';

@Component({
  selector: 'app-user-show-orders',
  imports: [],
  templateUrl: './user-show-orders.html',
  styleUrl: './user-show-orders.css',
})
export class UserShowOrders {
  orders = signal(<OrderResponseDTO[]>[]);
  orderService = inject(OrderService);

  // Controla quais IDs de pedido estão abertos (Ver mais)
  expandedOrders = signal<Set<string>>(new Set());

  // Alterna a exibição dos detalhes de um pedido específico
  toggleOrder(orderId: string) {
    this.expandedOrders.update(set => {
      const next = new Set(set);
      if (next.has(orderId)) {
        next.delete(orderId);
      } else {
        next.add(orderId);
      }
      return next;
    });
  }

  // Soma a quantidade física de todos os itens comprados
  getTotalItemsCount(order: OrderResponseDTO): number {
    return order.items.reduce((acc, current) => acc + current.amount, 0);
  }
  ordersMock: OrderResponseDTO[] = [
  {
    id: "1",
    catalogName: "Vitta Suplementos",
    orderStatus: OrderStatus.PAID,
    couponId: "coupon-1",
    couponSlug: "NATAL10",
    couponDiscount: 10,
    priceInitial: 249.8,
    priceFinal: 224.82,
    paymentUrl: "https://pagamentos.com/pay/1",
    items: [
      {
        id: "order-item-1",
        priceUnique: 124.9,
        amount: 2,
        priceFinal: 249.8,
        item: {
          id: "item-1",
          categoryId: "cat-1",
          categoryName: "Proteínas",
          name: "Whey Protein 900g",
          about: "Proteína concentrada sabor chocolate.",
          price: 124.9,
          stock: 50,
          deleted: false,
          imagePath1: "https://picsum.photos/600/600?random=1",
          imagePath2: "https://picsum.photos/600/600?random=2",
          imagePath3: "https://picsum.photos/600/600?random=3",
        },
      },
    ],
  },
  {
    id: "2",
    catalogName: "Vitta Suplementos",
    orderStatus: OrderStatus.PENDING_PAYMENT,
    priceInitial: 169.8,
    priceFinal: 169.8,
    paymentUrl: "https://pagamentos.com/pay/2",
    items: [
      {
        id: "order-item-2",
        priceUnique: 84.9,
        amount: 2,
        priceFinal: 169.8,
        item: {
          id: "item-2",
          categoryId: "cat-2",
          categoryName: "Creatina",
          name: "Creatina 300g",
          about: "Creatina monohidratada.",
          price: 84.9,
          stock: 30,
          deleted: false,
          imagePath1: "https://picsum.photos/600/600?random=4",
          imagePath2: null,
          imagePath3: null,
        },
      },
    ],
  },
  {
    id: "3",
    catalogName: "Vitta Suplementos",
    orderStatus: OrderStatus.COMPLETED,
    priceInitial: 49.9,
    priceFinal: 49.9,
    items: [
      {
        id: "order-item-3",
        priceUnique: 49.9,
        amount: 1,
        priceFinal: 49.9,
        item: {
          id: "item-3",
          categoryId: "cat-3",
          categoryName: "Acessórios",
          name: "Coqueteleira 700ml",
          about: "Coqueteleira com compartimento.",
          price: 49.9,
          stock: 100,
          deleted: false,
          imagePath1: "https://picsum.photos/600/600?random=5",
          imagePath2: null,
          imagePath3: null,
        },
      },
    ],
  },
];

  // Retorna os textos e as cores do status do pedido no tema correto
  getStatusConfig(status: OrderStatus) {
    const configs: Record<OrderStatus, { text: string; class: string }> = {
      CREATED: {
        text: 'Criado',
        class: 'bg-slate-50 text-slate-700 border-slate-100'
      },
      PENDING_PAYMENT: {
        text: 'Aguardando Pagamento',
        class: 'bg-amber-50 text-amber-700 border-amber-100'
      },
      PAID: {
        text: 'Pago',
        class: 'bg-emerald-50 text-emerald-700 border-emerald-100'
      },
      PROCESSING: {
        text: 'Processando',
        class: 'bg-indigo-50 text-indigo-700 border-indigo-100'
      },
      COMPLETED: {
        text: 'Entregue',
        class: 'bg-blue-50 text-blue-700 border-blue-100'
      },
      CANCELED: {
        text: 'Cancelado',
        class: 'bg-red-50 text-red-700 border-red-100'
      }
    };

    return configs[status];
  }

  ngOnInit(){
    this.orderService.getOrdersOfUser().subscribe({
      next:(data) =>{
        console.log(data);
        
        this.orders.set(data);
      }
    })
  }
  
}
