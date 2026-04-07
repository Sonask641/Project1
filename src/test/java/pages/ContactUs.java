package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactUs {

    private WebDriver driver;

    // Constructor
    public ContactUs(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to Contact Us page
    public void goToContactUs() {
        driver.findElement(By.linkText("Contact us")).click();
    }

    // Get the Full Name field
    public WebElement getNameField() {
        return driver.findElement(By.id("FullName"));
    }

    // Get the Email field
    public WebElement getEmailField() {
        return driver.findElement(By.id("Email"));
    }

    // Get the Enquiry textarea
    public WebElement getEnquiryField() {
        return driver.findElement(By.id("Enquiry"));
    }

    // Check alignment of two fields within a tolerance
    public boolean areFieldsAligned(int tolerance) {
        int nameWidth = getNameField().getSize().getWidth();
        int emailWidth = getEmailField().getSize().getWidth();
        return Math.abs(nameWidth - emailWidth) <= tolerance;
    }

    // Enter Unicode text into the Enquiry field
    public void enterUnicodeText(String text) {
        getEnquiryField().sendKeys(text);
    }

    // Get the value of the Enquiry field
    public String getEnquiryValue() {
        return getEnquiryField().getAttribute("value");
    }
}