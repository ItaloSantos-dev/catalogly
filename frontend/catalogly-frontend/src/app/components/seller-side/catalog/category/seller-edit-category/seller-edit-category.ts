import { Component, inject, signal } from '@angular/core';
import { CategoryRespondeDTO } from '../../../../../../types/category/category-response';
import { CategoryService } from '../../../../../service/category/category-service';
import { ActivatedRoute, Route, Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpdateCategoryrequestDTO } from '../../../../../../types/category/update-category-request';

@Component({
  selector: 'app-seller-edit-category',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './seller-edit-category.html',
  styleUrl: './seller-edit-category.css',
})
export class SellerEditCategory {
  category = signal(<CategoryRespondeDTO>{});
  categoryService = inject(CategoryService);
  private categoryId = signal("");
  private router = inject(Router);

  formEditCategory = new FormGroup({
    name: new FormControl('', { validators: [Validators.required] }),
    description: new FormControl('', { validators: [Validators.required] })
  });

  constructor(private route:ActivatedRoute){

  }

  completeFormValues(){
    this.formEditCategory.patchValue({
      name:this.category().name,
      description:this.category().description
    })
  }

  createUpdateCategoryRequest():UpdateCategoryrequestDTO{
    return{
      name:this.formEditCategory.get('name')?.value as string,
      description: this.formEditCategory.get('description')?.value as string
    }
  }

  ngOnInit(){
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.categoryId.set(id);
      this.categoryService.getCategoryById(id).subscribe({
        next:(data)=>{
          this.category.set(data);
          this.completeFormValues();
        }
      })
    }
  }

  ngOnSubmit(){
    this.categoryService.updateCategoryById(this.categoryId(), this.createUpdateCategoryRequest()).subscribe({
      next:async () =>{
        await this.router.navigate(['/']);
        await this.router.navigate(['/catalog/categories']);
      }
    })
  }

}
