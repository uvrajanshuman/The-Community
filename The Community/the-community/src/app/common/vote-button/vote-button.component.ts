import { VoteModel } from './voteModel';
import { Icon, IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../postModel';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { VoteService } from '../vote.service';
import { AuthService } from 'src/app/auth/service/auth.service';
import { PostService } from 'src/app/service/post.service';
import { ToastrService } from 'ngx-toastr';
import { VoteType } from './voteType';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  @Input() post!: PostModel;
  votePayload: VoteModel;
  faArrowUp = faArrowUp as IconDefinition;
  faArrowDown = faArrowDown as IconDefinition;
  upvoteColor?: string;
  downvoteColor?: string;

  constructor(private voteService: VoteService,
    private authService: AuthService,
    private postService: PostService, private toastr: ToastrService) {

    this.votePayload = {
      voteType: undefined,
      postId: undefined
    }
  }

  ngOnInit(): void {
    this.updateVoteDetails();
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
    this.downvoteColor = '';
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
    this.upvoteColor = '';
  }

  private vote() {
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(() => {
      this.updateVoteDetails();
    }, error => {
      this.toastr.error(error.error.message);
      throwError(error);
    });
  }

  private updateVoteDetails() {
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    });
  }
}
