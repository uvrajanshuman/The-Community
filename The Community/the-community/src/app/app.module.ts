import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './header/header.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import {NgxWebstorageModule} from 'ngx-webstorage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HomeComponent } from './home/home.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { PostTileComponent } from './common/post-tile/post-tile.component';
import { SideBarComponent } from './common/side-bar/side-bar.component';
import { ProductSideBarComponent } from './common/product-side-bar/product-side-bar.component';
import { VoteButtonComponent } from './common/vote-button/vote-button.component'
import { TokenInterceptor } from './auth/auth-interceptor';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { ListProductComponent } from './product/list-product/list-product.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { SearchComponent } from './search/search.component'


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignUpComponent,
    LoginComponent,
    HomeComponent,
    PostTileComponent,
    SideBarComponent,
    ProductSideBarComponent,
    VoteButtonComponent,
    CreateProductComponent,
    CreatePostComponent,
    ListProductComponent,
    ViewPostComponent,
    UserProfileComponent,
    SearchComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(),// ToastrModule added
    FontAwesomeModule,
    EditorModule
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }
],
  bootstrap: [AppComponent]
})
export class AppModule { }
