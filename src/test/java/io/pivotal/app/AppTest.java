package io.pivotal.app;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxBinary;

import static junit.framework.Assert.assertTrue;

public class AppTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Setup firefox binary to start in Xvfb        
        String Xport = System.getProperty(
                "lmportal.xvfb.id", ":1");
        final File firefoxPath = new File(System.getProperty(
                "lmportal.deploy.firefox.path", "/usr/bin/firefox"));
        FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
        firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);

        driver = new FirefoxDriver(firefoxBinary, null);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void verifyThatStockholmCanBeFound() {
        HomePage home = new HomePage(driver);
        SearchResultPage searchResult = home.searchFor("Stockholm");

        LocationPage stockholm = searchResult.clickOnTopSearchResultLink();

        String expected = "Stockholm";
        String actual = stockholm.getHeadLine();
        assertTrue(actual.contains(expected));
    }
}

