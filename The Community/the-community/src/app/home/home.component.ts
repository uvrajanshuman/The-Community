import { Component, OnInit } from '@angular/core';
import { PostService } from '../service/post.service';
import { PostModel } from '../common/postModel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts: Array<PostModel> = [];

  constructor(private postService: PostService) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts = post;
    }
    , error=>{
      console.log(error);
    });
  }

  ngOnInit(): void {
    
  }

}
