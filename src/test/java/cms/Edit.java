package cms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;

public class Edit {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        driver.get("http://demowebshop.tricentis.com/");
    }

    @Test(priority = 1)
    public void loginTest() {
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys("cmsautomation2025@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Cms@12345");
        driver.findElement(By.cssSelector("input.login-button")).click();

        WebElement accountName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("account"))
        );
        Assert.assertEquals(accountName.getText(), "cmsautomation2025@gmail.com", "Login Failed!");
    }

    @Test(priority = 2)
    public void editAddressTest() {
        driver.findElement(By.linkText("My account")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Addresses"))).click();

       
        List<WebElement> editButtons = driver.findElements(By.cssSelector("input.button-2.edit-address-button"));

        if (editButtons.size() > 0) {
            js.executeScript("arguments[0].scrollIntoView(true);", editButtons.get(0));
            wait.until(ExpectedConditions.elementToBeClickable(editButtons.get(0))).click();
        } else {
            List<WebElement> addButtons = driver.findElements(By.cssSelector("input.button-1.add-address-button"));
            if (addButtons.size() > 0) {
                js.executeScript("arguments[0].scrollIntoView(true);", addButtons.get(0));
                wait.until(ExpectedConditions.elementToBeClickable(addButtons.get(0))).click();
            } else {
                throw new RuntimeException("No address buttons found! Cannot proceed.");
            }
        }

     
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Address_FirstName")));
        firstName.clear();
        firstName.sendKeys("sayana");

        WebElement lastName = driver.findElement(By.id("Address_LastName"));
        lastName.clear();
        lastName.sendKeys("s");

        WebElement email = driver.findElement(By.id("Address_Email"));
        email.clear();
        email.sendKeys("sayana2020@gmail.com");

        new Select(driver.findElement(By.id("Address_CountryId"))).selectByVisibleText("India");

        WebElement city = driver.findElement(By.id("Address_City"));
        city.clear();
        city.sendKeys("kerala");

        WebElement address1 = driver.findElement(By.id("Address_Address1"));
        address1.clear();
        address1.sendKeys("calicut");

        WebElement zip = driver.findElement(By.id("Address_ZipPostalCode"));
        zip.clear();
        zip.sendKeys("321321");

        WebElement phone = driver.findElement(By.id("Address_PhoneNumber"));
        phone.clear();
        phone.sendKeys("987651234");

      
        driver.findElement(By.cssSelector("input.button-1.save-address-button")).click();

     
        List<WebElement> addressBlocks = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.address-list div.address-item"))
        );

        boolean updated = false;
        for (WebElement addr : addressBlocks) {
            String text = addr.getText().replaceAll("\\s+", " "); // normalize spaces
            if (text.contains("sayana") && text.contains("s") &&
                text.contains("kerala") && text.contains("calicut") &&
                text.contains("321321") && text.contains("987651234")) {
                updated = true;
                break;
            }
        }

        Assert.assertTrue(updated, "Address not updated!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


