package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SessionPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SessionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void goToLoginPage() {
        driver.get("http://demowebshop.tricentis.com/login");
    }

    public void login(String email, String password) {
        if (!isLoggedIn()) {
            goToLoginPage();
            driver.findElement(By.id("Email")).sendKeys(email);
            driver.findElement(By.id("Password")).sendKeys(password);
            driver.findElement(By.cssSelector("input.login-button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("account")));
        }
    }

    public void logout() {
        if (driver.findElements(By.linkText("Log out")).size() > 0) {
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
            logoutLink.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log in")));
        }
    }

    public boolean isLoggedIn() {
        return driver.findElements(By.className("account")).size() > 0;
    }

    public void navigateToContactUs() {
        driver.get("http://demowebshop.tricentis.com/contactus");
    }

    public void navigateToLogoff() {
        driver.get("http://demowebshop.tricentis.com/logoff");
    }

    public void ensureLoginPageVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
    }
}