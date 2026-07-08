import { Component, computed, inject, signal } from '@angular/core';
import { StockOrderResponseDTO } from '../../../../../../types/stock-order/stock-order-response';
import { StockOrderService } from '../../../../../service/stock-order/stock-order-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { StockOrderStatus } from '../../../../../../types/enums/stock-order-status';

@Component({
  selector: 'app-seller-show-stock-order',
  imports: [RouterLink],
  templateUrl: './seller-show-stock-order.html',
  styleUrl: './seller-show-stock-order.css',
})
export class SellerShowStockOrder {
  stockOrder = signal(<StockOrderResponseDTO>{});
  private stockOrderService = inject(StockOrderService);
  xmlFile = signal(<File>{});
  private stockOrderId = signal("");
  private router = inject(Router);


  constructor (private route:ActivatedRoute){};

  // Expõe o enum de status para validação no HTML
  OrderStatus = StockOrderStatus;

  // Calcula a quantidade de tipos/itens únicos com base no array opcional
  uniqueItemsCount = computed(() => {
    return this.stockOrder()?.supplierItems?.length ?? 0;
  });

  selectedFileName = '';

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;

    if (input.files?.length) {
      this.xmlFile.set(input.files[0])
      this.selectedFileName = input.files[0].name;
    } else {
      this.selectedFileName = '';
    }
  }


 stockOrderMock: StockOrderResponseDTO = {
  id: "b4a7b0a5-3c9d-4b44-8d4f-8f65e9b7c201",
  sellerId: "8c2b0b59-fc4d-4d2e-9f4d-1b7d9e72d111",
  sellerName: "João Silva",
  supplierId: "eda01daf-8ea2-4eba-8085-2e637f949e60",
  supplierName: "Distribuidora ABC",
  itemsAmount: 22,
  status: StockOrderStatus.PENDING,
  invoiceXmlurl:"ssss",
  supplierItems: [
    {
      amount: 5,
      supplierItem: {
        id: "si-001",
        supplierName: "Distribuidora ABC",
        supplierId: "eda01daf-8ea2-4eba-8085-2e637f949e60",
        cProd: "789100000001",
        lastPrice: 12.5,
        item: {
          id: "item-001",
          categoryId: "cat-001",
          categoryName: "Bebidas",
          name: "Refrigerante Cola 2L",
          about: "Garrafa PET 2 litros",
          price: 13.5,
          stock: 120,
          deleted: false,
          imagePath1: "/assets/images/cola-2l.png",
          imagePath2: null,
          imagePath3: null
        }
      }
    },
    {
      amount: 10,
      priceUnique: 8.9,
      priceFinal: 89,
      supplierItem: {
        id: "si-002",
        supplierName: "Distribuidora ABC",
        supplierId: "eda01daf-8ea2-4eba-8085-2e637f949e60",
        cProd: "789100000002",
        lastPrice: 8.9,
        item: {
          id: "item-002",
          categoryId: "cat-002",
          categoryName: "Alimentos",
          name: "Arroz Tipo 1 5kg",
          about: "Pacote de arroz tipo 1",
          price: 9.5,
          stock: 80,
          deleted: false,
          imagePath1: "/assets/images/arroz-5kg.png",
          imagePath2: null,
          imagePath3: null
        }
      }
    },
    {
      amount: 7,
      priceUnique: 13,
      priceFinal: 91,
      supplierItem: {
        id: "si-003",
        supplierName: "Distribuidora ABC",
        supplierId: "eda01daf-8ea2-4eba-8085-2e637f949e60",
        cProd: "789100000003",
        lastPrice: 13,
        item: {
          id: "item-003",
          categoryId: "cat-003",
          categoryName: "Limpeza",
          name: "Detergente 500ml",
          about: "Detergente neutro",
          price: 14,
          stock: 200,
          deleted: false,
          imagePath1: "/assets/images/detergente.png",
          imagePath2: null,
          imagePath3: null
        }
      }
    }
  ]
};

  ngOnInit(){
    const id = this.route.snapshot.paramMap.get("id");

    if(id){
      this.stockOrderId.set(id);
      this.stockOrderService.getStockOrderById(id).subscribe({
        next:(data) =>{
          this.stockOrder.set(data);
          console.log(this.stockOrder());
          
        },
        error: (err) => {
          console.error(err);
        }
      })
    }

    this.stockOrder.set(this.stockOrderMock);
  }

  generateFormData():FormData{
    console.log(this.xmlFile());
    
    const formData = new FormData();

    formData.append('invoiceXml', this.xmlFile());
    formData.append('stockOrderId', this.stockOrderId());

    return formData;
  }

  onSubmitXmlOfOrder(){
    this.stockOrderService.updateInvoiceXmlOfStockOrderById(this.generateFormData()).subscribe({
      next:async() =>{
        await this.router.navigate(['/catalog/stock-order', this.stockOrderId(), 'tie-items']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  downloadXml() {
    console.log("Baixando xml");
    
    const a = document.createElement('a');
    const url = this.stockOrder().invoiceXmlurl as string;

    fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao baixar o XML');
      }

      return response.blob();
    })
    .then(blob => {
      const blobUrl = URL.createObjectURL(blob);

      const a = document.createElement('a');
      a.href = blobUrl;
      a.download = `stock-order-${this.stockOrder().supplierName}-#${this.stockOrder().id}.xml`;

      document.body.appendChild(a);
      a.click();
      a.remove();

      URL.revokeObjectURL(blobUrl);
    })
      .catch(err => {
        console.error('Erro ao baixar XML:', err);
    });
  }
}
