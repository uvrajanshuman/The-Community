import { AuthService } from './../../auth/service/auth.service';
import { CommentModel } from './../../comment/commentModel';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PostModel } from 'src/app/common/postModel';
import { PostService } from 'src/app/service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from 'src/app/comment/comment.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  
  postId!: number;
  post!: PostModel;
  commentForm: FormGroup;
  commentRequestJSON!: CommentModel;
  comments?: CommentModel[];

  amAuthor: boolean = false;

  constructor(private postService: PostService, private activeRoute: ActivatedRoute, private authService: AuthService,
    private commentService: CommentService, private router: Router) {
    this.postId = this.activeRoute.snapshot.params['id'];

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });

    this.commentRequestJSON = {
      id:1,
      text: '',
      isAnswer: false,
      userName: authService.getUserName(),
      postId: this.postId
    };
    
  }

  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
    
  }

  postComment() {
    this.commentRequestJSON.text = this.commentForm.get('text')?.value;
    this.commentService.postComment(this.commentRequestJSON).subscribe(data => {
      //clearing value for next comment
      this.commentForm.get('text')?.setValue('');
      this.getCommentsForPost();
    }, error => {
      throwError(error);
    })
  }

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    }, error => {
      throwError(error);
    });
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe(data => {
      this.comments = data;
    }, error => {
      throwError(error);
    });
  }

  public markAnswer(comment:CommentModel){
    console.log(this.post.userName===this.authService.getUserName());
    
    if(this.post.userName==this.authService.getUserName()){
      this.amAuthor = true;
    this.commentService.markAnswer(comment.id).subscribe((data)=>{
      this.getCommentsForPost();
    }, error => {
      throwError(error);
    })}
    else{
      alert("Only author can mark answer");
    }
  }


}
