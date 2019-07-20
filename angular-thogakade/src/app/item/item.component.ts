import { Component, OnInit } from '@angular/core';
import {Item} from '../core/dto/Item';
import {ItemService} from '../core/services/item.service';
import {AlertService} from '../core/services/alert.service';
declare var $: any;

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  items: Item[] = [];
  item: Item = new Item();
  deleteItem: Item;
  isEdit = false;

  constructor(private itemService: ItemService, private alertService: AlertService) {}

  ngOnInit() {
    this.getAllItems();
  }

  private getAllItems() {
   this.itemService.getAllItems().subscribe( data => {
     this.items = data;
   });
  }

  // there is a issue with onEdit(). item array should be reloaded
  onEdit(item) {
    this.item = item;
    this.isEdit = true;
    this.items.splice(this.items.indexOf(item), 1);
  }

  onDelete(item) {
    this.deleteItem = item;
    $('#deleteItemModel').modal('show');
  }

   onCountinueDelete() {
    this.itemService.deleteItem(this.deleteItem).subscribe(data => {
      if (data) {
        this.alertService.showToaster('Item Deleted Successfully', 'SUCCESS');
        this.onClear();
      } else {
        this.alertService.showToaster('Item Delete Failed', 'ERROR');
      }
    });
  }

  onClear() {
    this.item = new Item();
    this.isEdit = false;
    this.getAllItems();
  }

  onSave() {
    if (!this.isEdit) {
      this.itemService.saveItem(this.item).subscribe(data => {
        if (data) {
          this.alertService.showToaster('Item Saved Successfully', 'SUCCESS');
          this.onClear();
        } else {
          this.alertService.showToaster('Item Save Failed', 'ERROR');
        }
      });
    } else {
      this.itemService.updateItem(this.item).subscribe(data => {
        if (data) {
          this.alertService.showToaster('Item Updated Successfully', 'SUCCESS');
          this.onClear();
        } else {
          this.alertService.showToaster('Item Update Failed', 'ERROR');
        }
      });
    }
  }

}
