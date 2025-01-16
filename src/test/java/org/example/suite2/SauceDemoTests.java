package org.example.suite2;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SauceDemoTests {

    //Instance variables
    private WebDriver driver;
    private String url= "https://www.saucedemo.com/";
    private String header = "Swag Labs";

    /*For demonstrating parameterized builds
    *Retrieve a system property named "browser", Thus can be set from the cmd when test is run,
    *or passed through a Jenkins job to specify which browser (e.g., Chrome, Firefox) to use for the test execution
    *For example, in Jenkins, you could pass the parameter using: -Dbrowser=chrome
    */
    String browser = System.getProperty("browser");

    public void waitForPageLoaded(){
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try{
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.of(30, SECONDS));
            wait.until(expectation);
        }catch (Throwable error){
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    //Test to launch browser with url
    @Test()
    public void open() {
        driver.get(url);
        waitForPageLoaded();
        String title = driver.getTitle();
        System.out.println(title);
        //My Store
        AssertJUnit.assertEquals(title, header);
    }

//Login
@Test(enabled = true, priority = 1)
public void login() {
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.className("btn_action")).click();
    AssertJUnit.assertEquals("Products", driver.findElement(By.className("title")).getText());
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
        else if(browser.equalsIgnoreCase("firefox")) {
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
