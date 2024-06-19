import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from "@angular/common/http";
import { AuthService } from "./auth.service";
import { FormsModule } from "@angular/forms";

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { AppComponent } from './app.component';
import { CustomerComponent } from './customer/customer.component';
import { VendingComponent } from './vending/vending.component';
import { StockComponent } from './stock/stock.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserComponent } from './user/user.component';

import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatSidenavModule} from "@angular/material/sidenav";
import { MatListItem, MatNavList} from "@angular/material/list";
import { MatCardModule } from "@angular/material/card";
import { MatDividerModule} from "@angular/material/divider";
import { MatTableModule} from "@angular/material/table";
import { MatPaginatorModule} from "@angular/material/paginator";
import { MatSortModule} from "@angular/material/sort";
import { MatInputModule} from "@angular/material/input";
import { MatGridListModule, MatGridTile} from "@angular/material/grid-list";

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
    FormsModule,
    HttpClientModule,
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
    provideAnimationsAsync(),
    AuthService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
