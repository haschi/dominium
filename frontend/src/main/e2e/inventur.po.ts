

import { browser, by, element} from 'protractor';
import { promise, WebElement } from 'selenium-webdriver';

export class InventurPage {
    cardTitle() {
        return element(by.css('h4')).getText();
    }
}
