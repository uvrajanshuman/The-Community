import { ProductService } from './../../product/product.service';
import { ProductResponseModel } from './../../product/product-response';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-side-bar',
  templateUrl: './product-side-bar.component.html',
  styleUrls: ['./product-side-bar.component.css']
})
export class ProductSideBarComponent implements OnInit {
  products: Array<ProductResponseModel> = [];
  displayViewAll!: boolean;

  constructor(private productService: ProductService) {
    this.productService.getAllProducts().subscribe(data => {
      if (data.length > 3) {
        this.products = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.products = data;
      }
    });
  }

  ngOnInit(): void { }

}
