package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumTest {

    private WebDriver driver;

    @Before
    public void setUp(){
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void teardDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    public void testEasy(){
        driver.get("https://testautomationu.applitools.com/");
        String title = driver.getTitle();
        Assert.assertTrue(title.equals("Test Automation University | Applitools"));
    }

    @Test
    public void testTwo(){
        driver.get("https://www.google.com/");
        String title1 = driver.getTitle();
        System.out.println("Title is: " + title1);
        Assert.assertTrue(title1.equals("Google"));
    }
}
