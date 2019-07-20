import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { MainComponent } from './main/main.component';
import { CustomerComponent } from './customer/customer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ItemComponent } from './item/item.component';
import { OrderComponent } from './order/order.component';
import {FormsModule} from '@angular/forms';
import {AlertService} from './core/services/alert.service';
import {ToastrModule} from 'ng6-toastr-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CustomerService} from './core/services/customer.service';
import {ItemService} from './core/services/item.service';
import {OrderService} from './core/services/order.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SideBarComponent,
    MainComponent,
    CustomerComponent,
    DashboardComponent,
    ItemComponent,
    OrderComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    HttpClientModule
  ],
  providers: [
    AlertService,
    CustomerService,
    ItemService,
    OrderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
