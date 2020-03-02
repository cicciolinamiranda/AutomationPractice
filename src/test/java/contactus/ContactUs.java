package contactus;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class ContactUs {

    private static final String URL = "http://automationpractice.com/index.php?controller=contact";
    private static final String ID_CONTACT = "id_contact";
    private static final String ID_EMAIL = "email";
    private static final String ID_ORDER = "id_order";
    private static final String ID_MESSAGE = "message";
    private static final String ID_FILE_UPLOAD = "fileUpload";
    private static final String ID_SUBMIT_MESSAGE = "submitMessage";

    private static final String LINK_TEXT_CONTACT_US = "Contact us";

    private static final String VALUE_CONTACT = "Customer Service";
    private static final String VALUE_EMAIL = "test@test.com";
    private static final String VALUE_ORDER_ID = "123412345";
    private static final String VALUE_MESSAGE = "This is a sample message for testing.";
    private static final String VALUE_FILE_PATH = "src/test/resources/sample_order_file.txt";

    private static final String VALUE_CONFIRMATION_MSG = "Your message has been successfully sent to our team.";
    private static final String VALUE_INVALID_EMAIL_MSG = "Invalid email address.";
    private static final String VALUE_MSG_BLANK = "The message cannot be blank.";

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
    public void contactUsSubmitMessage() {
        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1280, 772));
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ID_CONTACT)));
        }
        {
            Select dropdownContact = new Select(driver.findElement(By.id(ID_CONTACT)));
            dropdownContact.selectByVisibleText(VALUE_CONTACT);
        }
        driver.findElement(By.id(ID_EMAIL)).sendKeys(VALUE_EMAIL);
        driver.findElement(By.id(ID_ORDER)).sendKeys(VALUE_ORDER_ID);
        driver.findElement(By.id(ID_MESSAGE)).sendKeys(VALUE_MESSAGE);
        driver.findElement(By.id(ID_FILE_UPLOAD)).sendKeys(VALUE_FILE_PATH);

        driver.findElement(By.id(ID_SUBMIT_MESSAGE)).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li > .btn > span")));
        }
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(), is(VALUE_CONFIRMATION_MSG));
    }

    @Test
    public void contactUsCheckMandatoryFields() {
        driver.get(URL);
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LINK_TEXT_CONTACT_US)));
        }
        driver.findElement(By.linkText(LINK_TEXT_CONTACT_US)).click();
        driver.findElement(By.id(ID_EMAIL)).sendKeys("\"\"");
        driver.findElement(By.id(ID_SUBMIT_MESSAGE)).click();
        assertThat(driver.findElement(By.cssSelector(CSS_ERROR_MESSAGE_CONTAINER)).getText(), is(VALUE_INVALID_EMAIL_MSG));
        driver.findElement(By.id(ID_EMAIL)).sendKeys(VALUE_EMAIL);
        driver.findElement(By.id(ID_SUBMIT_MESSAGE)).click();
        assertThat(driver.findElement(By.cssSelector(CSS_ERROR_MESSAGE_CONTAINER)).getText(), is(VALUE_MSG_BLANK));
    }
}
