import { ProductService } from './../../product/product.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductResponseModel } from 'src/app/product/product-response';
import { PostService } from 'src/app/service/post.service';
import { CreatePostModel } from '../createPostModel';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  createPostForm!: FormGroup;
  postrequestJSON!: CreatePostModel;
  products!: Array<ProductResponseModel>;

  constructor(private router: Router, private postService: PostService,
    private productService: ProductService) {
    this.postrequestJSON = {
      postTitle: '',
      url: '',
      description: '',
      productId : 0
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      productName: new FormControl('', Validators.required),
      url: new FormControl(''),
      description: new FormControl('', Validators.required),
    });
    this.productService.getAllProducts().subscribe((data) => {
      this.products = data;
    }, error => {
      throwError(error);
    });
  }

  createPost() {
    this.postrequestJSON.postTitle = this.createPostForm.get('postName')?.value;
    console.log( this.createPostForm.get('productName'));
    this.postrequestJSON.productId = this.createPostForm.get('productName')?.value;
    this.postrequestJSON.url = this.createPostForm.get('url')?.value;
    this.postrequestJSON.description = this.createPostForm.get('description')?.value;

    this.postService.createPost(this.postrequestJSON).subscribe((data) => {
      this.router.navigateByUrl('/');
    }, error => {
      throwError(error);
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }
}
