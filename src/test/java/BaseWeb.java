import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class BaseWeb {

    WebDriver driver = new ChromeDriver();

    public WebDriver init(){
        driver.get("http://automationexercise.com");
        driver.manage().window().maximize();
        return driver;
    }
}

