import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ErfassungComponent } from './erfassung/erfassung.component';

@Component({
  selector: 'app-inventur',
  templateUrl: './inventur.component.html',
  styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  dialogOeffnen() {
    const dialogReferenz = this.dialog.open(ErfassungComponent, {
      width: '80wv'
    });

    dialogReferenz.afterClosed().subscribe(result => {
      console.info('Dialog geschlossen');
    });
  }
}
