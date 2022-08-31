package com.ygt.avatar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ADBRunner {

    private static final String URL = "https://adbs.uab.gov.tr/users/my-educations/1/join";

    public static void main(String[] args) {
        if (args.length<2){
            throw new IllegalArgumentException("Missing parameter");
        }
        SeleniumConfig seleniumConfig = new SeleniumConfig();
        WebDriver driver = seleniumConfig.getDriver();
        try {
            driver.get(URL);
            WebElement login = driver.findElement(By.cssSelector("a[class='mb-4']"));

            Thread.sleep(1000);
            login.click();
            Thread.sleep(1000);

            WebElement identityField = driver.findElement(By.id("tridField"));
            identityField.sendKeys(args[0]);
            WebElement passwordField = driver.findElement(By.id("egpField"));
            passwordField.sendKeys(args[1]);
            WebElement submit = driver.findElement(By.cssSelector("input[name='submitButton']"));
            submit.click();
            Thread.sleep(2000);
            WebElement skip = driver.findElement(By.cssSelector("button[class='btn btn-elevate btn-close']"));
            skip.click();
            run(driver);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    private static void run(WebDriver driver) throws InterruptedException {
        driver.get(URL);
        Thread.sleep(10000);
        for (int i = 0; i < 200; i++) {
            Thread.sleep(10000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
            Thread.sleep(2000);
            WebElement timer = driver.findElement(By.xpath("/html/body/in-root/in-users/in-dashboard-layout/main/in-lecture/div/div[2]/div/in-timer/div/h5"));
            while (!timer.getText().equals("00:00")) {
                Thread.sleep(20000);
            }
            try {
                WebElement forward = driver.findElement(By.cssSelector("dx-button[class='dx-button dx-button-success dx-button-mode-contained dx-widget dx-button-has-icon dx-button-has-text']"));
                forward.click();
                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    WebElement forward = driver.findElement(By.cssSelector("dx-button[aria-label='İleri']"));
                    forward.click();
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    try {
                        WebElement forward = driver.findElement(By.cssSelector("dx-button[role='button']"));
                        forward.click();
                        Thread.sleep(1000);
                    } catch (Exception e2) {
                       run(driver);
                    }
                }
            }

        }
    }


}
