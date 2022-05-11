import { SignupRequestModel } from './signupRequestModel';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signupForm!: FormGroup;
  signupRequestModel!: SignupRequestModel;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {
    this.signupRequestModel = {
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit() {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  signup() {
    this.signupRequestModel.username = this.signupForm.get('username')?.value;
    this.signupRequestModel.email = this.signupForm.get('email')?.value;
    this.signupRequestModel.password = this.signupForm.get('password')?.value;

    this.authService.signup(this.signupRequestModel).subscribe(() => {
      console.log('Signup Successful');
      //navigating to login page and adding the query param for signup success;
      this.router.navigate(['/login'], { queryParams: { registered: 'true' } });
    }, () => {
      console.log('Signup Failed');
      this.toastr.error('Registration Failed! Please try again');
    });
  }


}
