import { Component, inject, signal } from '@angular/core';
import { SellerService } from '../../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserRole } from '../../../../../../types/enums/user-role';
import { LinkItensAndCategory } from './link-itens-and-category/link-itens-and-category';
import { CreateCategoryRequestDTO } from '../../../../../../types/category/create-category-request';
import { CategoryService } from '../../../../../service/category/category-service';
import { Router } from '@angular/router';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-create-category',
  imports: [ReactiveFormsModule, LinkItensAndCategory],
  templateUrl: './seller-create-category.html',
  styleUrl: './seller-create-category.css',
})
export class SellerCreateCategory {
  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{});
  itemsSelected = signal(<string[]>[])
  private categoryService = inject(CategoryService);
  private router = inject(Router);
  private helperService = inject(HelperService);

  formCreateCategory = new FormGroup({
    name: new FormControl("", [Validators.min(4), Validators.required]),
    description: new FormControl("", [Validators.required, Validators.min(4)])
  })

   private catalogPrivateResponseDTO: CatalogPrivateResponseDTO = {
  id: '1',
  name: 'Loja Exemplo',
  slug: 'loja-exemplo',
  slogan: 'Os melhores produtos para você',
  about: 'Catálogo de exemplo para testes.',
  fisicAddress: 'Rua das Flores, 123',
  phone: '(99) 99999-9999',
  imageIconUrl: 'https://example.com/icon.png',
  imageBannerUrl: 'https://example.com/banner.png',

  sellerData: {
    firstName: 'Italo',
    lastName: 'Santos',
    userRole: UserRole.SELLER,
    phone: '(99) 99999-9999'
  },

  catalogDashboard: {
    catalogId: '1',
    name: 'Loja Exemplo',
    slug: 'loja-exemplo',
    totalItems: 150,
    totalCategories: 8,
    totalOrders: 320,
    completedOrders: 280,
    pendingOrders: 40,
    totalRevenue: 25430.75,
    averageOrderValue: 79.47,
    stockValue: 18500,
    lowStockItems: 5,
    outOfStockItems: 2,
    activeCoupons: 3,
    pendingInvoices: 4,

    topItems: [
      {
        itemId: '1',
        name: 'Hambúrguer Artesanal',
        quantitySold: 120,
        revenue: 3600
      },
      {
        itemId: '2',
        name: 'Pizza Calabresa',
        quantitySold: 95,
        revenue: 4750
      }
    ],

    recentOrders: [
      {
        orderId: '101',
        buyerName: 'João Silva',
        status: 'COMPLETED',
        total: 89.9,
        createdAt: '2026-06-20T18:30:00'
      },
      {
        orderId: '102',
        buyerName: 'Maria Souza',
        status: 'PENDING',
        total: 45.5,
        createdAt: '2026-06-20T19:15:00'
      }
    ],

    categories: [
      {
        name: 'Lanches',
        itemsCount: 20
      },
      {
        name: 'Bebidas',
        itemsCount: 12
      }
    ]
  },

  categoryNamesAndIds: [
    {
      id: '1',
      name: 'Lanches'
    },
    {
      id: '2',
      name: 'Bebidas'
    }
  ],

  items: [
    {
    id: '1',
    categoryName: 'Lanches',
    name: 'Hambúrguer Artesanal',
    about: 'Hambúrguer com carne bovina, queijo e molho especial.',
    price: 29.9,
    stock: 50,
    deleted: false,
    imagePath1: 'https://example.com/images/burger1.jpg',
    imagePath2: 'https://example.com/images/burger2.jpg',
    imagePath3: null
  },
  {
    id: '2',
    categoryName: 'Lanches',
    name: 'X-Bacon',
    about: 'Pão, hambúrguer, queijo, bacon e salada.',
    price: 32.5,
    stock: 35,
    deleted: false,
    imagePath1: 'https://example.com/images/xbacon.jpg',
    imagePath2: null,
    imagePath3: null
  },
  {
    id: '3',
    categoryName: 'Bebidas',
    name: 'Refrigerante 2L',
    about: 'Refrigerante gelado de 2 litros.',
    price: 12,
    stock: 80,
    deleted: false,
    imagePath1: 'https://example.com/images/refrigerante.jpg',
    imagePath2: null,
    imagePath3: null
  },
  {
    id: '4',
    categoryName: 'Sobremesas',
    name: 'Brownie',
    about: 'Brownie de chocolate com cobertura.',
    price: 15,
    stock: 20,
    deleted: false,
    imagePath1: 'https://example.com/images/brownie.jpg',
    imagePath2: 'https://example.com/images/brownie2.jpg',
    imagePath3: null
  },
  {
    id: '5',
    categoryName: '',
    name: 'Produto Sem Categoria',
    about: 'Item utilizado para testes.',
    price: 9.99,
    stock: 10,
    deleted: false,
    imagePath1: 'https://example.com/images/brownie.jpg',
    imagePath2: null,
    imagePath3: null
  }
  ]
  };

  isModalOpen = signal(false);

  closeModal(){
    this.isModalOpen.set(false);
  }
  receiveItens(itens:string[]){
    this.itemsSelected.set(itens);
  }

  private createCategoryRequest():CreateCategoryRequestDTO{
    
    
    let  itens;
    this.itemsSelected().length>0? itens = this.itemsSelected() : itens = null;

    console.log(this.itemsSelected());
    return {
      name: this.formCreateCategory.get('name')?.value as string,
      description: this.formCreateCategory.get('description')?.value as string,
      items: itens
    }
  }


  ngOnInit(){
    this.helperService.setAtualPage(3)
    this.sellerService.getMyCatalog().subscribe((data)=>{
      if(data){
        this.catalogPrivateOfSeller.set(data);
      }
    })

    this.catalogPrivateOfSeller.set(this.catalogPrivateResponseDTO)
  }

  ngOnSubmit(){
    const request = this.createCategoryRequest();
    console.log(request);
    
    this.categoryService.createCategory(request).subscribe({
      next: async () => {
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/categories']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

}
