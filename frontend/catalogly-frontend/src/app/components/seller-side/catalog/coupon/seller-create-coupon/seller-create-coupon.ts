import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreateCouponRequestDTO } from '../../../../../../types/coupon/create-coupon-request';
import { CouponService } from '../../../../../service/coupon/coupon-service';
import { Router, RouterLink } from '@angular/router';
import { HelperService } from '../../../../../service/helper/helper-service';

@Component({
  selector: 'app-seller-create-coupon',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './seller-create-coupon.html',
  styleUrl: './seller-create-coupon.css',
})
export class SellerCreateCoupon {

  private couponService = inject(CouponService);
  private router = inject(Router);
  private helperService = inject(HelperService);

  formCreateCoupon = new FormGroup({
    slug: new FormControl('', { validators: [Validators.required, Validators.minLength(3)] }),
    amount: new FormControl<number | null>(null, { validators: [Validators.required, Validators.min(1)] }),
    amountMinimum: new FormControl<number | null>(null, { validators: [Validators.min(0)] }),
    amountDiscountMaximum: new FormControl<number | null>(null, { validators: [Validators.min(0)] })
  });


  private createCouponRequest(): CreateCouponRequestDTO{
    return {
      slug: this.formCreateCoupon.get('slug')?.value as string,
      amount: String(this.formCreateCoupon.get('amount')?.value),
      amountMinimum: this.formCreateCoupon.get('amountMinimum')?.value?.toString() ?? null,
      amountDiscountMaximum: this.formCreateCoupon.get('amountDiscountMaximum')?.value?.toString() ?? null
    };
  }

  ngOnInit(){
    this.helperService.setAtualPage(4)
  }

  ngOnSubmit(){
    
    const request = this.createCouponRequest();
    const requestIsValid = this.couponService.validateCouponRequest(request);

    if (requestIsValid) {
      this.couponService.createCoupon(request).subscribe({
        next:async() =>{
          await this.router.navigate(['/'])
          await this.router.navigate(['/catalog/coupons'])
        },
          error: (err) => {
          console.error(err);
        }
      })
    }
    else{
      this.formCreateCoupon.get('amount')?.setErrors({
        invalidAmount: true
      });
    }
    
    
  }
}
