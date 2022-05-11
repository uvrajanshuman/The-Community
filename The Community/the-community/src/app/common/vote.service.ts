import { VoteModel } from './vote-button/voteModel';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  constructor(private http: HttpClient) { }

  vote(votePayload: VoteModel): Observable<any> {
    return this.http.post('http://localhost:3000/api/votes/', votePayload);
  }
}
