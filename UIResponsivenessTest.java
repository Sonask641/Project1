package cms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UIResponsivenessTest extends BaseTest {

    @Test(priority = 1)
    public void verifyFieldAlignmentOnContactForm() {
        driver.findElement(By.linkText("Contact us")).click();

        WebElement nameField = driver.findElement(By.id("FullName"));
        WebElement emailField = driver.findElement(By.id("Email"));
        

        int nameWidth = nameField.getSize().getWidth();
        int emailWidth = emailField.getSize().getWidth();

        int tolerance = 50; 

        boolean isAligned = Math.abs(nameWidth - emailWidth) <= tolerance;

        Assert.assertTrue(isAligned,
                "Form input fields are NOT aligned correctly. Width tolerance allowed: "
                        + tolerance + "px");
    }

    @Test(priority = 2)
    public void verifyUnicodeSupportInAddressField() {
        driver.findElement(By.linkText("Contact us")).click();

        WebElement enquiry = driver.findElement(By.id("Enquiry"));
        String unicodeText = "مرحبا வணக்கம் नमस्ते привет 你好"; 
        enquiry.sendKeys(unicodeText);

        String enteredValue = enquiry.getAttribute("value");

        Assert.assertTrue(enteredValue.contains("مرحبا"),
                "Unicode characters are NOT supported in Enquiry field!");
    }
}
