package register;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class Register {

    private static final String URL = "http://automationpractice.com/index.php";

    private static final String ID_EMAIL_CREATE = "email_create";
    private static final String ID_SUBMIT_CREATE = "SubmitCreate";

    private static final String VALUE_EMAIL = "test@test.com";
    private static final String VALUE_INVALID_EMAIL_MSG = "Invalid email address.";
    private static final String VALUE_EMAIL_EXISTING = "An account using this email address has already been registered. Please enter a valid password or request a new one.";

    private static final String LINK_TEXT_SIGN_IN = "Sign In";

    private static final String CSS_ERROR_MESSAGE_CONTAINER = "ol > li";

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void registerExistingAccount() {
        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1280, 772));
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LINK_TEXT_SIGN_IN)));
        }
        driver.findElement(By.linkText(LINK_TEXT_SIGN_IN)).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ID_EMAIL_CREATE)));
        }
        driver.findElement(By.id(ID_SUBMIT_CREATE)).click();
        assertThat(driver.findElement(By.cssSelector(CSS_ERROR_MESSAGE_CONTAINER)).getText(), is(VALUE_INVALID_EMAIL_MSG));
        driver.findElement(By.id(ID_EMAIL_CREATE)).sendKeys(VALUE_EMAIL);
        driver.findElement(By.id(ID_SUBMIT_CREATE)).click();
        assertThat(driver.findElement(By.cssSelector(CSS_ERROR_MESSAGE_CONTAINER)).getText(), is(VALUE_EMAIL_EXISTING));
    }
}
