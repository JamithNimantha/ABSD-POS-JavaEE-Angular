import { Component, OnInit } from '@angular/core';
// import * as $ from 'jquery';
declare var $: any;


@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    $(document).ready(() => {
      $('.ui.toggle.button').click(() => {
        $('.mobile.only.grid .ui.vertical.menu').toggle(100);
      });
    });
  }

  onClickLogout(){
    $('#logoutMode').modal('show');
  }

}
