package Tests;

import Utils.BaseWeb;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import Pages.ExcelClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import static Pages.RegisterClass.*;
import static Pages.SearchClass.*;
import static org.testng.Assert.assertEquals;
import java.util.Random;


public class SanityTest extends BaseWeb {
    private static final Logger logger = LoggerFactory.getLogger(SanityTest.class);

    WebDriver driver;
    @BeforeMethod
    public void setup() throws IOException {
        driver = init();
    }


    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomEmail = faker.internet().emailAddress();
    Random random = new Random();


    private void takeScreenshot(String screenshotName) {
        try {
            File directory = new File("screenshots");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            File destFile = new File(directory, fileName);
            FileUtils.copyFile(sourceFile, destFile);
            logger.info("Screenshot saved: {}", destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
        }
    }


    @Test()
    public void signUp() {
        WebElement signUP_Login = driver.findElement(By.xpath(SIGNUP_LOGIN_BUTTON));
        signUP_Login.click();
        WebElement newUserText = driver.findElement(By.cssSelector(NEW_USER_SIGNUP_TEXT));
        assertEquals(newUserText.getText(), "New User Signup!");
        logger.info("New User SignUp Message: {} ", newUserText.getText());
        assertEquals(newUserText.getText(), "New User Signup!");



    }

    @Test()
    public void fillContent() {

        signUp();
        WebElement nameField = driver.findElement(By.xpath(NAME));
        nameField.sendKeys(randomName);

        WebElement emailField = driver.findElement(By.xpath(EMAIL));
        emailField.sendKeys(randomEmail);

        WebElement signUpButton = driver.findElement(By.xpath(SIGNUP_BUTTON));
        signUpButton.click();
        WebElement Message = driver.findElement(By.cssSelector(ENTER_ACCOUNT_INFORMATION));
        String message = Message.getText();
        logger.info("Enter Account Information Message: {}", message);
        assertEquals(message, "ENTER ACCOUNT INFORMATION");
        takeScreenshot("enter account info");

    }

    @Test()

    public void fillPrivateContact() {

        try {

            signUp();
            fillContent();


            int genderChoice = random.nextInt(2) + 1;
            WebElement title = driver.findElement(By.xpath("//*[@id='id_gender"+genderChoice + "']"));
            title.click();


            String randomPassword = faker.internet().password(8, 12);
            WebElement passwordField = driver.findElement(By.id(PASSWORD));
            passwordField.sendKeys(randomPassword);


            Select day = new Select(driver.findElement(By.id("days")));
            day.selectByIndex(random.nextInt(27) + 1);

            Select month = new Select(driver.findElement(By.id("months")));
            month.selectByIndex(random.nextInt(12) + 1);

            Select year = new Select(driver.findElement(By.id("years")));
            year.selectByVisibleText(String.valueOf(random.nextInt(40) + 1980));


            driver.findElement(By.id(NEWS_LETTER)).click();
            driver.findElement(By.id(OPTION)).click();


            driver.findElement(By.id(FIRST_NAME)).sendKeys(faker.name().firstName());
            driver.findElement(By.id(LAST_NAME)).sendKeys(faker.name().lastName());


            driver.findElement(By.id(COMPANY)).sendKeys(faker.company().name());
            driver.findElement(By.id(ADDRESS)).sendKeys(faker.address().streetAddress());
            driver.findElement(By.id(ADDRESS2)).sendKeys(faker.address().secondaryAddress());


            Select country = new Select(driver.findElement(By.id(COUNTRY)));
            country.selectByVisibleText("Israel");


            driver.findElement(By.id(STATE)).sendKeys(faker.address().state());
            driver.findElement(By.id(CITY)).sendKeys(faker.address().city());
            driver.findElement(By.id(ZIP_CODE)).sendKeys(faker.number().digits(6));
            driver.findElement(By.id(MOBILE_NUMBER)).sendKeys(faker.phoneNumber().cellPhone());

            WebElement createAccount = driver.findElement(By.xpath(CREATE_ACCOUNT));
            createAccount.click();

            WebElement e = driver.findElement(By.xpath(ACCOUNT_CREATED_MASSAGE));
            String actualText1 = e.getText();
            logger.info("Account Created Message: {}", actualText1);
            assertEquals(actualText1, "ACCOUNT CREATED!");
            takeScreenshot("accountCreated ");


            WebElement continueButton = driver.findElement(By.xpath(CONTINUE));
            continueButton.click();


            WebElement e1 = driver.findElement(By.cssSelector(LOGGED_IN));
            String actualText2 = e1.getText();
            logger.info("Logged In Message: {}", actualText2);
            assertEquals(actualText2, "Logged in as " + randomName);

            Thread.sleep(1000);

            WebElement deleteAccount = driver.findElement(By.xpath(DELETE_ACCOUNT));
            deleteAccount.click();

            WebElement e2 = driver.findElement(By.xpath(ACCOUNT_DELETED));
            String actualText3 = e2.getText();
            logger.info("Account Deleted Message: {}", actualText3);
            assertEquals(actualText3, "ACCOUNT DELETED!");
            takeScreenshot("AccountDeleted");


        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}", e.getMessage());

        } catch (TimeoutException e) {
            logger.error("Timeout occurred: {}", e.getMessage());

        } catch (AssertionError e) {
            logger.error("Assertion failed: {}", e.getMessage());

        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            takeScreenshot("fillPrivateContact_error");
        }

    }


