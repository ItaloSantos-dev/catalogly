import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../../../../service/auth/auth-service';

@Component({
  selector: 'app-user-menu',
  imports: [],
  templateUrl: './user-menu.html',
  styleUrl: './user-menu.css',
})
export class UserMenu {
  private authService = inject(AuthService);

  // Controla se o menu lateral está encolhido ou expandido
  isCollapsed = signal<boolean>(false);

  // Controla se o usuário está logado ou não (pode alterar para testar)
  isLoggedIn = signal<boolean>(false);

  // Função simples para abrir/fechar o menu
  toggleMenu() {
    this.isCollapsed.update(state => !state);
  }

  ngOnInit(){
    const token = this.authService.getToken;
    if (token ==null) {
      this.isLoggedIn.set(true);
    }
  }
}
