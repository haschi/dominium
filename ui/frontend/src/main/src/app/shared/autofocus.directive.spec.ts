import { AutofocusDirective } from './autofocus.directive';

describe('AutofocusDirective', () => {
  it('should create an instance', () => {
    const mock = {nativeElement: {}};
    const directive = new AutofocusDirective(mock);
    expect(directive).toBeTruthy();
  });
});
