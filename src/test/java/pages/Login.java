package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

    WebDriver driver;

    By loginLink = By.linkText("Log in");
    By usernameField = By.id("Email");
    By passwordField = By.id("Password");
    By loginButton = By.xpath("//input[@value='Log in']");
    By logoutLink = By.xpath("//a[contains(text(),'Log')]");
    By errorMessage = By.cssSelector("div.validation-summary-errors");

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLoginPage() {
        driver.findElement(loginLink).click();
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isLogoutDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutLink)
        ).isDisplayed();
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }
}