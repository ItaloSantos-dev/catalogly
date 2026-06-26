import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LinkItensAndSupplier } from './link-itens-and-supplier/link-itens-and-supplier';
import { CatalogPrivateResponseDTO } from '../../../../../../types/catalog/catalog-private-response';
import { SellerService } from '../../../../../service/seller/seller-service';
import { HelperService } from '../../../../../service/helper/helper-service';
import { SupplierService } from '../../../../../service/supplier/supplier-service';
import { ContactSupplierType } from '../../../../../../types/enums/contact-supplier-type';
import { Router } from '@angular/router';

@Component({
  selector: 'app-seller-create-supplier',
  imports: [ReactiveFormsModule, LinkItensAndSupplier],
  templateUrl: './seller-create-supplier.html',
  styleUrl: './seller-create-supplier.css',
})
export class SellerCreateSupplier {

  private sellerService = inject(SellerService);
  private helperService = inject(HelperService);
  private supplierService = inject(SupplierService);
  private router = inject(Router);

  catalogPrivateOfSeller = signal(<CatalogPrivateResponseDTO>{});
  modalIsOpen = signal(false);
  itemsSelected = signal(<string[]> [])

  

  formCreateSupplier = new FormGroup({
    name: new FormControl('', { validators: [Validators.required] }),
    cnpj: new FormControl('', { validators: [Validators.required] }), // Dica: adicione sua diretiva/pipe de máscara aqui
    contactSupplierType: new FormControl<'EMAIL' | 'PHONE'>('EMAIL', { validators: [Validators.required] }),
    contactValue: new FormControl('', { validators: [Validators.required] }),
  });

  // Getter simples para monitorar o tipo de contato no HTML
  get currentContactType() {
    return this.formCreateSupplier.get('contactSupplierType')?.value;
  }

  closeModal(){
    this.modalIsOpen.set(false);
  }

  ngOnInit(){
    this.helperService.setAtualPage(4)
    this.sellerService.getMyCatalog().subscribe((data) =>{
      this.catalogPrivateOfSeller.set(data);
    })
  }

  receiveItens(itens:string[]){
    this.itemsSelected.set(itens);
    console.log(itens);
    
  }

  createSupplierRequest(){
    let  itens;
    this.itemsSelected().length>0? itens = this.itemsSelected() : itens = null;

    console.log(this.itemsSelected());
    return {
      name: this.formCreateSupplier.get('name')?.value as string,
      cnpj: this.formCreateSupplier.get('cnpj')?.value as string,
      contactSupplierType: this.formCreateSupplier.get('contactSupplierType')?.value as ContactSupplierType,
      contactValue: this.formCreateSupplier.get('contactSupplierType')?.value as string,
      items: itens
    }
  }

  ngOnSubmit(){
    const request = this.createSupplierRequest();
    console.log(request);
    
    this.supplierService.createSupplier(request).subscribe({
      next: async () => {
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/suppliers']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

}
