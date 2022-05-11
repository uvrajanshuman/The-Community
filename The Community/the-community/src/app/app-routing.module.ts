import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { ListProductComponent } from './product/list-product/list-product.component';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'create-post', component: CreatePostComponent,canActivate: [AuthGuard] },
  { path: 'create-product', component: CreateProductComponent,canActivate: [AuthGuard] },
  { path: 'view-post/:id', component: ViewPostComponent,canActivate: [AuthGuard] },
  { path: 'user-profile/:name', component: UserProfileComponent,canActivate: [AuthGuard]},
  { path: 'list-product', component: ListProductComponent },
  { path: 'login', component: LoginComponent }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
