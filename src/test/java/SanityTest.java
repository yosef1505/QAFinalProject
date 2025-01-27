import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static org.example.RegisterClass.*;
import static org.example.SearchClass.*;
import static org.testng.Assert.assertEquals;


public class SanityTest extends BaseWeb {
    private static final Logger logger = LoggerFactory.getLogger(SanityTest.class);

    WebDriver driver = init();

    Faker faker = new Faker();
    String randomName = faker.name().firstName();
    String randomEmail = faker.internet().emailAddress();



    @Test()
    public void signUp() {
        WebElement signUP_Login = driver.findElement(By.xpath(SIGNUP_LOGIN_BUTTON));
        signUP_Login.click();


    }

    @Test()
    public void fillContent() {

        signUp();
        WebElement element = driver.findElement(By.cssSelector(NEW_USER_SIGNUP_TEXT));
        String actualText = element.getText();
        logger.info("New User SignUp Message: {} " , actualText);
        assertEquals(actualText, "New User Signup!");

        WebElement nameField = driver.findElement(By.xpath(NAME));
        nameField.sendKeys(randomName);

        WebElement emailField = driver.findElement(By.xpath(EMAIL));
        emailField.sendKeys(randomEmail);

        WebElement signUpButton = driver.findElement(By.xpath(SIGNUP_BUTTON));
        signUpButton.click();
        WebElement Message = driver.findElement(By.cssSelector("#form > div > div > div > div > h2 > b"));
        String message = Message.getText();
        logger.info("Enter Account Information Message: {}" , message);
        assertEquals(message, "ENTER ACCOUNT INFORMATION");

    }

    @Test()

    public void fillPrivateContact() {

        try {

            signUp();
            fillContent();

            WebElement Message = driver.findElement(By.xpath(TITLE));
            String message = Message.getText();
            System.out.println(message);
            assertEquals(message, "Title");

            WebElement titleMrs = driver.findElement(By.id(UNIFORM_ID_GENDER1));
            titleMrs.click();

            WebElement passwordField = driver.findElement(By.id(PASSWORD));
            passwordField.sendKeys("yosef1122");

            Select day = new Select(driver.findElement(By.id("days")));
            day.selectByVisibleText("15");
            Select month = new Select(driver.findElement(By.id("months")));
            month.selectByVisibleText("May");
            Select year = new Select(driver.findElement(By.id("years")));
            year.selectByVisibleText("1999");

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Thread.sleep(2000);

            WebElement newsletter = driver.findElement(By.id(NEWS_LETTER));
            newsletter.click();

            WebElement options = driver.findElement(By.id(OPTION));
            options.click();

            WebElement firstNameField = driver.findElement(By.id(FIRST_NAME));
            firstNameField.sendKeys("yosef");

            WebElement lastNameField = driver.findElement(By.id(LAST_NAME));
            lastNameField.sendKeys("abo qweder");

            WebElement companyName = driver.findElement(By.id(COMPANY));
            companyName.sendKeys("Qualitest");

            WebElement addressName = driver.findElement(By.id(ADDRESS));
            addressName.sendKeys("Negev11");

            WebElement addressName2 = driver.findElement(By.id(ADDRESS2));
            addressName2.sendKeys("BANK_HAPOALIM");

            Select country = new Select(driver.findElement(By.id(COUNTRY)));
            country.selectByVisibleText("Israel");

            WebElement state = driver.findElement(By.id(STATE));
            state.sendKeys("Asia");

            WebElement city = driver.findElement(By.id(CITY));
            city.sendKeys("Tel_Aviv");

            WebElement zipCode = driver.findElement(By.id(ZIP_CODE));
            zipCode.sendKeys("456846");

            WebElement phone = driver.findElement(By.id(MOBILE_NUMBER));
            phone.sendKeys("0524700002");
            Thread.sleep(1000);

            WebElement createAccount = driver.findElement(By.xpath(CREATE_ACCOUNT));
            createAccount.click();


            WebElement e = driver.findElement(By.xpath(ACCOUNT_CREATED_MASSAGE));
            String actualText1 = e.getText();
            logger.info("Account Created Message: {}",actualText1);
            assertEquals(actualText1, "ACCOUNT CREATED!");


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



        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}",  e.getMessage());

        } catch (TimeoutException e) {
            logger.error("Timeout occurred: {}",  e.getMessage());

        } catch (AssertionError e) {
            logger.error("Assertion failed: {}", e.getMessage());

        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
        }
    }


    @Test
    public void SearchProduct(){

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
            logger.info("All Products Message: {}" , actualText3);

            WebElement phone = driver.findElement(By.id(SEARCH_PRODUCTS));
            phone.sendKeys("Blue Top");

            WebElement submit = driver.findElement(By.id(SUBMIT_SEARCH));
            submit.click();

            WebElement ele4 = driver.findElement(By.xpath(SEARCHED_PRODUCTS));
            String actualText4 = ele4.getText();
            logger.info("Searched Products Message: {}" , actualText4);
            assertEquals(actualText4, "SEARCHED PRODUCTS");

        } catch (Exception e) {

            logger.info("error : " , e);
        }
    }
}
