import { browser, by, element } from 'protractor';
import { InventurPage } from './inventur.po';
import { promise } from 'selenium-webdriver';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }

  jetztAusprobieren(): promise.Promise<void> {
      return element(by.id('ausprobieren')).click();
  }

    inventur() {
      return browser.get('/inventur');

    }
}
