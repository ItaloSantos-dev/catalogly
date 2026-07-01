import { inject, Injectable } from '@angular/core';
import { CreateCouponRequestDTO } from '../../../types/coupon/create-coupon-request';
import { BackApi } from '../../api/back-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CouponService {

  private backApi = inject(BackApi);

  validateCouponRequest(request:CreateCouponRequestDTO):boolean{
    const amount = Number(request.amount);
    if (amount%1!==0) {
      return false  
    }
    return true;
  }


  createCoupon(data:CreateCouponRequestDTO):Observable<any>{
    data.amount = String(Number(data.amount)/100);
    return this.backApi.createCoupon(data);
  }

  deleteCouponById(id:string):Observable<void>{
    return this.backApi.deleteCouponById(id);
  }
}
