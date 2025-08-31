import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-vending',
  templateUrl: './vending.component.html',
  styleUrl: './vending.component.css'
})
export class VendingComponent implements OnInit {
  public tray: any;
  public dataSource: any;

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.tray = [];
    for (let i = 0; i < 8; i++) {

      this.tray.push(
        {location: 'A1', cols: 1, rows: 1, image: "[image-url]", color: 'lightblue'},
        {location: 'A2', cols: 1, rows: 1, image: "[image-url]", color: 'lightgreen'},
        {location: 'A3', cols: 1, rows: 1, image: "[image-url]", color: 'lightpink'},
        {location: 'A4', cols: 1, rows: 1, image: "[image-url]", color: '#DDBDF1'}
      )
    }

    this.dataSource = new MatTableDataSource(this.tray);
    console.log(this.tray)
  }

  getGoods(vending: any) {
    this.router.navigateByUrl("/user")
  }
}
