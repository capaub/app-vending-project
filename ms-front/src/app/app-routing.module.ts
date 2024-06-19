import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";
import {StockComponent} from "./stock/stock.component";
import {UserComponent} from "./user/user.component";
import {VendingComponent} from "./vending/vending.component";
import { authGuard } from "./auth.guard"

const routes: Routes = [
  {path : "customer", component : CustomerComponent},
  {path : "dashboard", component : DashboardComponent},
  {path : "login", component : LoginComponent},
  {path : "stock", component : StockComponent, canActivate: [authGuard] },
  {path : "user", component : UserComponent},
  {path : "vending", component : VendingComponent},
  {path : "", redirectTo: "/login", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
