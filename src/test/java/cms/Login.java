package cms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Login extends BaseTest {

    // Locators
    private By loginLink = By.className("ico-login");
    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By loginButton = By.cssSelector("input.login-button");
    private By logoutLink = By.className("ico-logout");
    private By errorMsg = By.cssSelector("div.validation-summary-errors");
    private By emailError = By.id("Email-error");

  
    public void goToLoginPage() {
        driver.findElement(loginLink).click();
    }

    // 1️.Verify login with valid credentials 
    @Test
    public void loginWithValidCredentials() {
        goToLoginPage();
        driver.findElement(emailField).sendKeys("cmsautomation2025@gmail.com");
        driver.findElement(passwordField).sendKeys("Cms@12345");
        driver.findElement(loginButton).click();

        Assert.assertTrue(driver.findElement(logoutLink).isDisplayed(),
                "Login Failed with valid credentials");
    }

    // 2️. Verify login with incorrect password 
    @Test
    public void loginWithInvalidPassword() {
        goToLoginPage();
        driver.findElement(emailField).sendKeys("cmsautomation2025@gmail.com");
        driver.findElement(passwordField).sendKeys("WrongPassword");
        driver.findElement(loginButton).click();

        String msg = driver.findElement(errorMsg).getText();
        Assert.assertTrue(msg.contains("Login was unsuccessful"),
                "Error message not shown for incorrect password");
    }

    // 3️.Verify login with empty fields 
    @Test
    public void loginWithEmptyFields() {
        goToLoginPage();
        driver.findElement(loginButton).click(); 

        String msg = driver.findElement(errorMsg).getText();
        Assert.assertTrue(msg.contains("Login was unsuccessful"),
                "Validation message not shown for empty fields");
    }

    // 4️. Verify login with invalid email format 
    
    @Test
    public void loginWithInvalidEmailFormat() {
        driver.findElement(By.className("ico-login")).click();

        
        driver.findElement(By.id("Email")).sendKeys("user.com");
        driver.findElement(By.id("Password")).sendKeys("Cms@12345");
        driver.findElement(By.cssSelector("input.login-button")).click();

        
        boolean isLogoutPresent = driver.findElements(By.className("ico-logout")).size() > 0;
        Assert.assertFalse(isLogoutPresent, "User should NOT be logged in with invalid email format!");

        
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Login"), "User did not remain on Login page!");
    }

    // 5️.Verify password field masks input UI Test
    @Test
    public void verifyPasswordIsMasked() {
        goToLoginPage();
        WebElement password = driver.findElement(passwordField);
        password.sendKeys("Cms@12345");

        String type = password.getAttribute("type");
        Assert.assertEquals(type, "password", "Password field is not masking input");
    }
}
