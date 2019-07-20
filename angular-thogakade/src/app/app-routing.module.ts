import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {MainComponent} from './main/main.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CustomerComponent} from './customer/customer.component';
import {ItemComponent} from './item/item.component';
import {OrderComponent} from './order/order.component';

const routes: Routes = [
  {
    path: 'app', component: MainComponent, children: [
      // {path: '*', redirectTo: 'dashboard'},
      {path: 'dashboard', component: DashboardComponent},
      {path: 'customer', component: CustomerComponent},
      {path: 'item', component: ItemComponent},
      {path: 'order', component: OrderComponent}
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
