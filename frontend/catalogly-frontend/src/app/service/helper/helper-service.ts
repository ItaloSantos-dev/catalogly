import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HelperService {
  private atualPage = new BehaviorSubject<number>(0);
  atualPage$ = this.atualPage.asObservable();

  setAtualPage(page: number) {
    this.atualPage.next(page);
  }
  
}
