import { LoginResponseModel } from './../login/loginResponseModel';
import { Injectable } from '@angular/core';
import { SignupRequestModel } from '../sign-up/signupRequestModel';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';
import { map, tap } from 'rxjs/operators';
import { LoginRequestModel } from '../login/loginRequestModel';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  signup(signupRequestPayload: SignupRequestModel): Observable<any> {
    return this.http.post('http://localhost:3000/api/auth/signup', signupRequestPayload);
  }

  login(loginRequestModel: LoginRequestModel): Observable<boolean> {
    return this.http.post<LoginResponseModel>('http://localhost:3000/api/auth/login', loginRequestModel)
      .pipe(map(data => {
        this.localStorage.store('authenticationToken', data.acessToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        return true;
      }));
  }

  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }
    return this.http.post<LoginResponseModel>('http://localhost:3000/api/auth/refresh/token',
      refreshTokenPayload)
      .pipe(tap((response: { acessToken: any; }) => {
        this.localStorage.store('authenticationToken', response.acessToken);
      }));
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  logout(){
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
  }
}
