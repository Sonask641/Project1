package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {

    WebDriver driver;

    // Constructor
    public Login(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By loginLink = By.className("ico-login");
    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By loginButton = By.cssSelector("input.login-button");
    private By logoutLink = By.className("ico-logout");
    private By errorMsg = By.cssSelector("div.validation-summary-errors");

    // Navigate to login page
    public void goToLoginPage() {
        driver.findElement(loginLink).click();
    }

    // Login action
    public void login(String email, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        driver.findElement(loginButton).click();
    }

    // Check logout visible (successful login)
    public boolean isLogoutDisplayed() {
        return driver.findElement(logoutLink).isDisplayed();
    }

    // Get error message
    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}
