import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {MatIcon, MatIconModule} from "@angular/material/icon";
import {MatMenu, MatMenuModule} from "@angular/material/menu";
import {MatDrawer, MatDrawerContainer, MatSidenav, MatSidenavModule} from "@angular/material/sidenav";
import {MatListItem, MatNavList} from "@angular/material/list";
import { CustomerComponent } from './customer/customer.component';
import { VendingComponent } from './vending/vending.component';
import { StockComponent } from './stock/stock.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserComponent } from './user/user.component';
import {MatCard, MatCardHeader, MatCardModule, MatCardTitle} from "@angular/material/card";
import {MatDivider, MatDividerModule} from "@angular/material/divider";
import {MatTable, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatGridList, MatGridListModule, MatGridTile} from "@angular/material/grid-list";

@NgModule({
  declarations: [
    AppComponent,
    CustomerComponent,
    VendingComponent,
    StockComponent,
    LoginComponent,
    DashboardComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListItem,
    MatNavList,
    MatCardModule,
    MatDividerModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatGridListModule,
    MatGridTile
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
