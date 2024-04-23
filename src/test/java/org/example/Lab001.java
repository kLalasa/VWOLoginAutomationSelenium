package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;


public class Lab001 {

    // 2 Test cases

    // One Negative - Invalid Username, password -> Error
    // One Positive - Valid Username, password -> Dashboard Page

    ChromeOptions options;
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        System.out.println("Initializing WebDriver...");
        options = new ChromeOptions();
        options.addArguments("--start-maximized");


            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

            // Add any additional error handling or logging as needed

    }


    @Test(priority = 1, groups = {"negative", "sanity", "reg"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("TC#1 - Verify that with Invalid username and Valid password, Login is not successfull !!")
    public void testInvalidLogin() throws InterruptedException {




        driver.get("https://app.vwo.com");
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix.co");
        driver.findElement(By.id("login-password")).sendKeys("Wingify@123");
        driver.findElement(By.id("js-login-btn")).click();

        WebElement errorMessage = driver.findElement(By.className("notification-box-description"));
        // 3 seconds
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(errorMessage));

        String errorString = driver.findElement(By.className("notification-box-description")).getText();
        Assert.assertEquals(errorMessage.getText(), "Your email, password, IP address or location did not match");


    }

    @Test(enabled = true, priority = 2, groups = {"positive", "sanity", "stage"})
    @Description("Verify that with Valid username and Valid password, Login is successfull !!")
    public void testValidLogin() throws InterruptedException {

        // ID, Name, ClassName -> CssSelector, Xppath - Firefox or Chrome
        // xpath. CSS Selector -> Chrome, will not work Firefox.


        driver.get("https://app.vwo.com");
        driver.findElement(By.id("login-username")).sendKeys("abcd@test.com");
        driver.findElement(By.id("login-password")).sendKeys("Test@abc");
        driver.findElement(By.id("js-login-btn")).click();

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa=\"lufexuloga\"]")));
        String text= element.getText();
        System.out.println(text);
        Assert.assertEquals(text,"abcd test");




    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }


}