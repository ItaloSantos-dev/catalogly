import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { LoginRequestDTO } from '../../../types/auth/login-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private  backApi = inject(BackApi);

  loginUser(data:LoginRequestDTO):Observable<string>{
    return this.backApi.login(data);
  }
}
