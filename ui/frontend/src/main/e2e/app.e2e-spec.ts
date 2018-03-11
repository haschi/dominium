import { AppPage } from './app.po';
import { InventurPage } from './inventur.po';
import { browser, by, element } from 'protractor';

describe('haushaltsbuch-app App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Haushaltsbuch');
  });

  it('sollte zum Inventur navigieren', () => {
      page.navigateTo();
      page.jetztAusprobieren();
      const inventur = new InventurPage();
      expect(inventur.cardTitle()).toEqual('Meine Inventur');
  });
});