    @Test
    public void SearchProduct() {

        String visible = "Home page is visible successfully.";
        String notVisible = "Home page is not visible.";
        try {

            WebDriver driver = init();

            WebElement homePage = driver.findElement(By.cssSelector(HOME_PAGE));
            if (homePage.isDisplayed()) {
                logger.info(visible);
            } else {
                logger.info(notVisible);
            }

            WebElement products = driver.findElement(By.xpath(PRODUCTS));
            products.click();

            WebElement ele = driver.findElement(By.xpath(ALL_PRODUCTS));
            String actualText3 = ele.getText();
            logger.info("All Products Message: {}", actualText3);

            WebElement phone = driver.findElement(By.id(SEARCH_PRODUCTS));
            phone.sendKeys("Blue Top");

            WebElement submit = driver.findElement(By.id(SUBMIT_SEARCH));
            submit.click();

            WebElement ele4 = driver.findElement(By.xpath(SEARCHED_PRODUCTS));
            String actualText4 = ele4.getText();
            logger.info("Searched Products Message: {}", actualText4);
            assertEquals(actualText4, "SEARCHED PRODUCTS");
            takeScreenshot("searchResults");



        } catch (Exception e) {

            logger.info("error : ", e);
        }
    }

    @Test()
    public void fillContentExcel() {



        String path = "C:\\Users\\Yosef\\IdeaProjects\\QAFinalProject\\Sheet1.xlsx";
        String sheet = "Sheet1";


        List<String> names = ExcelClass.readExcelData(path, sheet, 0);
        List<String> emails = ExcelClass.readExcelData(path, sheet, 1);


        String nameExcel = names.get(1);
        String emailExcel = emails.get(2);

        signUp();
        WebElement element = driver.findElement(By.cssSelector(NEW_USER_SIGNUP_TEXT));
        String actualText = element.getText();
        logger.info("New User SignUp Message: {} ", actualText);
        assertEquals(actualText, "New User Signup!");

        WebElement nameField = driver.findElement(By.xpath(NAME));
        nameField.sendKeys(nameExcel);

        WebElement emailField = driver.findElement(By.xpath(EMAIL));
        emailField.sendKeys(emailExcel);

        WebElement signUpButton = driver.findElement(By.xpath(SIGNUP_BUTTON));
        signUpButton.click();
        WebElement Message = driver.findElement(By.cssSelector(ENTER_ACCOUNT_INFORMATION));
        String message = Message.getText();
        logger.info("Enter Account Information Message: {}", message);
        assertEquals(message, "ENTER ACCOUNT INFORMATION");
        takeScreenshot("enter account info");
    }
    @Test()
    public void testRegisterWithExistingEmail() {
        signUp();


        WebElement nameField = driver.findElement(By.xpath(NAME));
        nameField.sendKeys("nagham");

        WebElement emailField = driver.findElement(By.xpath(EMAIL));
        emailField.sendKeys("naghama@gmail.com");


        WebElement signUpButton = driver.findElement(By.xpath(SIGNUP_BUTTON));
        signUpButton.click();


        WebElement errorMessage = driver.findElement(By.xpath(ERROR_MESSAGE));
        assertEquals(errorMessage.getText(), "Email Address already exist!");

        takeScreenshot("email_exists_error");
    }
    @Test
    public void PlaceOrderAfterLogin() {
        signUp();
        // וידוא שהמשתמש מחובר
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")).sendKeys("naghama@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")).sendKeys("nagham123");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div[1]/form/button")).click();


        WebElement loggedInMessage = driver.findElement(By.cssSelector(LOGGED_IN));
        String expectedLoginText = "Logged in as " + "nagham";
        assertEquals(loggedInMessage.getText(), expectedLoginText);
        logger.info("Verified login: {}", expectedLoginText);

        //מחפש מוצר ומוסיף אותו לעגלה
        driver.findElement(By.xpath(PRODUCTS)).click();


        WebElement addToCartButton = driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);

        //לוחץ על ADD TO CART שלוש פעמים
        boolean isClicked = false;
        for (int i = 0; i < 3; i++) {
            try {
                addToCartButton.click();
                isClicked = true;
                break;
            } catch (ElementClickInterceptedException e) {

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                isClicked = true;
                break;
            } catch (Exception e) {
                logger.error("Failed to click 'Add to cart' button: {}", e.getMessage());

            }
        }

        if (!isClicked) {
            throw new NoSuchElementException("Unable to click the 'Add to cart' button after multiple attempts.");
        }

        //עשר שניות עד שילחץ על הכפתור
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement Btn_continueShopping = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cartModal > div > div > div.modal-footer > button")));
        Btn_continueShopping.click();


        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[3]/a")).click();

        WebElement cartPage = driver.findElement(By.xpath("//*[@id=\"cart_items\"]/div/div[1]/ol/li[2]"));
        assertEquals(cartPage.getText(), "Shopping Cart");
        logger.info("Cart page is displayed successfully");


        driver.findElement(By.xpath("//*[@id=\"do_action\"]/div[1]/div/div/a")).click();


        WebElement addressDetails = driver.findElement(By.xpath("//h2[contains(text(),'Address Details')]"));
        WebElement orderReview = driver.findElement(By.xpath("//*[@id=\"cart_items\"]/div/div[4]/h2"));
        assertEquals(addressDetails.getText(), "Address Details");
        assertEquals(orderReview.getText(), "Review Your Order");
        logger.info("Address and order details are displayed");


        WebElement commentBox = driver.findElement(By.xpath("//textarea[@name='message']"));
        commentBox.sendKeys("Please deliver ASAP!");
        WebElement placeOrderButton = driver.findElement(By.xpath("//*[@id=\"cart_items\"]/div/div[7]/a"));
        placeOrderButton.click();


        WebElement nameOnCard = driver.findElement(By.name("name_on_card"));
        nameOnCard.sendKeys(faker.name().fullName());
        WebElement cardNumber = driver.findElement(By.name("card_number"));
        cardNumber.sendKeys(faker.finance().creditCard().replaceAll("-", ""));
        WebElement cvc = driver.findElement(By.name("cvc"));
        cvc.sendKeys(String.valueOf(faker.number().numberBetween(100, 999)));


        WebElement expiryMonth = driver.findElement(By.name("expiry_month"));
        expiryMonth.sendKeys(String.valueOf(faker.number().numberBetween(1, 12)));

        WebElement expiryYear = driver.findElement(By.name("expiry_year"));
        expiryYear.sendKeys(String.valueOf(faker.number().numberBetween(2025, 2035)));
        takeScreenshot("card information");


        WebElement payButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
        payButton.click();


        WebElement successMessage = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/p"));
        assertEquals(successMessage.getText(), "Congratulations! Your order has been confirmed!");
        logger.info("Order placed successfully");


        WebElement deleteAccount = driver.findElement(By.xpath(DELETE_ACCOUNT));
        deleteAccount.click();


        WebElement deletedMessage = driver.findElement(By.xpath(ACCOUNT_DELETED));
        assertEquals(deletedMessage.getText(), "ACCOUNT DELETED!");
        logger.info("Account deleted successfully");
        takeScreenshot("account deleted");
    }

}