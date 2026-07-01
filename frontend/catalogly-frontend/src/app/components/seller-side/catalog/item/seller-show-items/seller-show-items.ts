import { DecimalPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ItemService } from '../../../../../service/item/item-service';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { HelperService } from '../../../../../service/helper/helper-service';
import { Router, RouterLink } from "@angular/router";

@Component({
  selector: 'app-seller-show-items',
  imports: [DecimalPipe, RouterLink],
  templateUrl: './seller-show-items.html',
  styleUrl: './seller-show-items.css',
})
export class SellerShowItems {
  private ItemService = inject(ItemService);
  items = signal(<ItemResponseDTO[]>[]);
  private helperService = inject(HelperService);
  private router = inject(Router);

 itemsMock: ItemResponseDTO[] = [
  {
    id: "550e8400-e29b-41d4-a716-446655440001",
    categoryName: "Eletrônicos",
    name: "Mouse Gamer RGB",
    about: "Mouse óptico com 7200 DPI e iluminação RGB.",
    price: 149.9,
    stock: 35,
    deleted: false,
    imagePath1: "/images/mouse-rgb-1.jpg",
    imagePath2: "/images/mouse-rgb-2.jpg",
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440002",
    categoryName: "Informática",
    name: "Teclado Mecânico",
    about: "Teclado mecânico com switches azuis e iluminação LED.",
    price: 299.9,
    stock: 18,
    deleted: true,
    imagePath1: "/images/teclado-1.jpg",
    imagePath2: null,
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440003",
    categoryName: "Áudio",
    name: "Headset Gamer",
    about: "Headset com som surround e microfone removível.",
    price: 249.5,
    stock: 12,
    deleted: false,
    imagePath1: "/images/headset-1.jpg",
    imagePath2: "/images/headset-2.jpg",
    imagePath3: "/images/headset-3.jpg"
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440004",
    categoryName: "Monitores",
    name: "Monitor Full HD 24\"",
    about: "Monitor IPS de 24 polegadas com taxa de atualização de 75Hz.",
    price: 899.99,
    stock: 7,
    deleted: false,
    imagePath1: "/images/monitor-1.jpg",
    imagePath2: null,
    imagePath3: null
  },
  {
    id: "550e8400-e29b-41d4-a716-446655440005",
    categoryName: "Armazenamento",
    name: "SSD NVMe 1TB",
    about: "SSD PCIe Gen4 com velocidades de leitura de até 7000 MB/s.",
    price: 549.0,
    stock: 20,
    deleted: false,
    imagePath1: "/images/ssd-1.jpg",
    imagePath2: "/images/ssd-2.jpg",
    imagePath3: null
  }
];


  ngOnInit() {
    this.ItemService.getItemsOfCatalog().subscribe({
      next:(data) =>{
        this.items.set(data);
      },
      error:(err) =>{
        console.error("Error fetching items of catalog: ", err);
      }
    });
    this.helperService.setAtualPage(1);
  }

  deleteItem(id:string){
    this.ItemService.deleteItemById(id).subscribe({
      next:async() =>{
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/products']);
      },
    }); 
  }
}
