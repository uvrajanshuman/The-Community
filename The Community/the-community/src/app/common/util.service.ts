import { UtilResponseModel } from './utilResponseModel';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtilService {
  constructor(private http: HttpClient) { }

  getAllStats(): Observable<UtilResponseModel> {
    return this.http.get<UtilResponseModel>('http://localhost:3000/api/util/stats');
  }
}
