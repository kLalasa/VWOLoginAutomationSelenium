package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class Lab001 {
        EdgeDriver driver;

        @BeforeClass

        public void setUp(){

            EdgeOptions options=new EdgeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.addArguments("--guest");
            driver=new EdgeDriver(options);

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        }
        @Test(dataProvider = "logindata")
        public void testDataDriven(String id,String Email,String password, String ExpectedResult){
            driver.get("https://app.vwo.com");
            WebElement emailElement= driver.findElement(By.id("login-username"));
            emailElement.clear();
            emailElement.sendKeys(Email);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement passwordElement=driver.findElement(By.id("login-password"));
            passwordElement.clear();
            passwordElement.sendKeys(password);
            driver.findElement(By.id("js-login-btn")).click();

            if (ExpectedResult.equalsIgnoreCase("Invalid")){
                WebElement err_msg=driver.findElement(By.className("notification-box-description"));
                WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(err_msg));
                Assert.assertTrue(err_msg.isDisplayed());
                String err_msg_text=err_msg.getText();
                Assert.assertEquals(err_msg_text,"Your email, password, IP address or location did not match");


            }


            if(ExpectedResult.equalsIgnoreCase("valid")){
                String text=driver.findElement(By.cssSelector("[data-qa=\"lufexuloga\"]")).getText();
                System.out.println(text);
                Assert.assertEquals(text,"abcd test");
            }


        }
        @DataProvider(name="logindata")
        public Object[][] testData(){
            return new Object[][]{

                    {"TD1","practice@special.com","test@123","Invalid"},
                    {"TD2","abcd@test.com","Test@abc","valid"}
            };
        }


        @AfterClass
        public void tearDown(){

            driver.quit();
        }


    }


