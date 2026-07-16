import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../../service/user/user-service';
import { RegisterRequestDTO } from '../../../../../types/auth/register-request';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-register',
  imports: [ReactiveFormsModule],
  templateUrl: './user-register.html',
  styleUrl: './user-register.css',
})
export class UserRegister {
  private userService = inject(UserService);
  private router = inject(Router);
  // Controle de carregamento simulado durante o cadastro
  isLoading = signal<boolean>(false);

  // Instanciação do formulário de registro com validações rígidas
  registerForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(2)
    ]),
    lastName: new FormControl('', [
      Validators.required
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
    cpf: new FormControl('', [
      Validators.required,
      // Valida tanto apenas números (11 dígitos) quanto formato com pontos e traço
      Validators.pattern(/^\d{3}\.?\d{3}\.?\d{3}-?\d{2}$/)
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ])
  });

  // Getters para reduzir a verbosidade do HTML
  get firstNameControl() { return this.registerForm.get('firstName'); }
  get lastNameControl() { return this.registerForm.get('lastName'); }
  get emailControl() { return this.registerForm.get('email'); }
  get cpfControl() { return this.registerForm.get('cpf'); }
  get passwordControl() { return this.registerForm.get('password'); }

  private createRegisterUserRequestDTO():RegisterRequestDTO{
      return{
        firstName:this.registerForm.get('firstName')?.value as string,
        lastName:this.registerForm.get('lastName')?.value as string,
        email:this.registerForm.get('email')?.value as string,
        password:this.registerForm.get('password')?.value as string,
        cpf:this.registerForm.get('cpf')?.value as string,
      }
    } 

  onSubmit() {
    this.userService.registerUser(this.createRegisterUserRequestDTO()).subscribe({
      next:(data)=>{
        this.router.navigate(['/catalogly', 'user', 'login']);
      }
    })
  }
}
