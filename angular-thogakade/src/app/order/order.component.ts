import { Component, OnInit } from '@angular/core';
import {OrderService} from '../core/services/order.service';
import {AlertService} from '../core/services/alert.service';
import {OrderDetail} from '../core/dto/OrderDetail';
import {Order} from '../core/dto/Order';
import {CustomerService} from '../core/services/customer.service';
import {ItemService} from '../core/services/item.service';
import {Customer} from '../core/dto/Customer';
import {Item} from '../core/dto/Item';
import {PlaceOrder} from '../core/dto/PlaceOrder';
declare var $: any;

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  orders: OrderDetail[] = [];
  orderDetail: OrderDetail = new OrderDetail();
  order: Order = new Order();
  customers: Customer[] = [];
  items: Item[] = [];
  selectedCustomer: Customer = null;
  customer = '';
  item: Item = new Item();
  placeOrder: PlaceOrder[] = [];
  qty: number = null;
  subTotal: number = Number(0.00);

  constructor(private orderService: OrderService, private alertService: AlertService,
              private customerService: CustomerService, private itemService: ItemService) {
    this.getAllOrders();
    this.getAllItems();
    this.getAllCustomers();
  }

  ngOnInit() {
    $('.ui.dropdown')
      .dropdown({
        allowAdditions: false
      });
  }

  openModalPlaceOrder() {
    $('#orderModel').modal('show');
  }

  onAdd() {
    const placed = new PlaceOrder();
    placed.item = this.item;
    placed.qty = this.qty;
    placed.total = this.item.price * this.qty;
    this.placeOrder.push(placed);
    this.subTotal += placed.total;
    this.clearItem();
    this.qty = null;
  }

  onChan() {
    this.customer = this.selectedCustomer.name;
  }

  private getAllOrders() {
    this.orders = this.orderService.getAllOrders();
  }

  private clearItem() {
    this.item = new Item();
  }

  private getAllCustomers() {
    this.customerService.getAllCustomers().subscribe(data => {
      this.customers = data;
    });
  }

  private getAllItems() {
    // this.items = this.itemService.getAllItems();
  }

  onPlaceOrderEdit(placedOrder) {

  }

  onPlaceOrderDelete(placedOrder) {

  }

  viewOrder(order) {
    console.log(order);
  }

  deleteOrder(order) {
    this.orders.splice(order, 1);
  }
}
