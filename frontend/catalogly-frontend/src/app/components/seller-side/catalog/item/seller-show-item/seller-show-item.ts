import { Component, computed, inject, signal } from '@angular/core';
import { ItemResponseDTO } from '../../../../../../types/item/item-response';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService } from '../../../../../service/item/item-service';
import { HelperService } from '../../../../../service/helper/helper-service';
import { FormControl, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from '@angular/forms';
import { SellerService } from '../../../../../service/seller/seller-service';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { CategoryNameAndId } from '../../../../../../types/category/category-name-and-id';
import { UpdateItemRequestDTO } from '../../../../../../types/item/update-item-request';
import { UpdateImageItem } from '../../../../../../types/item/update-image-item';

@Component({
  selector: 'app-seller-show-item',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  templateUrl: './seller-show-item.html',
  styleUrl: './seller-show-item.css',
})
export class SellerShowItem {
  item = signal(<ItemResponseDTO>{});
  private itemService = inject(ItemService);
  private helperService = inject(HelperService);
  formUpdateItemIsActive = signal(false);
  private sellerService = inject(SellerService);
  privateCatalogOfSeller = signal(<CatalogPrivateResponseDTO> {});
  private itemId = signal(<string | null> null);
  private router = inject(Router);
  private updateImagesItem = signal(<boolean[]>[
    false,false,false
  ]);
  private imageFiles = signal(<File[]|null[]>[null, null, null]);

  formUpdateItem = new FormGroup({
    categoryId: new FormControl<string>('', [Validators.required]),
    name: new FormControl<string>(this.item().name, [Validators.required]),
    about: new FormControl<string>(this.item().about, [Validators.required]),
    price: new FormControl<string>(String(this.item().price), [Validators.required]),
    stock: new FormControl<number>(this.item().stock, [Validators.required])
  });
  // Signal local para controlar a foto selecionada em destaque
  selectedImage = signal<string | null>(null);

  // Computed para garantir que sempre haja uma foto em exibição
  activePreviewImage = computed(() => {
    return this.selectedImage() || this.item()?.imagePath1 || null;
  });

  totalValueStocked = computed(() =>{
    return this.item().price * this.item().stock;
  })

  //Links temporarios para exibir as imagens
  inputImage1Selected = signal("");
  inputImage2Selected = signal("");
  inputImage3Selected = signal("");

  valuesOfitemIsDifferent():boolean{
    
    return (this.item().categoryId && (this.formUpdateItem.get('categoryId')?.value as string !== this.item().categoryId)) ||
          (this.formUpdateItem.get('name')?.value as string !== this.item().name) ||
          (this.formUpdateItem.get('about')?.value as string !== this.item().about) ||
          (this.formUpdateItem.get('price')?.value as string !== String(this.item().price)) ||
          (String(this.formUpdateItem.get('stock')?.value) !== String(this.item().stock)) ||
          (this.updateImagesItem()[0]===true || this.updateImagesItem()[1]===true || this.updateImagesItem()[2]===true)
  };
  
  constructor(private route: ActivatedRoute){
    
  }

  onFileChange(event: Event, slot: number) {
    const input = event.target as HTMLInputElement;
    
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const temporaryUrl = URL.createObjectURL(file);
      let updateImages = this.updateImagesItem();
      let imageFiles = this.imageFiles();

      if (slot === 1) {
        this.inputImage1Selected.set(temporaryUrl);
      } else if (slot === 2) {
        this.inputImage2Selected.set(temporaryUrl);
      } else if (slot === 3) {
        this.inputImage3Selected.set(temporaryUrl);
      }
      if(updateImages){
        updateImages[slot-1] = true;
        imageFiles[slot-1]  = file;
      }

      this.updateImagesItem.set(updateImages);
      this.imageFiles.set(imageFiles);
      
      this.selectedImage.set(temporaryUrl);
    }
  }

  removeImage(slot: number) {
    const currentItem = this.item();
    
    if (currentItem) {
      if (slot === 2) {
        this.inputImage2Selected.set("")
      } else if (slot === 3) {
        this.inputImage3Selected.set("")
      }
      let updateImages = this.updateImagesItem();
      let imageFiles = this.imageFiles();

      if(updateImages){
        updateImages[slot-1] = !updateImages[slot-1];
        imageFiles[slot-1] = null;
      }

      this.imageFiles.set(imageFiles);
      this.updateImagesItem.set(updateImages);
      this.selectedImage.set(null);


    }
  }

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
    imagePath3: null
  };

  ngOnInit(){
    this.helperService.setAtualPage(1)
    

    this.sellerService.getMyCatalog().subscribe((data) =>{
      if (data) {
        this.privateCatalogOfSeller.set(data);
      }
    })

    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.itemId.set(id);
      this.itemService.getItemById(id).subscribe({
        next:(data) =>{
          console.log(data);
          this.item.set(data);
          this.completeForm();
          this.completeImages();
          console.log(this.updateImagesItem());
          
        }
      })
    }

    // this.item.set(this.itemMock);
    // this.completeForm();
    // this.completeImages();
  }


  private generateUpdateItemRequest():UpdateItemRequestDTO{
    let updatesImages = null;
    if(this.updateImagesItem()[0] || this.updateImagesItem()[1] || this.updateImagesItem()[2])
      updatesImages = this.updateImagesItem()

    return {
      categoryId:this.formUpdateItem.get('categoryId')?.value as string,
      name:this.formUpdateItem.get('name')?.value as string,
      about:this.formUpdateItem.get('about')?.value as string,
      price:this.formUpdateItem.get('price')?.value as string,
      stock:this.formUpdateItem.get('stock')?.value as number,
      updateImages:updatesImages
    }
  }

  private generateFormDataUpateItem():FormData{
    const formData = new FormData();

    formData.append('data', new Blob([JSON.stringify(this.generateUpdateItemRequest())], { type: 'application/json' }));

    // 2. Adiciona as imagens correspondentes aos RequestParts do back-end
    if (this.imageFiles()[0]!==null) 
      formData.append("image1", this.imageFiles()[0] as File)

    if (this.imageFiles()[1]!==null) 
      formData.append("image2", this.imageFiles()[1] as File)

    if (this.imageFiles()[2]!==null) 
      formData.append("image3", this.imageFiles()[2] as File)
    
    
    return formData;
  }
  ngOnSubmit(){
    console.log("Images: ");
    
    console.log(this.generateFormDataUpateItem().get("image1"));
    console.log(this.generateFormDataUpateItem().get("image2"));
    console.log(this.generateFormDataUpateItem().get("image3"));

    console.log("Editar imagens: " + this.updateImagesItem());
    
    
    
    
    
    // console.log(this.updateImagesItem());
    // console.log("Pode enviar:" + this.valuesOfitemIsDifferent())

    // console.log("DATA");
    
    // for (const [chave, valor] of this.generateFormDataUpateItem().entries()) {
    //   console.log(chave, valor);
    // }

    if (this.valuesOfitemIsDifferent()) {
      if (this.itemId()!=="" && this.item()!==null) {
        this.itemService.updateItemById(this.itemId() as string, this.generateFormDataUpateItem()).subscribe({
          next:async () =>{
            await this.router.navigate(["/"]);
            await this.router.navigate(["/catalog/products"]);
          }
        })
      }
    }
    else{
      window.alert("Nenhum valor é diferente do atual")
      console.log(this.valuesOfitemIsDifferent());
      
    }
  }

  completeImages(){
    this.inputImage1Selected.set(this.item().imagePath1);

    if (this.item().imagePath2!==null) {
      this.inputImage2Selected.set(this.item().imagePath2 as string);
    }
    if (this.item().imagePath3!==null) {
      this.inputImage3Selected.set(this.item().imagePath3 as string);
    }
  }

  completeForm(){
    this.formUpdateItem.patchValue({
      categoryId: this.item().categoryId,
      name: this.item().name,
      about: this.item().about,
      price: String(this.item().price),
      stock: this.item().stock
    });
  }

  closeForm(){
    this.inputImage1Selected.set(this.item().imagePath1);
    if (this.item().imagePath2!==null) {
      this.inputImage2Selected.set(this.item().imagePath2 as string);
    }
    if (this.item().imagePath3!==null) {
      this.inputImage3Selected.set(this.item().imagePath3 as string);
    }
    this.selectedImage.set(this.item().imagePath1);
    this.formUpdateItemIsActive.set(false);
  }


}
