import { Component, computed, inject, signal } from '@angular/core';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { ActivatedRoute } from '@angular/router';
import { ItemService } from '../../../../../service/item/item-service';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-show-item',
  imports: [],
  templateUrl: './seller-show-item.html',
  styleUrl: './seller-show-item.css',
})
export class SellerShowItem {
  item = signal(<ItemResponseDTO>{});
  private itemService = inject(ItemService);
  private helperService = inject(HelperService);
  
  constructor(private route: ActivatedRoute){
    
  }

  // Signal local para controlar a foto selecionada em destaque
  selectedImage = signal<string | null>(null);

  // Computed para garantir que sempre haja uma foto em exibição
  activePreviewImage = computed(() => {
    return this.selectedImage() || this.item()?.imagePath1 || null;
  });

  totalValueStocked = computed(() =>{
    return this.item().price * this.item().stock;
  })

  itemMock: ItemResponseDTO = {
    id: "550e8400-e29b-41d4-a716-446655440001",
    categoryName: "Eletrônicos",
    name: "Mouse Gamer RGB",
    about: "Mouse óptico com 7200 DPI e iluminação RGB.",
    price: 149.9,
    stock: 35,
    deleted: false,
    imagePath1: "https://imgs.search.brave.com/Dok21lIN5QAkj99whpsdVfR-A7HENGVxEjKBwLwaKQI/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly90aHVt/YnMuZHJlYW1zdGlt/ZS5jb20vYi9ibGFj/ay1nYW1pbmctbW91/c2UtcmdiLWxpZ2h0/aW5nLWlzb2xhdGVk/LXdoaXRlLWJhY2tn/cm91bmQtYmxhY2st/Z2FtaW5nLW1vdXNl/LXJnYi1saWdodGlu/Zy1pc29sYXRlZC13/aGl0ZS0zNTYzMTA0/MjIuanBn",
    imagePath2: "https://imgs.search.brave.com/yE5nWP4cO51JhOxowJbsbOfOVKv5yRedcGj63w8DLBk/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NTEwS1JhNHcrREwu/anBn",
    imagePath3: "https://imgs.search.brave.com/VlaGLMMjX-TyJRl_aTWfzLpPudzksc6oize7gmaNfno/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9jZG4u/YXdzbGkuY29tLmJy/LzMwMHgzMDAvMjU0/Ny8yNTQ3MzU4L3By/b2R1dG8vMjA2MTk2/ODIyL21vdXNlLWdh/bWVyLXJlZHJhZ29u/LWNlbnRyb3Bob3J1/cy0yLXJnYi1tNjAx/LXJnYi00LWozMGRz/amVyY2Yud2VicA"
  };

  ngOnInit(){
    this.helperService.setAtualPage(1)
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.itemService.getItemById(id).subscribe({
        next:(data) =>{
          this.item.set(data);
        }
      })
    }

  }

}
