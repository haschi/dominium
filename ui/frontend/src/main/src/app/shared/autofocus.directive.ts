import { AfterContentInit, Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appAutofocus]'
})
export class AutofocusDirective implements AfterContentInit {

    ngAfterContentInit(): void {
      this.element.nativeElement.focus();
    }

  constructor(private element: ElementRef) {
  }
}
