import { inject, Injectable } from '@angular/core';
import { BackApi } from '../../api/back-api';
import { LoginRequestDTO } from '../../../types/auth/login-request';
import { BehaviorSubject, Observable } from 'rxjs';
import { RegisterRequestDTO } from '../../../types/auth/register-request';
import { UserResponseDTO } from '../../../types/user/user-response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private  backApi = inject(BackApi);




  loginUser(data:LoginRequestDTO):Observable<string>{
    return this.backApi.login(data);
  }

  registerUser(data:RegisterRequestDTO):Observable<UserResponseDTO>{
    return this.backApi.registerUser(data);
  }
}
