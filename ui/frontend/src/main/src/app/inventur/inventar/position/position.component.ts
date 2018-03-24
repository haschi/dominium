import { Component, Input, OnInit } from '@angular/core';
import { Inventarposition } from '../../inventarposition';

@Component({
  selector: 'app-position',
  templateUrl: './position.component.html',
  styleUrls: ['./position.component.scss']
})
export class PositionComponent {

  @Input() position: Inventarposition;
}
