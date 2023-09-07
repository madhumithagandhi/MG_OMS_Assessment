package StepDefinitions;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class StepDefs {

    WebDriver driver;
    WebElement webElement = null;
    String emailIDText = "";


    @Given("Opens Browser")
    public void opens_browser() throws Exception {
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    @Given("Launchs URL")
    public void launchs_url() throws Exception {
        driver.navigate().to("http://www.way2automation.com/angularjs-protractor/webtables/");
    }

    @Given("Checks if in User List Table Page")
    public void checks_if_in_user_list_table_page() throws Exception {
        assertTrue(driver.findElement(By.xpath("/html/body/table/thead/tr[3]")).isDisplayed());
        Thread.sleep(3000);
    }

    @When("Hits Add button")
    public void hits_add_button() throws Exception {
        driver.findElement(By.xpath("//button[contains(text(),'Add User')]")).click();
    }

    @When("Enters user details including \"([^\"]*)\" and \"([^\"]*)\"$")
    public void enters_user_details_including_and(String firstName, String lastName) throws Exception {

        //generate random ID for username
        String uniqueID = RandomStringUtils.random(5, true, false);
        emailIDText = "WebTbls" + uniqueID + "@mail.com";

        //enter required details for add user
        driver.findElement(By.xpath("//input[@name='FirstName']")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys(lastName);
        driver.findElement(By.xpath("//input[@name='UserName']")).sendKeys(uniqueID);
        driver.findElement(By.xpath("//input[@name='Password']")).sendKeys("password");

        //randomise company selection
        int pickCompanyOption = new Random().nextBoolean() ? 1 : 2;
        String xpathXpression = "//tbody/tr[5]/td[2]/label[" + pickCompanyOption + "]";
        driver.findElement(By.xpath(xpathXpression)).click();

        //randomise role selection
        Select se = new Select(driver.findElement(By.xpath("//*[contains(@name, 'RoleId')]")));
        List<WebElement> l = se.getOptions();
        Random random = new Random();
        Integer rand = random.nextInt(l.size() - 2);
        Select drpRole = new Select(driver.findElement(By.xpath("//*[contains(@name, 'RoleId')]")));
        drpRole.selectByIndex(rand+1);

        driver.findElement(By.xpath("//input[contains(@name, 'Email')]")).sendKeys(emailIDText);
        webElement = driver.findElement(By.xpath("//input[contains(@name, 'Mobilephone')]"));
        driver.findElement(By.xpath("//input[contains(@name, 'Mobilephone')]")).sendKeys("0112233445");
        Thread.sleep(5000);
    }

    @When("Hits save button")
    public void hits_save_button() throws InterruptedException {
        webElement.sendKeys(Keys.TAB, Keys.TAB, Keys.ENTER);
        Thread.sleep(5000);
    }

    @Then("Details should appear on the table")
    public void details_should_appear_on_the_table() throws Exception {
        String testText = "qwertyasdfgzxcvb";
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + emailIDText + "')]"));
        assertTrue("Entry for User Details not found in the Web Users Table!", list.size() > 0);
        Thread.sleep(5000);
    }

    @AfterStep
    public void takeSnapShot(Scenario scenario){
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"image/png", "image");
    }

    @After
    public void cleanUp(){
        driver.close();
        driver.quit();
    }
}

















