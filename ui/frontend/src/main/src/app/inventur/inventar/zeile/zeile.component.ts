import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-zeile',
  templateUrl: './zeile.component.html',
  styleUrls: ['./zeile.component.scss']
})
export class ZeileComponent implements OnInit {

  @Input() text: string;
  @Input() betrag: string;

  constructor() { }

  ngOnInit() {
  }

}
