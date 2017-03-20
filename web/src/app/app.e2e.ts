import { browser, by, element } from 'protractor';
import ElementNotVisibleError = webdriver.error.ElementNotVisibleError;

describe('App', () => {

    beforeEach(() => {
        browser.get('/');
    });

    it('should have a title', () => {
        const subject = browser.getTitle();
        const result = 'Haushaltsbuch';
        expect(subject).toEqual(result);
    });

    it('should have header', () => {
        const subject = element(by.css('h1')).isPresent();
        const result = true;
        expect(subject).toEqual(result);
    });

    it('should have <home>', () => {
        const subject = element(by.css('app home')).isPresent();
        const result = true;
        expect(subject).toEqual(result);
    });

    it('should have buttons', () => {
        const subject = element(by.css('button')).getText();
        const result = 'LOS GEHT\'S';
        expect(subject).toEqual(result);
    });

    it('sollte verstecktes Seitenmenü besitzen', () => {
        const subject = element(by.css('ul#side-menu')).getCssValue('transform');
        const result = 'matrix(1, 0, 0, 1, -300, 0)';
        expect(subject).toEqual(result);
    });

    describe('wenn das Browserfenster auf unter 993 Pixel verkleinert wird', () => {
        beforeEach(() => {
            browser.manage().window().setSize(1001, 800);
        });

        it('sollte das Hamburgersymbol sichtbar sein', () => {
            const subject = element(by.css('a[materialize="sideNav"]')).isDisplayed();
            expect(subject).toEqual(true);
        });

        describe('und ich das Hamburgersymbol anklicke', () => {
            beforeEach(() => {
                element(by.css('a[materialize="sideNav"]')).click();
                browser.waitForAngular();
            });

            // Wegen der Animation ist das Seitenmenü noch nicht vollständig ausgeklappt,
            xit('sollte das Seitenmenü sichtbar sein', () => {
                const subject = element(by.css('ul#side-menu')).getCssValue('transform');
                const result = 'matrix(1, 0, 0, 1, 0, 0)';
                expect(subject).toEqual(result);
            });
        });
    });

    describe('mit einem Browserfenster größer als 992 Pixel', () => {
        beforeEach(() => {
            // Hier scheint ein Fehler von 10 Pixeln vorzuliegen. Vielleicht
            // wird der Rahmen dazugezählt.
            browser.manage().window().setSize(1003, 800);
        });

        it('sollte das Hamburgersymbol nicht sichtbar sein', () => {
            const subject = element(by.css('a[materialize="sideNav"]')).isDisplayed();
            expect(subject).toEqual(false);
        });

        it('ist das Hamburgersymbol nicht anklickbar', () => {
            element(by.css('a[materialize="sideNav"]')).click()
                .then(() => fail('Hamburgersymbol ist sichtbar'),
                    (err: ElementNotVisibleError) => {
                        expect(err.message).toContain('element not visible');
                    });
        });

        it('sollte das Seitenmenu nicht sichtbar sein', () => {
            const subject = element(by.css('ul#side-menu')).getCssValue('transform');
            const result = 'matrix(1, 0, 0, 1, -300, 0)';
            expect(subject).toEqual(result);
        });
    });
});
