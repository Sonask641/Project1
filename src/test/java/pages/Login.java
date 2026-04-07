package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {

    WebDriver driver;

    // Locators used
    By loginLink = By.linkText("Log in");              // nav link on header
    By usernameField = By.id("Email");                // email input
    By passwordField = By.id("Password");             // password input
    By loginButton = By.xpath("//input[@value='Log in']"); // login button
    By logoutLink = By.linkText("Log out");           // logout link when logged in
    By errorMessage = By.cssSelector("div.validation-summary-errors"); // error block

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to Login page
    public void goToLoginPage() {
        driver.findElement(loginLink).click();
    }

    // Enter credentials and submit
    public void login(String username, String password) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // Check if Logout link is displayed
    public boolean isLogoutDisplayed() {
        try {
            WebElement logout = driver.findElement(logoutLink);
            return logout.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get error text if login fails
    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }
}