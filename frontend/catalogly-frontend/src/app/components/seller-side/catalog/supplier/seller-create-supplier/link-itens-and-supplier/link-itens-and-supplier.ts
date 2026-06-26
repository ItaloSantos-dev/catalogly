import { Component, signal, Input, Output, EventEmitter } from '@angular/core';
import { ItemResponseDTO } from '../../../../../../../types/item/item-response';

@Component({
  selector: 'app-link-itens-and-supplier',
  imports: [],
  templateUrl: './link-itens-and-supplier.html',
  styleUrl: './link-itens-and-supplier.css',
})
export class LinkItensAndSupplier {

  @Input() items! : ItemResponseDTO[];
  itemsSelected = signal(<string[]>[])
  @Output() closeModal = new EventEmitter<void>();
  @Output() sendItens = new EventEmitter<string[]>();
  @Input() itensHasSelected! : string[];

  private itemsMock: ItemResponseDTO[] = [
  {
    id: "550e8400-e29b-41d4-a716-446655440001",
    categoryName: "Eletrônicos",
    name: "Mouse Gamer RGB",
    about: "Mouse gamer com sensor óptico de alta precisão e iluminação RGB.",
    price: 149.90,
    stock: 25,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=1",
    imagePath2: "https://picsum.photos/400/400?random=2",
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440002",
    categoryName: "Informática",
    name: "Teclado Mecânico",
    about: "Teclado mecânico com switches azuis e iluminação branca.",
    price: 299.90,
    stock: 12,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=3",
    imagePath2: null,
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440003",
    categoryName: "Áudio",
    name: "Headset Bluetooth",
    about: "Headset sem fio com cancelamento de ruído e bateria de 30 horas.",
    price: 459.99,
    stock: 8,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=4",
    imagePath2: "https://picsum.photos/400/400?random=5",
    imagePath3: "https://picsum.photos/400/400?random=6"
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440004",
    categoryName: "Acessórios",
    name: "Cabo USB-C",
    about: "Cabo USB-C de 2 metros com suporte para carregamento rápido.",
    price: 39.90,
    stock: 150,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=4",
    imagePath2: null,
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440005",
    categoryName: "Monitores",
    name: "Monitor 24\" Full HD",
    about: "Monitor IPS de 24 polegadas com taxa de atualização de 75 Hz.",
    price: 899.00,
    stock: 5,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=7",
    imagePath2: null,
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440006",
    categoryName: "Armazenamento",
    name: "SSD NVMe 1TB",
    about: "SSD NVMe PCIe 4.0 com velocidades de leitura de até 7000 MB/s.",
    price: 549.90,
    stock: 18,
    deleted: false,
    imagePath1: "https://picsum.photos/400/400?random=8",
    imagePath2: "https://picsum.photos/400/400?random=9",
    imagePath3: null
  }
];

  sendItensSelected(){
    this.sendItens.emit(this.itemsSelected());
    this.closeModal.emit();
  }

  selectItem(id:string){
    if(this.itemsSelected().includes(id)){
      this.itemsSelected.update(items =>
        items.filter(itemId => itemId !== id)
      );
    }
    else{
      this.itemsSelected.update(items => [...items, id])
    }
    
  }

  closingModal(){
    this.itemsSelected.set([])
    this.closeModal.emit();
  }

  ngOnInit(){
    console.log(this.items);
    
    this.itemsSelected.set(this.itensHasSelected);
    
  }

  



}
