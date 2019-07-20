import { Injectable } from '@angular/core';
import {Customer} from '../dto/Customer';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

export const MAIN_URL = 'http://localhost:8080';
const CUSTOMER_URL = '/api/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private httpClient: HttpClient) { }

  getAllCustomers(): Observable<Array<Customer>> {
    return this.httpClient.get<Array<Customer>>(MAIN_URL + CUSTOMER_URL);
  }

  saveCustomer(customer: Customer): Observable<boolean> {
    return this.httpClient.post<boolean>(MAIN_URL + CUSTOMER_URL, customer);
  }

  editCustomer(customer: Customer): Observable<boolean> {
    return this.httpClient.put<boolean>(MAIN_URL + CUSTOMER_URL, customer);
  }

  deleteCustomer(customer: Customer): Observable<boolean> {
    return this.httpClient.delete<boolean>(MAIN_URL + CUSTOMER_URL + '?id=' + customer.id);
  }

  getAllCustomerIds() {
    return ['C001', 'C002', 'C002', 'C003', 'C004', 'C005'];
  }

}
