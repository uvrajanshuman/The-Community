import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { ProductResponseModel } from '../product-response';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {
  products!: Array<ProductResponseModel>;
  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.productService.getAllProducts().subscribe(data => {
      this.products = data;
    }, error => {
      throwError(error);
    })
  }
}
