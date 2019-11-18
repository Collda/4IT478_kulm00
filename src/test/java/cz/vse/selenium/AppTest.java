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
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private ChromeDriver driver;
    private String PREFIX = "https://digitalnizena.cz/rukovoditel/";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
       // ChromeDriverService service = new ChromeDriverService();
        //ChromeOptions cho = new ChromeOptions();
        //cho.addArguments("headless");
        driver = new ChromeDriver();
//        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
//        driver.close();
    }

    @Test
    public void valid_login(){
        driver.get(PREFIX);

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
        driver.get(PREFIX);

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
        driver.get(PREFIX);

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

       WebElement menu = driver.findElement(By.className("username"));
       menu.click();
       driver.findElement(By.cssSelector("a[href*='logoff']")).click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel"));
        WebElement h3 = driver.findElement(By.className("form-title"));
        Assert.assertTrue(h3.getText().equals("Login"));

        driver.close();

    }


    @Test
    public void create_project() throws InterruptedException {
        driver.get(PREFIX);

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));

        //passwordInput.sendKeys(Keys.ENTER);
        //driver.findElement(By.cssSelector("a[href*='logoff']")).click();

        WebElement menu = driver.findElement(By.className("fa-reorder"));
        menu.click();

        WebElement createButton = driver.findElement(By.className("btn-primary"));
        createButton.click();

        /*WebElement saveButton = driver.findElement(By.cssSelector("button"));
        String typeOfElement = saveButton.getAttribute("type");
        saveButton.click();*/

        //Thread.sleep(2000);

        /*WebElement saveButton = driver.findElement(By.className("btn-primary-modal-action"));
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(saveButton));
        saveButton.click();*/

        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        WebElement saveButton = driver.findElement(By.className("btn-primary-modal-action"));
        saveButton.click();


        WebElement error = driver.findElement(By.id("fields_158-error"));
        Assert.assertTrue(error.getText().equals("This field is required!"));

        driver.close();


    }

    @Test
    public void create_NewProject() throws InterruptedException {
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

    public void created_Task(){
        driver.get(PREFIX);


        //Login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Dashboard"));


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




    }

/*

    @Test
    public void google1_should_pass() {
        driver.get("https://www.google.com/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("travis");
        searchInput.sendKeys(Keys.ENTER);
        Assert.assertTrue(driver.getTitle().startsWith("travis - "));
        driver.quit();
    }

    @Test
    public void google2_should_fail() {
        driver.get("https://www.google.com/");
       // WebElement searchInputNotExisting = driver.findElement(By.name("kdsfkladsjfas"));
        driver.quit();
    }

    @Test
    public void google3_should_fail() {
        driver.get("https://www.google.com/");
        Assert.assertEquals("one", "one");
        driver.quit();
    }

    public void shouldNotLoginUsingInvalidPassword() {
        // given
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // when
        WebElement usernameInput = driver.findElement(By.id("txtUsername"));
        usernameInput.sendKeys("admin");
        WebElement passwordInput = driver.findElement(By.id("txtPassword"));
        passwordInput.sendKeys("invalidPassssssssword");
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();

        // then
        WebElement errorMessageSpan = driver.findElement(By.id("spanMessage"));
        Assert.assertEquals("Invalid credentials", errorMessageSpan.getText());

        // validation error exists
        // url changed to https://opensource-demo.orangehrmlive.com/index.php/auth/validateCredentials
        // there is no menu
    }


    @Test
    public void shouldLoginUsingValidCredentials() {
        // given
        //driver.get("http://demo.churchcrm.io/master/");
        driver.get("http://digitalnizena.cz/church/");

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys("church");
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys("church12345");
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();
    }

    public void shouldCreateNewUser() throws InterruptedException {
        // Given
        shouldLoginUsingValidCredentials();

        // When
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        WebElement genderSelectElement = driver.findElement(By.name("Gender"));
        Select genderSelect = new Select(genderSelectElement);
        genderSelect.selectByVisibleText("Male");

        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        firstNameInput.sendKeys("John");
        WebElement lastNameInput = driver.findElement(By.id("LastName"));
        String uuid = UUID.randomUUID().toString();
        lastNameInput.sendKeys("Doe " + uuid);
        WebElement emailInput = driver.findElement(By.name("Email"));
        emailInput.sendKeys("john.doe@gmail.com");

        WebElement classificationSelectElement = driver.findElement(By.name("Classification"));
        Select classificationSelect = new Select(classificationSelectElement);
        classificationSelect.selectByIndex(4);

        WebElement personSaveButton = driver.findElement(By.id("PersonSaveButton"));
        personSaveButton.click();

        // Then
        driver.get("http://digitalnizena.cz/church/v2/people");

        WebElement searchInput = driver.findElement(By.cssSelector("#members_filter input"));
        searchInput.sendKeys(uuid);
        Thread.sleep(500);

        // to verify if record is shown in table grid, we first filter the whole table to get exactly one data row
        // that row should contain previously generated UUID value (in last name
        // UKOL...opravit, doplnit tak, aby se provedla verifikace ze kontakt, ktery jsme vytvorili opravdu existuje
        //    (jde vyhledat a zobrazi se v tabulce)
        //    doporucuji radek tabulky s danou osobou projit (traverzovat), nebo jinym zpusobem v nem najit retezec UUID, ktery jednoznacne identifikuje pridanou osobu
        List<WebElement> elements = driver.findElements(By.cssSelector("table#members tr"));
        Assert.assertEquals(2, elements.size());

        // data row is at index 0, header row is at index 1  (because in ChurchCRM html code there is tbody before thead)
        WebElement personTableRow = elements.get(0);


        // option1
        Assert.assertTrue(personTableRow.getText().contains(uuid));

        // option2 - traverse all cells in table grid
        List<WebElement> cells = personTableRow.findElements(By.tagName("td"));
        final int EXPECTED_COLUMNS = 9;
        Assert.assertEquals(EXPECTED_COLUMNS, cells.size());
        for (int i = 0; i < cells.size(); i++) {
            WebElement cell = cells.get(i);
            if (cell.getText().contains(uuid)) {
                //
            }

            System.out.println(cells.get(i).getText());
        }
    }


    @Test
    public void given_userIsLoggedIn_when_userAddsNewDeposit_then_depositRecordIsShownInDepositTableGrid() throws InterruptedException {
        // GIVEN user is logged in

        shouldLoginUsingValidCredentials();

        // WHEN user adds deposit comment

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        WebElement depositCommentInput = driver.findElement(By.cssSelector("#depositComment"));
        String uuid = UUID.randomUUID().toString();
        String depositComment = "deposit-PavelG-" + uuid;
        depositCommentInput.sendKeys(depositComment);

        WebElement depositDateInput = driver.findElement(By.cssSelector("#depositDate"));
        depositDateInput.click();
        depositDateInput.clear();
        depositDateInput.sendKeys("2018-10-30");

        WebElement addDepositButton = driver.findElement(By.cssSelector("#addNewDeposit"));
        addDepositButton.click();

        // THEN newly added deposit should be shown in deposits table grid

        // option1 - wait exactly 2 seconds, blocks the thread ....not recommended
        // Thread.sleep(2000);

        // option2 - use custom "expected condition" of WebDriver framework
        WebDriverWait wait = new WebDriverWait(driver, 2);     // timeout after 2 seconds
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                // each time, we try to get the very first row from table grid and check, if contains the last record

                List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable_wrapper #depositsTable tbody tr"));
                WebElement firstRow = depositRows.get(0);
                String innerHTML = firstRow.getAttribute("innerHTML");

                if (innerHTML.contains(uuid)) {
                    Assert.assertTrue(innerHTML.contains("10-30-18"));    // beware, different date format in table grid vs. input field
                    Assert.assertTrue(innerHTML.contains(depositComment));
                    return true;     // expected condition is met
                } else {
                    return false;    // selenium webdriver will continue polling the DOM each 500ms and check the expected condition by calling method apply(webDriver) again
                }
            }
        });
    }

    public void deleteDeposits() throws InterruptedException {
        shouldLoginUsingValidCredentials();

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        Thread.sleep(1000);

        List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable tbody tr"));

        for (WebElement row : depositRows) {
            row.click();
        }

//
        WebElement deleteButton = driver.findElement(By.cssSelector("#deleteSelectedRows"));
        deleteButton.click();
//
//        //TODO compare this WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-dialog .btn-primary"));
        WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-content > .modal-footer .btn-primary"));
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOf(confirmDeleteButton));
        confirmDeleteButton.click();

//        // actually the application behaves incorrect => when delete all rows, Delete button should be disabled
//        // we have our test correct, so it good that test fails!
        Assert.assertFalse(deleteButton.isEnabled());
    }

    public void loadingExample() {
        driver.get("http://digitalnizena.cz/priklad/loading1.html");

        WebElement button = driver.findElement(By.cssSelector("#my-button"));

        WebDriverWait wait = new WebDriverWait(driver, 12);
        wait.until(ExpectedConditions.visibilityOf(button));

        // here in code, we are 100% sure, that button is visible
    }
*/

}
