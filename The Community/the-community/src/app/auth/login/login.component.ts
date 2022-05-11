import { LoginRequestModel } from './loginRequestModel';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginRequestModel!: LoginRequestModel;
  registerSuccessMessage!: string;
  isError!: boolean;

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute,
    private router: Router, private toastr: ToastrService) {
    this.loginRequestModel = {
      username: '',
      password: ''
    };
    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params['registered'] !== undefined && params['registered'] === 'true') {
          this.toastr.success('Signup Successful');
          this.registerSuccessMessage = 'Login';
        }
      });
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
    
  }

  login() {
    this.loginRequestModel.username = this.loginForm.get('username')?.value;
    this.loginRequestModel.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginRequestModel).subscribe(data => {
      if (data) {
        this.isError = false;
        this.router.navigateByUrl('/').then(() => {
          window.location.reload();
        });
        this.toastr.success("Success");
      } else {
        console.log("error login")
        this.isError = true;
      }
    },(error)=>{
      console.log("error login")
        this.isError = true;
    });
  }
}
