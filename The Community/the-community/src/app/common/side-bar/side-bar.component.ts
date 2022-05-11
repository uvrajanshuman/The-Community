import { UtilResponseModel } from './../utilResponseModel';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { UtilService } from './../util.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {faUsers,faComments, faQuestion, faCommentDots} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  resp!: UtilResponseModel;

  constructor(private router: Router,private utilService: UtilService) {
    this.utilService.getAllStats().subscribe(post => {
      this.resp = post;
      console.log(this.resp)
    }
    , error=>{
      console.log(error);
    });

   }

  ngOnInit() {
  }

  goToCreatePost() {
    this.router.navigateByUrl('/create-post');
  }

  goToCreateSubreddit() {
    this.router.navigateByUrl('/create-product');
  }

}
