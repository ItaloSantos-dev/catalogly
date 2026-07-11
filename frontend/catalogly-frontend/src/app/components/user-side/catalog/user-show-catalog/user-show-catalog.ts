import { Component, inject, signal } from '@angular/core';
import { CatalogPublicResponseDTO } from '../../../../../types/catalog/catalog-public-response';
import { ItemResponseDTO } from '../../../../../types/item/item-response';
import { ActivatedRoute } from '@angular/router';
import { CatalogService } from '../../../../service/catalog/catalog-service';

@Component({
  selector: 'app-user-show-catalog',
  imports: [],
  templateUrl: './user-show-catalog.html',
  styleUrl: './user-show-catalog.css',
})
export class UserShowCatalog {
  catalogService = inject(CatalogService);
  catalog = signal(<CatalogPublicResponseDTO>{});

  constructor(private route:ActivatedRoute){};

  catalogMock: CatalogPublicResponseDTO = {
    id:"01",
  sellerName: 'Tech Store LTDA',
  name: 'Tech Store',
  slug: 'tech-store',
  slogan: 'Tecnologia com os melhores preços',
  about: 'Somos uma loja especializada em eletrônicos, notebooks, smartphones e periféricos.',
  fisicAddress: 'Av. Brasil, 1500 - Centro, São Luís - MA',
  phone: '(98) 99123-4567',
  imageIconUrl: 'https://picsum.photos/200/200',
  imageBannerUrl: 'https://picsum.photos/1200/400',

  items: [
    {
      id: 'd6f70d2d-34d6-4b58-b5b8-c8477f236d1d',
      categoryName: 'Notebooks',
      categoryId: '0b54820d-4e72-4d30-a60d-6d9a0a9db7b5',
      name: 'Notebook Lenovo IdeaPad 3',
      about: 'Ryzen 5, 8GB RAM, SSD 512GB.',
      price: 3299.9,
      stock: 8,
      deleted: false,
      imagePath1: 'https://picsum.photos/400/400?1',
      imagePath2: 'https://picsum.photos/400/400?2',
      imagePath3: 'https://picsum.photos/400/400?3'
    },
    {
      id: 'e58b5634-42f5-49d2-89c7-6b1aef27d6a2',
      categoryName: 'Periféricos',
      categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
      name: 'Mouse Gamer Logitech G203',
      about: 'Mouse RGB com sensor de 8.000 DPI.',
      price: 149.9,
      stock: 35,
      deleted: false,
      imagePath1: 'https://picsum.photos/400/400?4',
      imagePath2: 'https://picsum.photos/400/400?5',
      imagePath3: 'https://picsum.photos/400/400?6'
    },
    {
      id: '3f9fdc82-f0df-4dc9-9c71-37f44d9b84c0',
      categoryName: 'Smartphones',
      categoryId: 'b38b41a3-ccaf-4b29-ae46-f58a5c5925db',
      name: 'Samsung Galaxy S25',
      about: '256GB de armazenamento e câmera de 50MP.',
      price: 4799,
      stock: 12,
      deleted: false,
      imagePath1: 'https://picsum.photos/400/400?7',
      imagePath2: 'https://picsum.photos/400/400?8',
      imagePath3: 'https://picsum.photos/400/400?9'
    },
    {
  id: 'f1111111-1111-1111-1111-111111111111',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'Teclado Mecânico Redragon Kumara',
  about: 'Switch Outemu Blue, RGB.',
  price: 249.9,
  stock: 15,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?10',
  imagePath2: 'https://picsum.photos/400/400?11',
  imagePath3: 'https://picsum.photos/400/400?12'
},
{
  id: 'f2222222-2222-2222-2222-222222222222',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'Headset HyperX Cloud Stinger',
  about: 'Headset gamer com microfone.',
  price: 329.9,
  stock: 20,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?13',
  imagePath2: 'https://picsum.photos/400/400?14',
  imagePath3: 'https://picsum.photos/400/400?15'
},
{
  id: 'f3333333-3333-3333-3333-333333333333',
  categoryName: 'Smartphones',
  categoryId: 'b38b41a3-ccaf-4b29-ae46-f58a5c5925db',
  name: 'iPhone 16',
  about: '128GB, tela OLED.',
  price: 6999,
  stock: 5,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?16',
  imagePath2: 'https://picsum.photos/400/400?17',
  imagePath3: 'https://picsum.photos/400/400?18'
},
{
  id: 'f4444444-4444-4444-4444-444444444444',
  categoryName: 'Notebooks',
  categoryId: '0b54820d-4e72-4d30-a60d-6d9a0a9db7b5',
  name: 'Acer Nitro V15',
  about: 'Intel Core i5, RTX 4050.',
  price: 5899.9,
  stock: 6,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?19',
  imagePath2: 'https://picsum.photos/400/400?20',
  imagePath3: 'https://picsum.photos/400/400?21'
},
{
  id: 'f5555555-5555-5555-5555-555555555555',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'Monitor LG UltraGear 24"',
  about: 'Full HD, 144Hz.',
  price: 1099.9,
  stock: 11,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?22',
  imagePath2: 'https://picsum.photos/400/400?23',
  imagePath3: 'https://picsum.photos/400/400?24'
},
{
  id: 'f6666666-6666-6666-6666-666666666666',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'SSD Kingston NV2 1TB',
  about: 'SSD NVMe PCIe 4.0.',
  price: 449.9,
  stock: 28,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?25',
  imagePath2: 'https://picsum.photos/400/400?26',
  imagePath3: 'https://picsum.photos/400/400?27'
},
{
  id: 'f7777777-7777-7777-7777-777777777777',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'Webcam Logitech C920',
  about: 'Full HD 1080p.',
  price: 379.9,
  stock: 14,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?28',
  imagePath2: 'https://picsum.photos/400/400?29',
  imagePath3: 'https://picsum.photos/400/400?30'
},
{
  id: 'f8888888-8888-8888-8888-888888888888',
  categoryName: 'Smartphones',
  categoryId: 'b38b41a3-ccaf-4b29-ae46-f58a5c5925db',
  name: 'Xiaomi Redmi Note 15',
  about: '256GB, 12GB RAM.',
  price: 2399.9,
  stock: 18,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?31',
  imagePath2: 'https://picsum.photos/400/400?32',
  imagePath3: 'https://picsum.photos/400/400?33'
},
{
  id: 'f9999999-9999-9999-9999-999999999999',
  categoryName: 'Periféricos',
  categoryId: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
  name: 'Mouse Pad Gamer XXL',
  about: '90x40 cm, superfície speed.',
  price: 89.9,
  stock: 40,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?34',
  imagePath2: 'https://picsum.photos/400/400?35',
  imagePath3: 'https://picsum.photos/400/400?36'
},
{
  id: 'faaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
  categoryName: 'Notebooks',
  categoryId: '0b54820d-4e72-4d30-a60d-6d9a0a9db7b5',
  name: 'MacBook Air M4',
  about: '13 polegadas, 16GB RAM, 512GB SSD.',
  price: 10999,
  stock: 3,
  deleted: false,
  imagePath1: 'https://picsum.photos/400/400?37',
  imagePath2: 'https://picsum.photos/400/400?38',
  imagePath3: 'https://picsum.photos/400/400?39'
},
  ],

  categorys: [
    {
      id: '0b54820d-4e72-4d30-a60d-6d9a0a9db7b5',
      name: 'Notebooks',
      description: 'Notebooks para estudo e trabalho.',
      itemsCount: 1,
      items: []
    },
    {
      id: 'd8821c45-13fd-47ef-9b77-983e79d2f6a4',
      name: 'Periféricos',
      description: 'Mouses, teclados e acessórios.',
      itemsCount: 1,
      items: []
    },
    {
      id: 'b38b41a3-ccaf-4b29-ae46-f58a5c5925db',
      name: 'Smartphones',
      description: 'Os melhores smartphones do mercado.',
      itemsCount: 1,
      items: []
    }
  ],

  coupons: [
    {
      id: 'a8f34d88-77b1-4cbe-a5d4-5bb2c8fd1d11',
      slug: 'BEMVINDO10',
      amount: 10,
      amountMinimum: 100,
      amountDiscountMaximum: 50,
      active: true
    },
    {
      id: 'd9fd9b77-31f0-42fb-8d02-5f7c0cb7af92',
      slug: 'FRETEGRATIS',
      amount: 20,
      amountMinimum: 200,
      amountDiscountMaximum: 20,
      active: true
    },
    {
      id: 'fcb0eb9c-92d2-4d4f-8cf0-cfe0d28dc8d5',
      slug: 'BLACK50',
      amount: 50,
      amountMinimum: 1000,
      amountDiscountMaximum: 200,
      active: false
    }
  ]
  };

  // NOVO: Controle de estado do Balão de Filtros
  isFilterOpen = signal<boolean>(false);
  
  // NOVO: Estados dos filtros aplicados
  selectedCategoryId = signal<string | null>(null);
  priceMin = signal<number | null>(null);
  priceMax = signal<number | null>(null);

  toggleFilterBalloon() {
    this.isFilterOpen.update(state => !state);
  }

  selectCategory(id: string | null) {
    this.selectedCategoryId.set(id);
  }

  clearFilters() {
    this.selectedCategoryId.set(null);
    this.priceMin.set(null);
    this.priceMax.set(null);
    this.isFilterOpen.set(false);
  }

  // Função simulada para o clique de compra do usuário
  addToCart(item: ItemResponseDTO) {
    console.log('Adicionando ao carrinho da Catalogly:', item.name);
  }

  ngOnInit(){
    const slug = this.route.snapshot.paramMap.get("slug");

    if (slug) {
      console.log("VEIO");
      
      this.catalogService.getCatalogPublicBySlug(slug).subscribe({
        next:(data) =>{
          this.catalog.set(data);
        },
        error:(err) =>{
          console.log(err);
          
        }
      })
    }
  }

}
