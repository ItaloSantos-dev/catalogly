import { Component, inject, signal } from '@angular/core';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { CategoryRespondeDTO } from '../../../../../../types/category/category-response';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerService } from '../../../../../service/seller/seller-service';
import { HelperService } from '../../../../../service/helper/helper-service';
import { RouterLinkActive, RouterLink, Router } from "@angular/router";
import { CategoryService } from '../../../../../service/category/category-service';

@Component({
  selector: 'app-seller-show-categorys',
  imports: [ RouterLink],
  templateUrl: './seller-show-categorys.html',
  styleUrl: './seller-show-categorys.css',
})
export class SellerShowCategorys {
  private catalogService = inject(CatalogService);
  private sellerService = inject(SellerService);
  categorysOfCatalog = signal(<CategoryRespondeDTO[]>[]);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{});
  private helperService = inject(HelperService);
  private categoryService = inject(CategoryService);
  private router = inject(Router);


  // categories: CategoryRespondeDTO[] = [
  // {
  //   id: "1",
  //   name: "Eletrônicos",
  //   description: "Produtos eletrônicos em geral",
  //   itemsCount: 25
  // },
  // {
  //   id: "2",
  //   name: "Roupas",
  //   description: "Vestuário masculino e feminino",
  //   itemsCount: 48
  // },
  // {
  //   id: "3",
  //   name: "Calçados",
  //   description: "Tênis, sapatos e sandálias",
  //   itemsCount: 19
  // },
  // {
  //   id: "4",
  //   name: "Livros",
  //   description: "Livros físicos e digitais",
  //   itemsCount: 67
  // },
  // {
  //   id: "5",
  //   name: "Casa e Decoração",
  //   description: "Itens para decoração e utilidades domésticas",
  //   itemsCount: 31
  // }
  // ];

  ngOnInit(){
    this.sellerService.getMyCatalog().subscribe((data) =>{
      if (data) {
        this.catalogPrivateOfSeller.set(data);

        this.catalogService.getCategorysOfCatalogById(this.catalogPrivateOfSeller().id).subscribe({
          next:(categories) =>{
            this.categorysOfCatalog.set(categories);
          },
          error:(err) =>{
            console.error("Error fetching categorys of catalog: ", err);
          }
        })

      }
    })

    this.helperService.setAtualPage(2);

  }


  deleteCategory(id:string){
    this.categoryService.deleteCategoryById(id).subscribe({
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
