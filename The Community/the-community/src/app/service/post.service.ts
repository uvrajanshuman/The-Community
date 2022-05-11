import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostModel } from '../common/postModel';
import { CreatePostModel } from '../post/createPostModel';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>('http://localhost:3000/api/posts');
  }

  createPost(postrequestJSON: CreatePostModel): Observable<any> {
    return this.http.post('http://localhost:3000/api/posts', postrequestJSON);
  }

  getPost(id: number): Observable<PostModel> {
    return this.http.get<PostModel>('http://localhost:3000/api/posts/' + id);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.http.get<PostModel[]>('http://localhost:3000/api/posts/by-user/' + name);
  }
}
