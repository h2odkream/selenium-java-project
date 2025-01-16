package org.example.suite1;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OpenCartTests {
    //For demonstrating parameterized builds
    String browser = System.getProperty("browser");

    private WebDriver driver;
    private String url = "http://opencart.abstracta.us/";
    String searchField = "//*[@id='search']/input";
    String result = "//*[@id=\"content\"]/div[3]/div/div/div[1]/a/img";
    String query = "Macbook Air";


    //Test to launch browser with url
    @Test
    public void launchSite() {
        driver.get(url);
        String title = driver.getTitle();
        //Your Store
        AssertJUnit.assertTrue(title.equals("Your Store"));
    }

    //Test to search for a product
    @Test
    public void searchForProduct() {
        driver.findElement(By.xpath(searchField)).sendKeys(query + Keys.ENTER);

        //sleep only when firefox as page loading takes some more time
        if(browser.equalsIgnoreCase("firefox")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        AssertJUnit.assertTrue(driver.findElement(By.xpath(result)).isDisplayed());
    }


    //Before test
    @BeforeTest
    public void beforeTest() {
        //Instantiate browser based on user input

        if(browser != "" && browser != null) {
            if(browser.equalsIgnoreCase("Chrome")) {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
            else if(browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
                driver.manage().window().maximize();
            }
            else {
                System.out.println("Invalid option Selected hence defaulting to Chrome");
                browser = "Chrome";
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
        }
        else {
            browser = "Chrome";
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }

    //hooks - to tear down after test is executed
    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
