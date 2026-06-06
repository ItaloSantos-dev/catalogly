import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { LoginRequestDTO } from '../../../../../types/auth/login-request';
import { SellerService } from '../../../../service/seller/seller-service';
import { AuthService } from '../../../../service/auth/auth-service';

@Component({
  selector: 'app-login-seller',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login-seller.html',
  styleUrl: './login-seller.css',
})
export class LoginSeller {
  private sellerService = inject(SellerService);
  private authService = inject(AuthService);
  private router = inject(Router);

  formLoginSeller = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });

  private createLoginSellerRequestDTO():LoginRequestDTO {
    return {
      email: this.formLoginSeller.get('email')?.value as string,
      password: this.formLoginSeller.get('password')?.value as string,
    }
  }

  ngOnSubmit(){
    this.sellerService.loginSeller(this.createLoginSellerRequestDTO()).subscribe({
      next: (token) => {
        this.authService.setToken(token);
        this.router.navigate(['/catalog/dashboard']);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
