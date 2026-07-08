import { Component, computed, inject, signal } from '@angular/core';
import { SupplierItemResponseDTO } from '../../../../../../types/supplier-item/supplier-item-response';
import { ActivatedRoute, Router } from '@angular/router';
import { SupplierService } from '../../../../../service/supplier/supplier-service';
import { CreateStockOrderRequestDTO } from '../../../../../../types/stock-order/create-stock-order-request';
import { StockOrderService } from '../../../../../service/stock-order/stock-order-service';

@Component({
  selector: 'app-seller-create-stock-order',
  imports: [],
  templateUrl: './seller-create-stock-order.html',
  styleUrl: './seller-create-stock-order.css',
})
export class SellerCreateStockOrder {
  // Itens disponíveis para compra com este fornecedor específico
  supplierItems = signal<SupplierItemResponseDTO[]>([]);

  private supplierService = inject(SupplierService);
  private supplierId = signal("");
  private stockOrderService = inject(StockOrderService);
  private router = inject(Router);

  // Estado reativo das quantidades: { [supplierItemId]: quantidade }
  quantities = signal<Record<string, number>>({});

  constructor(private route: ActivatedRoute){};

  // Nome do fornecedor baseado nos itens do catálogo
  supplierName = computed(() => {
    const items = this.supplierItems();
    return items.length > 0 ? items[0].supplierName : 'Fornecedor';
  });

  // 1. Quantidade de itens unitários (tipos de produtos diferentes selecionados)
  uniqueItemsCount = computed(() => {
    return Object.values(this.quantities()).filter(qty => qty > 0).length;
  });

  // 2. Quantidade de itens total (soma de todas as unidades físicas)
  totalUnitsCount = computed(() => {
    return Object.values(this.quantities()).reduce((sum, qty) => sum + qty, 0);
  });

  // 3. Valor total baseado no preço de custo de atacado (lastPrice)
  totalOrderValue = computed(() => {
    const items = this.supplierItems();
    const currentQuantities = this.quantities();

    return items.reduce((total, supplierItem) => {
      const qty = currentQuantities[supplierItem.id] || 0;
      const basePrice = supplierItem.lastPrice ?? 0; 
      return total + (basePrice * qty);
    }, 0);
  });

  onQuantityChange(supplierItemId: string, event: Event) {
    const input = event.target as HTMLInputElement;
    const value = parseInt(input.value, 10) || 0;
    this.setQuantity(supplierItemId, value);
  }

  adjustQuantity(supplierItemId: string, amount: number) {
    const currentQty = this.quantities()[supplierItemId] || 0;
    this.setQuantity(supplierItemId, currentQty + amount);
  }

  private setQuantity(supplierItemId: string, value: number) {
    const sanitizedValue = Math.max(0, value);
    this.quantities.update(prev => ({ ...prev, [supplierItemId]: sanitizedValue }));
  }

  suppliersItemsMock: SupplierItemResponseDTO[] = [
  {
    id: "1",
    supplierName: "Fornecedor Alpha",
    supplierId:"01",
    item: {
      id: "item-1",
      categoryId: "cat-1",
      categoryName: "Ferramentas",
      name: "Furadeira de Impacto",
      about: "Furadeira elétrica 750W com impacto.",
      price: 99.90,
      stock: 15,
      deleted: true,
      imagePath1: "/images/furadeira.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "2",
    supplierName: "Fornecedor Beta",
    supplierId:"01",
    item: {
      id: "item-2",
      categoryId: "cat-2",
      categoryName: "Elétrica",
      name: "Disjuntor Bipolar 32A",
      about: "Disjuntor bipolar curva C.",
      price: 24.90,
      stock: 42,
      deleted: false,
      imagePath1: "/images/disjuntor.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "3",
    supplierName: "Fornecedor Gama",
    cProd: "GAM-889",
    lastPrice: 245.00,
    supplierId:"01",
    item: {
      id: "item-3",
      categoryId: "cat-3",
      categoryName: "Motores",
      name: "Motor Elétrico 2CV",
      about: "Motor trifásico 220/380V.",
      price: 299.90,
      stock: 8,
      deleted: false,
      imagePath1: "/images/motor.jpg",
      imagePath2: "/images/motor2.jpg",
      imagePath3: null,
    },
  },
  {
    id: "4",
    supplierName: "Fornecedor Delta",
    cProd: "DEL-502",
    lastPrice: 5.75,
    supplierId:"01",
    item: {
      id: "item-4",
      categoryId: "cat-4",
      categoryName: "Fixação",
      name: "Parafuso Sextavado M8",
      about: "Parafuso galvanizado M8 x 40mm.",
      price: 8.50,
      stock: 350,
      deleted: false,
      imagePath1: "/images/parafuso.jpg",
      imagePath2: null,
      imagePath3: null,
    },
  },
  {
    id: "5",
    supplierName: "Fornecedor Omega",
    cProd: "OMG-990",
    lastPrice: 135.00,
    supplierId:"01",
    item: {
      id: "item-5",
      categoryId: "cat-5",
      categoryName: "Automação",
      name: "Contator 25A",
      about: "Contator tripolar para acionamento de motores.",
      price: 159.90,
      stock: 21,
      deleted: false,
      imagePath1: "/images/contator.jpg",
      imagePath2: "/images/contator2.jpg",
      imagePath3: "/images/contator3.jpg",
    },
  },
  ];

  generateCreateStockOrder():CreateStockOrderRequestDTO{
    return {
      supplierId:this.supplierId(),
      items: Object.entries(this.quantities()).map(([itemId, quantity]) =>({
        itemId,
        quantity
      }))
    }
  }

  ngOnInit(){
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.supplierId.set(id)
      this.supplierService.getItemOfSupplierById(id).subscribe({
        next: (data) =>{
          this.supplierItems.set(data);
        },
        error: (err) => {
          console.error(err);
        }
      })
    }
  }

  ngOnSubmit(){
    this.stockOrderService.createStockOrder(this.generateCreateStockOrder()).subscribe({
      next: async() =>{
        await this.router.navigate(['/catalog/suppliers/', this.supplierId(), 'stock-orders'])
      },
      error: (err) => {
        console.error(err);
      }
    })
    
  }
}
