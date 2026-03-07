package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Login;
import base.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentials() {

        Login loginPage = new Login(driver);

        loginPage.goToLoginPage();
        loginPage.login("cmsautomation2025@gmail.com", "Cms@12345");

        Assert.assertTrue(loginPage.isLogoutDisplayed(),
                "Login failed with valid credentials");
    }

    @Test
    public void loginWithInvalidPassword() {

        Login loginPage = new Login(driver);

        loginPage.goToLoginPage();
        loginPage.login("cmsautomation2025@gmail.com", "WrongPassword");

        String error = loginPage.getErrorMessage();

        Assert.assertTrue(error.contains("Login was unsuccessful"),
                "Error message not displayed for invalid password");
    }

    @Test
    public void loginWithEmptyFields() {

        Login loginPage = new Login(driver);

        loginPage.goToLoginPage();
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();

        Assert.assertTrue(error.contains("Login was unsuccessful"),
                "Validation message not shown for empty fields");
    }
}