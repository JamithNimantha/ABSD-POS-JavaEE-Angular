import { Injectable } from '@angular/core';
import {ToastrManager} from 'ng6-toastr-notifications';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  constructor(public toastr: ToastrManager) { }

  showToaster(message: string, type: string) {
    if (type.toUpperCase() === 'SUCCESS') {
      this.toastr.successToastr(message, type, {animate: 'slideFromTop', enableHTML: true});
    }
    if (type.toUpperCase() === 'ERROR') {
      this.toastr.errorToastr(message, type, {animate: 'slideFromTop', enableHTML: true});
    }
    if (type.toUpperCase() === 'WARNING') {
      this.toastr.warningToastr(message, type, {animate: 'slideFromTop', enableHTML: true});
    }
    if (type.toUpperCase() === 'INFO') {
      this.toastr.infoToastr(message, type, {animate: 'slideFromTop', enableHTML: true});
    }

  }

}
