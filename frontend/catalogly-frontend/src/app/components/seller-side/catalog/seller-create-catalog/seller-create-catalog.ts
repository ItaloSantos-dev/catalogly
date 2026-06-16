import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../../../service/auth/auth-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreateCatalogRequestDTO } from '../../../../../types/catalog/create-catalog-request';
import { CatalogService } from '../../../../service/catalog/catalog-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-seller-create-catalog',
  imports: [ReactiveFormsModule],
  templateUrl: './seller-create-catalog.html',
  styleUrl: './seller-create-catalog.css',
})
export class SellerCreateCatalog {
  private authService = inject(AuthService);
  sellerLogged = signal(false);
  timeRest = signal(5);
  private catalogService = inject(CatalogService);
  router = inject(Router);

  formCreateCatalog = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(4)]),
    slug: new FormControl('', [Validators.required, Validators.minLength(4)]),
    slogan: new FormControl('', [Validators.required, Validators.minLength(4)]),
    about: new FormControl('', [Validators.required, Validators.minLength(4)]),
    fisicAddress: new FormControl('', [Validators.required, Validators.minLength(4)]),
    phone: new FormControl('', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]),
  });

  imageIconPath = signal('');
  imageBannerPath = signal('');

  iconFile!: File;
  bannerFile!: File;

  onFileSelected(event: Event, control:number) {
    const input = event.target as HTMLInputElement;

    //ImageIcon
    if (input.files?.length) {
      const file = input.files[0];

      if (control===0) {
        this.imageIconPath.set(URL.createObjectURL(file))
        this.iconFile = file;
      }
      else{
        this.bannerFile = file;
        this.imageBannerPath.set(URL.createObjectURL(file))
      }

      console.log(file.name);
      
    }

  }



  private createCatalogRequest():CreateCatalogRequestDTO{
    return{
      name: this.formCreateCatalog.get('name')?.value as string,
      slug: this.formCreateCatalog.get('slug')?.value as string,
      slogan: this.formCreateCatalog.get('slogan')?.value as string,
      about: this.formCreateCatalog.get('about')?.value as string,
      fisicAddress: this.formCreateCatalog.get('fisicAddress')?.value as string,
      phone: this.formCreateCatalog.get('phone')?.value as string,
    }
  }

  private createCatalogFormData():FormData{
    if (!this.bannerFile || !this.iconFile) {
      window.alert('Selecione as imagens pendentes');
    }
    const createCatalogFormData = new FormData();

    createCatalogFormData.append(
      'data',
      new Blob(
        [JSON.stringify(this.createCatalogRequest())],
        {type:'application/json'}
      )
    )

    createCatalogFormData.append('imageIconUrl', this.iconFile)
    createCatalogFormData.append('imageBannerUrl', this.bannerFile)

    return createCatalogFormData;
  }

  



  ngOnSubmit(){
    this.catalogService.createCatalog(this.createCatalogFormData()).subscribe({
      next: async () => {
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/dashboard']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  ngOnInit(){
    if(!this.authService.getToken()){
      const interval = setInterval(() => {
        this.timeRest.update(value => value - 1);

        if(this.timeRest() <= 0){
          clearInterval(interval);
          window.location.href = '/seller/login';
        }
      }, 1000
    )
    }
    else{
      this.sellerLogged.set(true);
    }

  }
}
