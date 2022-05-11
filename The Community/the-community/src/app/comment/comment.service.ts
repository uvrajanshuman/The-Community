import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentModel } from './commentModel';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  
  constructor(private httpClient: HttpClient) { }

  getAllCommentsForPost(postId: number): Observable<CommentModel[]> {
    return this.httpClient.get<CommentModel[]>('http://localhost:3000/api/comments/by-post/' + postId);
  }

  postComment(commentPayload: CommentModel): Observable<any> {
    return this.httpClient.post<any>('http://localhost:3000/api/comments/', commentPayload);
  }

  getAllCommentsByUser(username: string) {
    return this.httpClient.get<CommentModel[]>('http://localhost:3000/api/comments/by-user/' + username);
  }

  markAnswer(postId: number): Observable<any> {
    return this.httpClient.get<any>('http://localhost:3000/api/comments/mark-answered/' + postId);
  }
}
