package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EditPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    
    public EditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

  
    public void login(String email, String password) {
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.login-button")).click();
    }

    
    public void goToAddressSection() {
        driver.findElement(By.linkText("My account")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Addresses"))).click();
    }

   
    public void clickEditOrAddAddress() {
        List<WebElement> editButtons = driver.findElements(By.cssSelector("input.button-2.edit-address-button"));

        if (!editButtons.isEmpty()) {
            js.executeScript("arguments[0].scrollIntoView(true);", editButtons.get(0));
            wait.until(ExpectedConditions.elementToBeClickable(editButtons.get(0))).click();
        } else {
            List<WebElement> addButtons = driver.findElements(By.cssSelector("input.button-1.add-address-button"));
            if (!addButtons.isEmpty()) {
                js.executeScript("arguments[0].scrollIntoView(true);", addButtons.get(0));
                wait.until(ExpectedConditions.elementToBeClickable(addButtons.get(0))).click();
            } else {
                throw new RuntimeException("No address buttons found!");
            }
        }
    }

    
    public void fillAddressForm(String firstName, String lastName, String email, String country,
                                String city, String address1, String zip, String phone) {
        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Address_FirstName")));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = driver.findElement(By.id("Address_LastName"));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        WebElement emailField = driver.findElement(By.id("Address_Email"));
        emailField.clear();
        emailField.sendKeys(email);

        new Select(driver.findElement(By.id("Address_CountryId"))).selectByVisibleText(country);

        WebElement cityField = driver.findElement(By.id("Address_City"));
        cityField.clear();
        cityField.sendKeys(city);

        WebElement addressField = driver.findElement(By.id("Address_Address1"));
        addressField.clear();
        addressField.sendKeys(address1);

        WebElement zipField = driver.findElement(By.id("Address_ZipPostalCode"));
        zipField.clear();
        zipField.sendKeys(zip);

        WebElement phoneField = driver.findElement(By.id("Address_PhoneNumber"));
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

   
    public void saveAddress() {
        driver.findElement(By.cssSelector("input.button-1.save-address-button")).click();
    }

    // Verify address updated
    public boolean isAddressUpdated(String firstName, String lastName, String city, String address1,
                                    String zip, String phone) {
        List<WebElement> addressBlocks = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.address-list div.address-item"))
        );

        for (WebElement addr : addressBlocks) {
            String text = addr.getText().replaceAll("\\s+", " "); // normalize spaces
            if (text.contains(firstName) && text.contains(lastName) &&
                text.contains(city) && text.contains(address1) &&
                text.contains(zip) && text.contains(phone)) {
                return true;
            }
        }
        return false;
    }
}