import { Injectable } from '@angular/core';
import {Item} from '../dto/Item';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

export const MAIN_URL = 'http://localhost:8080';
const ITEM_URL = '/api/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private httpClient: HttpClient) { }

  getAllItems(): Observable<Array<Item>>{
    return this.httpClient.get<Array<Item>>(MAIN_URL + ITEM_URL);
  }

  saveItem(item: Item): Observable<boolean>{
    return this.httpClient.post<boolean>(MAIN_URL + ITEM_URL, item);
  }

  updateItem(item: Item): Observable<boolean>{
    return this.httpClient.put<boolean>(MAIN_URL + ITEM_URL, item);
  }

  deleteItem(item: Item): Observable<boolean>{
    return this.httpClient.delete<boolean>(MAIN_URL + ITEM_URL + '?code=' + item.code);
  }
}
