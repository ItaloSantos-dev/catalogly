import { Component, signal, Input, computed, inject } from '@angular/core';
import { SupplierItemWithCprodResponseDTO } from '../../../../../../types/tie-supplier-item/supplier-item-cprod/supplier-item-with-cprod-response';
import { TieItemStockOrder } from '../../../../../../types/tie-supplier-item/supplier-item-cprod/tie-item-stock-order';
import { StockOrderService } from '../../../../../service/stock-order/stock-order-service';
import { UpdateCprodOfSupplierItemsRequestDTO } from '../../../../../../types/tie-supplier-item/update-cprod/update-cprod-of-supplier-items-request';
import { TieCprodInvoiceWithSupplierItemId } from '../../../../../../types/tie-supplier-item/update-cprod/tie-cprod-invoice-with-supplier-item-id';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-seller-tie-item-with-cprod',
  imports: [],
  templateUrl: './seller-tie-item-with-cprod.html',
  styleUrl: './seller-tie-item-with-cprod.css',
})
export class SellerTieItemWithCprod {
  itemsAndCprod = signal(<SupplierItemWithCprodResponseDTO>{});
  private stockOrderService = inject(StockOrderService);
  private router = inject(Router);
  private stockOrderId = "";
  

  constructor(private route:ActivatedRoute){};

  supplierItemWithCprodMock: SupplierItemWithCprodResponseDTO = {
    stockOrderId:"01",
    supplierId:"02",
    stockOrderItems: [
    {
      stockOrderItemId: '8f4d1b27-4d18-4fd2-8b4e-4f5d7f0d8a01',
      stockOrderItemName: 'Arroz Tipo 1 5kg',
      linkImage1: 'https://picsum.photos/200?random=1'
    },
    {
      stockOrderItemId: '2c9f9f90-82c2-49c0-a7f3-5c3d72f3b002',
      stockOrderItemName: 'Feijão Carioca 1kg',
      linkImage1: 'https://picsum.photos/200?random=2'
    },
    {
      stockOrderItemId: '7d5b4c12-ef8e-42d0-9d34-ef4c83b9a003',
      stockOrderItemName: 'Macarrão Espaguete 500g',
      linkImage1: 'https://picsum.photos/200?random=3'
    }
  ],

  invoiceItems: [
    {
      itemInvoiceCprod: '789100000001',
      itemInvoiceName: 'Arroz Tipo 1 Premium 5kg',
      itemInvoicePrice: 29.9
    },
    {
      itemInvoiceCprod: '789100000002',
      itemInvoiceName: 'Feijão Carioca Especial 1kg',
      itemInvoicePrice: 8.49
    },
    {
      itemInvoiceCprod: '789100000003',
      itemInvoiceName: 'Macarrão Espaguete 500g',
      itemInvoicePrice: 5.99
    }
  ],

  xmlLink:
    'http://localhost:9000/catalogly-media/catalog/stock-order-invoice-xml/exemplo.xml'
};

  // Signal preenchido após o upload bem-sucedido do XML
  stockOrderItemsAndCprod = signal(<SupplierItemWithCprodResponseDTO> {});

  // Dicionário reativo para armazenar as ligações: { [itemInvoiceCprod]: stockOrderItemId }
  links = signal<Record<string, string>>({});

  // Computa quantos itens da nota já foram devidamente vinculados
  mappedCount = computed(() => {
    return Object.values(this.links()).filter(id => id !== '').length;
  });

  // Verifica se a conciliação foi 100% concluída para liberar o envio
  isMappingComplete = computed(() => {
    const totalInvoiceItems = this.stockOrderItemsAndCprod()?.invoiceItems?.length ?? 0;
    return totalInvoiceItems > 0 && this.mappedCount() === totalInvoiceItems;
  });

  // Atualiza o vínculo quando o usuário seleciona um produto no dropdown
  onLinkItem(cprod: string, event: Event) {
    const selectElement = event.target as HTMLInputElement;
    const stockOrderItemId = selectElement.value;

    this.links.update(prev => ({
      ...prev,
      [cprod]: stockOrderItemId
    }));
  }

  itemHasSelected(itemId:string){
    return Object.values(this.links()).includes(itemId);
  }

  // Retorna o objeto do item do pedido baseado no ID selecionado (para exibir a miniatura dinamicamente)
  getSelectedStockItem(id: string|undefined): TieItemStockOrder | undefined {
    if (!id) return undefined;
    return this.stockOrderItemsAndCprod()?.stockOrderItems.find(item => item.stockOrderItemId === id);
  }

  // Envia a matriz de relacionamento gerada para o backend persistir o cProd

  ngGenerateUpdateCprodOfItems():UpdateCprodOfSupplierItemsRequestDTO{
    const tieItems:TieCprodInvoiceWithSupplierItemId[] = Object.entries(this.links()).map(([cprod, stockItemId]) => ({
      cProd: cprod,
      stockOrderItemId: stockItemId
    }));
    console.log(tieItems);
    
    return {
      tieItems:tieItems
    }
  }

  submitMapping() {
    this.stockOrderService.updateCprodOfSupplierItens(this.stockOrderId, this.ngGenerateUpdateCprodOfItems()).subscribe({
      next:() =>{
        //suppliers/id/items
        this.router.navigate(['/catalog','suppliers', this.stockOrderItemsAndCprod().supplierId, 'items'])
      }
    })
  }

  ngOnInit(){
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.stockOrderId = id;
      this.stockOrderService.getCprodAndSupplierItemsOfStockOrderById(id).subscribe({
        next:(data) =>{
          
          this.stockOrderItemsAndCprod.set(data);
        },
        error: (err) => {
          console.error(err);
        }
      })
    }
  }
}
