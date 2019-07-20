import { Component, OnInit } from '@angular/core';
import {AlertService} from '../core/services/alert.service';
import {LoginService} from '../core/services/login.service';
import {User} from '../core/dto/User';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: string;
  pass: string;

  constructor(private alertService: AlertService, private loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }

  onLogin() {
    const user: User = new User();
    user.user = this.user;
    user.pass = this.pass;

    this.loginService.logInUser(user).subscribe(data => {
      console.log(data);
      if (data) {
        this.router.navigateByUrl('app/dashboard');
      } else {
        this.alertService.showToaster('Username or Password is incorrect!', 'ERROR');
      }
    });

  }
}
