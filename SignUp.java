

package cms;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUp extends BaseTest {

    //  open the registration page
    public void goToRegisterPage() {
        driver.findElement(By.className("ico-register")).click();
    }

    @Test
    public void signUpWithValidInputs() {
        goToRegisterPage();  // Use the helper method
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("sona");
        driver.findElement(By.id("LastName")).sendKeys("sk");
        String uniqueEmail = "cmsautomation" + System.currentTimeMillis() + "@gmail.com";
        driver.findElement(By.id("Email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("Password")).sendKeys("Cms@12345");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Cms@12345");
        driver.findElement(By.id("register-button")).click();

        WebElement successMsg = driver.findElement(By.className("result"));
        Assert.assertTrue(successMsg.getText().contains("completed"), "Registration failed!");
    }




 // 2. Registration with already registered email

 @Test
 public void signUpWithExistingEmail() {
     goToRegisterPage();
     driver.findElement(By.id("FirstName")).sendKeys("Sona");
     driver.findElement(By.id("LastName")).sendKeys("Bazi");
     driver.findElement(By.id("Email")).sendKeys("cmsautomation2025@gmail.com"); // existing email
     driver.findElement(By.id("Password")).sendKeys("Cms@12345");
     driver.findElement(By.id("ConfirmPassword")).sendKeys("Cms@12345");
     driver.findElement(By.id("register-button")).click();

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
     WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
         By.cssSelector("div.validation-summary-errors li")
     ));

     System.out.println("Actual error message: " + errorMsg.getText());
     Assert.assertTrue(errorMsg.getText().contains("already exists"), "Expected error not displayed!");
 }

    // 3. Registration with blank fields
    @Test
    public void signUpWithBlankFields() {
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.id("register-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='FirstName']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='LastName']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='Email']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='Password']"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='ConfirmPassword']"))).isDisplayed());
    }

    // 4. Password and confirm password mismatch
    @Test
    public void signUpPasswordMismatch() {
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.id("FirstName")).sendKeys("sona");
        driver.findElement(By.id("LastName")).sendKeys("sk");
        String uniqueEmail = "cmsautomation" + System.currentTimeMillis() + "@gmail.com";
        driver.findElement(By.id("Email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("Password")).sendKeys("Cms@12345");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Cms@54321");
        driver.findElement(By.id("register-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmPasswordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='ConfirmPassword']")));
        Assert.assertTrue(confirmPasswordError.getText().contains("The password and confirmation password do not match"), "Mismatch error not displayed!");
    }

 // 5. Invalid email format
    @Test
    public void signUpInvalidEmail() {
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Sona");
        driver.findElement(By.id("LastName")).sendKeys("Bazi");
        driver.findElement(By.id("Email")).sendKeys("invalidemail"); // invalid email
        driver.findElement(By.id("Password")).sendKeys("Test@1234");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@1234");
        driver.findElement(By.id("register-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-valmsg-for='Email']")));
        Assert.assertEquals(emailError.getText(), "Wrong email"); // Update the expected error message
    }
}

