package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Navigate to registration page
    public void goToRegisterPage() {
        driver.findElement(By.className("ico-register")).click();
    }

    // Fill registration form
    public void fillRegistrationForm(String firstName, String lastName, String email, String password, String confirmPassword, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            driver.findElement(By.id("gender-male")).click();
        } else {
            driver.findElement(By.id("gender-female")).click();
        }

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(confirmPassword);
    }

    // Submit registration
    public void submitRegistration() {
        driver.findElement(By.id("register-button")).click();
    }

    // Get success message
    public String getSuccessMessage() {
        WebElement successMsg = driver.findElement(By.className("result"));
        return successMsg.getText();
    }

    // Get error message for existing email
    public String getExistingEmailErrorMessage() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.validation-summary-errors li")
        ));
        return errorMsg.getText();
    }

    // Get field-specific validation error
    public String getFieldError(String fieldName) {
        WebElement fieldError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span[data-valmsg-for='" + fieldName + "']")
        ));
        return fieldError.getText();
    }
}