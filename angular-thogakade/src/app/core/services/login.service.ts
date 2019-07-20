import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {User} from '../dto/User';
import {HttpClient} from '@angular/common/http';

export const MAIN_URL = 'http://localhost:8080';
const USER_URL = '/login';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  logInUser(user: User): Observable<boolean> {
    return this.httpClient.post<boolean>(MAIN_URL + USER_URL, user);
  }
}
