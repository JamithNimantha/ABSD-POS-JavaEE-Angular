import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

export const MAIN_URL = 'http://localhost:8080';
const DASH_URL = '/api/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private httpClient: HttpClient) { }

  getData(): Observable<Array<number>> {
    return this.httpClient.get<Array<number>>(MAIN_URL + DASH_URL);
  }
}
