import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductResponseModel } from '../product-response';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {
  createProductForm: FormGroup;
  productModel!: ProductResponseModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private productService: ProductService) {
    this.createProductForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.productModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit() {
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  createProduct() {
    this.productModel.name = this.createProductForm.get('title')?.value;
    this.productModel.description = this.createProductForm.get('description')?.value;
    this.productService.createProduct(this.productModel).subscribe(data => {
      this.router.navigateByUrl('/list-product');
    }, error => {
      console.log('Error occurred');
    })
  }

}
