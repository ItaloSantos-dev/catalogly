import { Component, inject } from '@angular/core';
import { SellerService } from '../../../../service/seller/seller-service';
import { AuthService } from '../../../../service/auth/auth-service';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequestDTO } from '../../../../../types/auth/login-request';

@Component({
  selector: 'app-seller-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './seller-login.html',
  styleUrl: './seller-login.css',
})
export class SellerLogin {
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
    localStorage.clear();
    this.sellerService.loginSeller(this.createLoginSellerRequestDTO()).subscribe({
      next: (token) => {
        this.authService.setToken(token);
        this.router.navigate(['/catalog/dashboard']);
      },
      error: (err) => {
        window.alert("Deu ruin");
      }
    });
  }
}
