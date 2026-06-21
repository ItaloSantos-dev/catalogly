import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HelperService } from '../../../../../service/helper/helper-service';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { CategoryService } from '../../../../../service/category/category-service';

@Component({
  selector: 'app-seller-show-items-of-category',
  imports: [RouterLink],
  templateUrl: './seller-show-items-of-category.html',
  styleUrl: './seller-show-items-of-category.css',
})
export class SellerShowItemsOfCategory {
  private helperService = inject(HelperService);
  itemsOfCategory = signal(<ItemResponseDTO[]> [])
  showInativeItems = signal(false);
  private categoryService = inject(CategoryService);
  constructor(private route: ActivatedRoute){
  
  }


  items: ItemResponseDTO[] = [
    {
      id: '550e8400-e29b-41d4-a716-446655440000',
      categoryName: 'Periféricos',
      name: 'Mouse Gamer RGB',
      about: 'Mouse sem fio com iluminação RGB e sensor óptico.',
      price: 129.90,
      stock: 15,
      deleted: false,
      imagePath1: '/images/mouse1.jpg',
      imagePath2: '/images/mouse2.jpg',
      imagePath3: null
    },
    {
      id: '550e8400-e29b-41d4-a716-446655440001',
      categoryName: 'Periféricos',
      name: 'Teclado Mecânico',
      about: 'Teclado mecânico com switches azuis e RGB.',
      price: 249.90,
      stock: 8,
      deleted: false,
      imagePath1: '/images/keyboard1.jpg',
      imagePath2: null,
      imagePath3: null
    },
    {
      id: '550e8400-e29b-41d4-a716-446655440002',
      categoryName: 'Periféricos',
      name: 'Mouse Pad XL',
      about: 'Mouse pad de grande dimensão com base antiderrapante.',
      price: 59.90,
      stock: 25,
      deleted: false,
      imagePath1: '/images/mousepad1.jpg',
      imagePath2: null,
      imagePath3: null
    },
    {
      id: '550e8400-e29b-41d4-a716-446655440003',
      categoryName: 'Periféricos',
      name: 'Headset Gamer',
      about: 'Headset estéreo com microfone removível.',
      price: 199.90,
      stock: 12,
      deleted: false,
      imagePath1: '/images/headset1.jpg',
      imagePath2: '/images/headset2.jpg',
      imagePath3: null
    },
    {
      id: '550e8400-e29b-41d4-a716-446655440004',
      categoryName: 'Periféricos',
      name: 'Webcam Full HD',
      about: 'Webcam 1080p com microfone integrado.',
      price: 149.90,
      stock: 6,
      deleted: true,
      imagePath1: '/images/webcam1.jpg',
      imagePath2: null,
      imagePath3: null
    }
  ];


  // Quantidade total de itens cadastrados (variedade)
  totalItemsCount = computed(() => this.itemsOfCategory().length);

  // Quantidade total de itens ativos 
  totalItemsActivatedCount = computed(() => this.itemsOfCategory().filter(i => !i.deleted).length);

  // Quantidade total de unidades físicas somadas no estoque
  totalStockUnits = computed(() => this.itemsOfCategory().filter(i => !i.deleted).reduce((acc, item) => acc + item.stock, 0));

  // Valor financeiro total que a categoria acumula (Preço * Estoque)
  totalCategoryValue = computed(() => this.itemsOfCategory().filter(i => !i.deleted).reduce((acc, item) => acc + (item.price * item.stock), 0));

  // Nome da Categoria (pega do primeiro item válido)
  categoryName = computed(() => this.itemsOfCategory().find(i => !i.deleted)?.categoryName || 'Categoria');

  ngOnInit(){
    this.helperService.setAtualPage(2);
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.categoryService.getItensOfCategoryById(id).subscribe({
        next: (data) =>{
          this.itemsOfCategory.set(data)
        }
      })
    }
  }
}
