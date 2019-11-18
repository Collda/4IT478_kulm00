package cz.vse.selenium;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


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


    @Test
    public void valid_login(){

        //GIVEN + WHEN
        driver.get(PREFIX);
        //User login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));
        //passwordInput.sendKeys(Keys.ENTER);

        driver.close();
    }


    @Test
    public void invalid_login(){

        //GIVEN + WHEN
        driver.get(PREFIX);
        //User login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("bad");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        //passwordInput.sendKeys(Keys.ENTER);
        WebElement alert = driver.findElement(By.cssSelector(".alert"));
        Assert.assertTrue(!driver.getTitle().startsWith("Rukovoditel | Dashboard"));
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel"));
        Assert.assertTrue(alert != null);

        driver.close();
    }

    @Test
    public void user_logOff(){

        //GIVEN
        driver.get(PREFIX);
        //User login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

        //WHEN
        //Logoff
        WebElement menu = driver.findElement(By.className("username"));
        menu.click();
        driver.findElement(By.cssSelector("a[href*='logoff']")).click();

        //THEN
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel"));
        WebElement h3 = driver.findElement(By.className("form-title"));
        Assert.assertTrue(h3.getText().equals("Login"));

        driver.close();
    }
}
