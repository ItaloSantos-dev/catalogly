import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequestDTO } from '../../../../../types/auth/login-request';
import { UserService } from '../../../../service/user/user-service';
import { AuthService } from '../../../../service/auth/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-login',
  imports: [ReactiveFormsModule],
  templateUrl: './user-login.html',
  styleUrl: './user-login.css',
})
export class UserLogin {

  private userService = inject(UserService);
  private authService = inject(AuthService);
  private router = inject(Router);

  // Sinalizador de carregamento simulado durante o envio
  isLoading = signal<boolean>(false);

  // Instanciação do FormGroup com as validações requeridas
  loginForm = new FormGroup({
    email: new FormControl('', [
      Validators.required, 
      Validators.email
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ])
  });


  private createLoginUserRequestDTO():LoginRequestDTO {
    return {
      email: this.loginForm.get('email')?.value as string,
      password: this.loginForm.get('password')?.value as string,
    }
  }

  // Atalhos para as validações no HTML
  get emailControl() { return this.loginForm.get('email'); }
  get passwordControl() { return this.loginForm.get('password'); }

  onSubmit() {
    this.userService.loginUser(this.createLoginUserRequestDTO()).subscribe({
      next:(data) =>{
        this.authService.setToken(data);
        this.router.navigate(['../']);
      }
    })
  }
}
