import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.css'
})
export class StockComponent implements OnInit, AfterViewInit {

public goods:any;
public dataSource:any;
public displayedColumns = ["id","brand","stock","batchDetails"]
@ViewChild(MatPaginator) paginator! : MatPaginator;
@ViewChild(MatSort) sort! :MatSort;

  constructor(private router : Router) {
  }

  ngOnInit() {
    this.goods = [];
    for (let i= 1; i < 70; i++) {
      this.goods.push(
        {
          id : i,
          brand : Math.random().toString(20),
          stock : ((Math.random() + 1) * 100 ).toPrecision(3),
        }
      )
    }
    this.dataSource = new MatTableDataSource(this.goods);
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  filterGoods(event: Event) {
    let value = (event.target as HTMLInputElement).value;
    this.dataSource.filter = value;
  }

  getGoods(vending:any) {
    this.router.navigateByUrl("/user")
  }
}
