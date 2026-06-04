import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreateSellerRequestDTO } from '../../../../../types/seller/create-seller-request';
import { RegisterRequestDTO } from '../../../../../types/auth/register-request';
import { SellerService } from '../../../../service/seller/seller-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-register-seller',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register-seller.html',
  styleUrl: './register-seller.css',
})
export class RegisterSeller {

  private sellerService = inject(SellerService);

  formRegisterSeller = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    cpf: new FormControl('', [Validators.required]),
    phone: new FormControl('', [Validators.required]),
  })

  private createRegisterSellerRequestDTO():CreateSellerRequestDTO{
    const userData:RegisterRequestDTO ={
      firstName:this.formRegisterSeller.get('firstName')?.value as string,
      lastName:this.formRegisterSeller.get('lastName')?.value as string,
      email:this.formRegisterSeller.get('email')?.value as string,
      password:this.formRegisterSeller.get('password')?.value as string,
      cpf:this.formRegisterSeller.get('cpf')?.value as string,
    }

    return {
      userData,
      phone:this.formRegisterSeller.get('phone')?.value as string
    }
  } 

  ngSubmit(){
    this.sellerService.registerSeller(this.createRegisterSellerRequestDTO()).subscribe({
      next:(response)=>{
        console.log(response);
      },
      error:(error)=>{
        console.error(error);
      }
    })
  }
}
