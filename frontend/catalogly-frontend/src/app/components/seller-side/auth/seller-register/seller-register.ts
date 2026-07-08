import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CreateSellerRequestDTO } from '../../../../../types/seller/create-seller-request';
import { RegisterRequestDTO } from '../../../../../types/auth/register-request';
import { SellerService } from '../../../../service/seller/seller-service';

@Component({
  selector: 'app-seller-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './seller-register.html',
  styleUrl: './seller-register.css',
})
export class SellerRegister {
  private sellerService = inject(SellerService);

  private router = inject(Router);

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
    localStorage.clear();
    this.sellerService.registerSeller(this.createRegisterSellerRequestDTO()).subscribe({
      next:(response)=>{
        this.router.navigate(['/catalog', 'dashboard'])
      },
      error:(error)=>{
        console.error(error);
      }
    })
  }
}
