import { Injectable } from '@angular/core';
import {Order} from '../dto/Order';
import {OrderDetail} from '../dto/OrderDetail';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor() { }

  public getAllOrders() {
    const order1 = new Order();
    order1.id = 'OID001';
    order1.custId = 'C001';
    order1.date = new Date().toDateString();

    const order2 = new Order();
    order2.id = 'OID002';
    order2.custId = 'C004';
    order2.date = new Date().toDateString();

    const od2 = new OrderDetail();
    od2.order = order1;
    od2.code = 'Item001';
    od2.qty = 20;

    const od1 = new OrderDetail();
    od1.order = order2;
    od1.code = 'Item002';
    od1.qty = 100;

    const orders: OrderDetail[] = [];
    orders.push(od2);
    orders.push(od1);

    return orders;
  }

}
