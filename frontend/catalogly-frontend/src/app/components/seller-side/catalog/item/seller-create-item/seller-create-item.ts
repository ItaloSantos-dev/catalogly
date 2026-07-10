import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreateItemRequestDTO } from '../../../../../../types/item/create-item-request';
import { CatalogService } from '../../../../../service/catalog/catalog-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerService } from '../../../../../service/seller/seller-service';
import { ItemService } from '../../../../../service/item/item-service';
import { Router } from '@angular/router';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-create-item',
  imports: [ReactiveFormsModule],
  templateUrl: './seller-create-item.html',
  styleUrl: './seller-create-item.css',
})
export class SellerCreateItem {

  private sellerService = inject(SellerService);
  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{});
  private itemService = inject(ItemService);
  router = inject(Router);
  private helperService = inject(HelperService);

  // Arquivos capturados nos eventos (change) das fotos


  formCreateItem = new FormGroup({
    categoryId: new FormControl(''),
    name: new FormControl('', [Validators.required, Validators.minLength(4)]),
    about: new FormControl('', [Validators.required, Validators.minLength(4)]),
    price: new FormControl('', [Validators.required, Validators.min(0.01)]),
    stock: new FormControl('', [Validators.required, Validators.min(0)]),
  });

  private createItemRequest():CreateItemRequestDTO{
    return {
      categoryId: this.formCreateItem.controls.categoryId.value,
      name: this.formCreateItem.controls.name.value as string,
      about: this.formCreateItem.controls.about.value as string,
      price: this.formCreateItem.controls.price.value as string,
      stock: Number(this.formCreateItem.controls.stock.value)
    };
  }

  image1Path = signal('');
  image2Path = signal('');
  image3Path = signal('');

  image1File!: File;
  image2File!: File;
  image3File!: File;

  onFileSelected(event: Event, control:number) {
    const input = event.target as HTMLInputElement;

    //ImageIcon
    if (input.files?.length) {
      const file = input.files[0];

      if (control===1) {
        this.image1Path.set(URL.createObjectURL(file))
        this.image1File = file;
      }
      else if (control===2){
        this.image2Path.set(URL.createObjectURL(file))
        this.image2File = file;
      }
      else{
        this.image3Path.set(URL.createObjectURL(file))
        this.image3File = file;
      }

      console.log(file.name);
      
    }

  }

  ngOnInit(){
    this.helperService.setAtualPage(2)
    this.sellerService.catalogPrivateData$.subscribe(data =>{
      if(data){
        console.log(data);
        (data);
        this.catalogPrivateOfSeller.set(data);
        
      }
    })
  }

  private createFormData():FormData{
    if (!this.image1File)
      window.alert("Selecione as imagens pendentes")

    const formData = new FormData();

    formData.append('data', new Blob([JSON.stringify(this.createItemRequest())], { type: 'application/json' }));

    // 2. Adiciona as imagens correspondentes aos RequestParts do back-end
    formData.append('image1', this.image1File);
    
    if (this.image2File) {
      formData.append('image2', this.image2File);
    }
    if (this.image3File) {
      formData.append('image3', this.image3File);
    }
    return formData;
  }

  onSubmit() {
    this.itemService.createItem(this.createFormData()).subscribe({
      next: async () => {
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/products']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }
}
