package com.github.haschi.haushaltsbuch.abfrage;

@RunWith(Arquillian.class)
public class HelloEndpointIT
{
    @Drone
    WebDriver browser;

    @Test
    public void testHello() {
        browser.navigate().to("http://localhost:8080/hello");
        assertThat(browser.getPageSource()).contains("Hello from Haushaltsbuch query");
    }
}
