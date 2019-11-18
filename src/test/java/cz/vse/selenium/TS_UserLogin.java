package cz.vse.selenium;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.UUID;

public class TS_UserLogin {


    private ChromeDriver driver;
    private String PREFIX = "https://digitalnizena.cz/rukovoditel/";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        // ChromeDriverService service = new ChromeDriverService();
        //ChromeOptions cho = new ChromeOptions();
        //cho.addArguments("headless");
        driver = new ChromeDriver();
//      driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
//  driver.close();
    }
}
