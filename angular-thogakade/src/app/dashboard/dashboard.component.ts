import { Component, OnInit } from '@angular/core';
import {DashboardService} from '../core/services/dashboard.service';

declare let $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  counts: number[] = [];

  constructor(private dashBoardService: DashboardService) { }

  ngOnInit() {
    this.dashBoardService.getData().subscribe(data => {
      this.counts = data;
    });
  }




}
