import { Component, OnInit } from '@angular/core';
import {Customer} from '../core/dto/Customer';
import {AlertService} from '../core/services/alert.service';
import {CustomerService} from '../core/services/customer.service';
declare var $: any;

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  customers: Customer[] = [];
  id: string ;
  name: string ;
  address: string ;
  salary: number ;
  deleteCustomer: Customer ;
  isEdit = false;
  originalData: Customer[] = [];


  constructor(private alertService: AlertService, private customerService: CustomerService) {}

  ngOnInit() {
    this.getAllCustomers();
  }

  private getAllCustomers() {
    this.customerService.getAllCustomers().subscribe(data => {
      this.originalData = data;
      this.customers = data;
    });
  }

  // there is a issue with onEdit(). customer array should be reloaded
  onEdit(customer) {
    this.id = customer.id;
    this.name = customer.name;
    this.address = customer.address;
    this.salary = customer.salary;
    this.isEdit = true;
    this.customers.splice(this.customers.indexOf(customer), 1);
  }

  onDelete(cust) {
    this.deleteCustomer = cust;
    $('#deleteCustomerModel').modal('show');
  }

  onContinueDelete() {
    this.customerService.deleteCustomer(this.deleteCustomer).subscribe(data => {
      if (data) {
        this.alertService.showToaster('Customer is Deleted', 'SUCCESS');
        this.onClear();
      } else {
        this.alertService.showToaster('Customer Delete Failed', 'ERROR');
      }
    });
  }

  onSave() {
    const cust = new Customer();
    cust.id = this.id;
    cust.name = this.name;
    cust.address = this.address;
    cust.salary = this.salary;
    if (!this.isEdit) {
      this.customerService.saveCustomer(cust).subscribe(data => {
        if (data) {
          this.alertService.showToaster('Customer is Saved', 'SUCCESS');
          this.onClear();
        } else {
          this.alertService.showToaster('Customer save Failed', 'ERROR');
        }
      });
    } else {
      this.customerService.editCustomer(cust).subscribe(data => {
        if (data) {
          this.alertService.showToaster('Customer is Updated', 'SUCCESS');
          this.onClear();
        } else {
          this.alertService.showToaster('Customer update Failed', 'ERROR');
        }
      });
    }

  }

  onClear() {
    this.id = null;
    this.name = null;
    this.address = null;
    this.salary = null;
    this.isEdit = false;
    this.getAllCustomers();
  }


}
