import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductResponseModel } from './product-response';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Array<ProductResponseModel>> {
    return this.http.get<Array<ProductResponseModel>>('http://localhost:3000/api/product');
  }

  createProduct(productModel: ProductResponseModel): Observable<ProductResponseModel> {
    return this.http.post<ProductResponseModel>('http://localhost:3000/api/product',
      productModel);
  }
}
