package cz.vse.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TS_CreatingProject {

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
    public void create_NewProject() throws InterruptedException {

        //GIVEN
        driver.get(PREFIX);

        //Login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

        //Go to Project table and save the count of rows
        WebElement menu = driver.findElement(By.className("fa-reorder"));
        menu.click();
        WebDriverWait waitt = new WebDriverWait(driver, 1);
        waitt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        int countBefore = elements.size();
        WebElement createButton = driver.findElement(By.className("btn-primary"));
        createButton.click();

        //Filling the form and save
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        WebElement saveButton = driver.findElement(By.className("btn-primary-modal-action"));
        WebElement name = driver.findElement(By.id("fields_158"));
        name.sendKeys("Novy projekt");
        Select select = new Select(driver.findElement(By.id("fields_156")));
        select.selectByIndex(1);
        WebElement calendarButton = driver.findElement(By.className("date-set"));
        calendarButton.click();
        WebElement searchInput = driver.findElement(By.id("fields_159"));
        searchInput.click();
        driver.findElement(By.cssSelector("td[class='active day']")).click();
        saveButton.click();

        //Then
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Tasks"));
        WebElement menu2 = driver.findElement(By.className("fa-reorder"));
        menu2.click();

        wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        int countAfter = elements.size();

        // Control check (before was fewer rows than now)
        Assert.assertTrue(countBefore < countAfter);

        //Helpfull removal of first row in table (header)
        elements.remove(0);

        // Helpful variable
        WebElement helpRow= null;

        // Cycle going through all fields in table
        for (WebElement row : elements)
        {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.get(4).getText().equals("Novy projekt"))
            {
                helpRow = row;
                List<WebElement> buttons = row.findElements(By.tagName("a"));
                buttons.get(0).click();
            }
        }

        // CHECK, Is project exists and its mine
        Assert.assertTrue(helpRow != null);

        // Deleting the project and check deletion
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uniform-delete_confirm")));
        driver.findElement(By.id("delete_confirm")).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();
        elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        Assert.assertTrue(!elements.contains(helpRow));

        driver.close();


        //Some failed attempts
       /* WebElement newRow = driver.findElement(By.className("a.item_heading_link"));
        Assert.assertTrue(newRow.getText().equals("Novy projekt"));
        Thread.sleep(2000);
        int index = 0;
        WebElement baseTable = driver.findElement(By.cssSelector("[class='table table-striped table-bordered table-hover']"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("td"));
        driver.findElement(By.xpath("//td[contains(div, 'EMP_COMPANY')]/following-sibling::td/div[@id = 'tdDataDiv']")).getText();

        //tableRows.get(index).getText();


       for (WebElement row:tableRows){

            Assert.assertTrue(row.getText().equals("Novy projekt"));

        }

        Assert.assertTrue(tableRows.getText().equals("Novy projekt"));
        */


    }

    @Test

    public void created_Task() throws ParseException {
        driver.get(PREFIX);


        //Login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

        //Create project
        WebElement menu = driver.findElement(By.className("fa-reorder"));
        menu.click();
        WebElement createButton = driver.findElement(By.className("btn-primary"));
        createButton.click();

        //Filling the form (Project) and save
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        WebElement saveButton = driver.findElement(By.className("btn-primary-modal-action"));
        WebElement name = driver.findElement(By.id("fields_158"));
        name.sendKeys("Novy projekt");
        Select select = new Select(driver.findElement(By.id("fields_156")));
        select.selectByIndex(1);
        WebElement calendarButton = driver.findElement(By.className("date-set"));
        calendarButton.click();
        WebElement searchInput = driver.findElement(By.id("fields_159"));
        searchInput.click();
        driver.findElement(By.cssSelector("td[class='active day']")).click();
        saveButton.click();

        //Creating task
        WebElement task = driver.findElement(By.className("btn-primary"));
        task.click();
        //WebDriverWait waitt = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));
        WebElement searchInput2 = driver.findElement(By.id("fields_168"));
        searchInput2.sendKeys("Babicka pece kolace");

        //Filling the description field
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.tagName("body")).sendKeys("Kolace budou povidlove a tvarohove");
        driver.switchTo().defaultContent();
        driver.findElement(By.className("btn-primary-modal-action")).click();

        wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        List<WebElement> cells = elements.get(1).findElements(By.tagName("td"));
        List<WebElement> content = cells.get(1).findElements(By.tagName("a"));
        content.get(2).click();


        //Then
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr")));
        elements = driver.findElements(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr"));


        // Check name - "Babicka pece kolace"
        WebElement nameTask = driver.findElement(By.className("caption"));
        Assert.assertTrue(nameTask.getText().equals("Babicka pece kolace"));

        // CHeck description "Kolace budou povidlove a tvarohove"
        WebElement description = driver.findElement(By.className("fieldtype_textarea_wysiwyg"));
        System.out.println(description.getText());
        Assert.assertTrue(description.getText().equals("Kolace budou povidlove a tvarohove"));

        // Date check
        cells = elements.get(1).findElements(By.tagName("td"));
        Date date =new SimpleDateFormat("MM/dd/yyyy").parse(cells.get(0).getText().substring(0, 10));
        Date sysdate = new Date();
        Assert.assertTrue(!elements.contains(date.equals(sysdate)));

        // Type check
        cells = elements.get(3).findElements(By.tagName("td"));
        content= cells.get(0).findElements(By.tagName("div"));
        Assert.assertTrue(content.get(0).getText().equals("Task"));

        // State check - new
        cells = elements.get(4).findElements(By.tagName("td"));
        content = cells.get(0).findElements(By.tagName("div"));
        Assert.assertTrue(content.get(0).getText().equals("New"));

        // Prio check - Medium
        cells = elements.get(5).findElements(By.tagName("td"));
        content = cells.get(0).findElements(By.tagName("div"));
        Assert.assertTrue(content.get(0).getText().equals("Medium"));

        //Delete task
        driver.executeScript("window.history.go(-1)");
        wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        cells = elements.get(1).findElements(By.tagName("td"));
        content = cells.get(1).findElements(By.tagName("a"));
        content.get(0).click();
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        driver.findElement(By.className("btn-primary-modal-action")).click();

        driver.close();

    }

    @Test

    public void created_Task2(){
        driver.get(PREFIX);


        //Login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

        //Create project
        WebElement menu = driver.findElement(By.className("fa-reorder"));
        menu.click();
        WebElement createButton = driver.findElement(By.className("btn-primary"));
        createButton.click();

        //Filling the form (Project) and save
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        WebElement saveButton = driver.findElement(By.className("btn-primary-modal-action"));
        WebElement name = driver.findElement(By.id("fields_158"));
        name.sendKeys("Novy projekt");
        Select select = new Select(driver.findElement(By.id("fields_156")));
        select.selectByIndex(1);
        WebElement calendarButton = driver.findElement(By.className("date-set"));
        calendarButton.click();
        WebElement searchInput = driver.findElement(By.id("fields_159"));
        searchInput.click();
        driver.findElement(By.cssSelector("td[class='active day']")).click();
        saveButton.click();

        //Cycle for creating tasks

        for (int x = 0;x<7;x++){

            //Creating task
            WebElement task = driver.findElement(By.className("btn-primary"));
            task.click();
            //WebDriverWait waitt = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));
            WebElement searchInput2 = driver.findElement(By.id("fields_168"));
            searchInput2.sendKeys("Babicka pece kolace");

            Select selectState = new Select(driver.findElement(By.id("fields_169")));
            selectState.selectByIndex(x);

            //Filling the description field
            driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            driver.findElement(By.tagName("body")).sendKeys("Kolace budou povidlove a tvarohove");
            driver.switchTo().defaultContent();
            driver.findElement(By.className("btn-primary-modal-action")).click();
        }

        // 3 tasks shown
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        //1st row of table is also row (name ...etc) so we have to deal with it
        Assert.assertTrue(elements.size() == 4);


        // Change filter
        driver.findElement(By.className("filters-preview-condition-include")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='chosen-choices'] a")));
        List<WebElement> filters = driver.findElements(By.cssSelector("[class='chosen-choices'] a"));
        filters.get(1).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();

        // 3 tasks shown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        //1st row of table is also row (name ...etc) so we have to deal with it
        Assert.assertTrue(elements.size() == 3);

        // Clear filters
        driver.findElement(By.className("filters-preview-condition-include")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='chosen-choices'] a")));
        filters = driver.findElements(By.cssSelector("[class='chosen-choices'] a"));
        filters.get(1).click();
        filters.get(0).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();

        // All 7 tasks shown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        elements = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        //1st row of table is also row (name ...etc) so we have to deal with it
        Assert.assertTrue(elements.size() == 8);

        // Clear all created tasks
        driver.findElement(By.id("select_all_items")).click();
        //doubleclick
        driver.findElement(By.cssSelector("[class='btn btn-default dropdown-toggle']")).click();
        driver.findElement(By.cssSelector("[class='btn btn-default dropdown-toggle']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        driver.findElement(By.linkText("Delete")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        driver.findElement(By.className("btn-primary-modal-action")).click();


        driver.close();

    }

}
