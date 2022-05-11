import { UtilService } from './../common/util.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  public totalItem : number = 0;
  public searchTerm !: string;
  constructor(private UtilService : UtilService) { }

  ngOnInit(): void {
   
  }
  search(event:any){
    this.searchTerm = (event.target as HTMLInputElement).value;
    console.log(this.searchTerm);
    // this.cartService.search.(searchTerm);
  }

}
