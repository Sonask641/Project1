package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Login;
import base.BaseTest;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentials() throws Exception {

        Login loginPage = new Login(driver);

        try {
            ExcelUtils.setExcelFile();

            String username = ExcelUtils.getCellData(1, 0);
            String password = ExcelUtils.getCellData(1, 1);

            loginPage.goToLoginPage();
            loginPage.login(username, password);

            Assert.assertTrue(loginPage.isLogoutDisplayed(),
                    "Login failed with valid credentials");

        } finally {
            ExcelUtils.closeWorkbook();
        }
    }

    @Test
    public void loginWithInvalidPassword() throws Exception {

        Login loginPage = new Login(driver);

        try {
            ExcelUtils.setExcelFile();

            String username = ExcelUtils.getCellData(2, 0);
            String password = ExcelUtils.getCellData(2, 1);

            loginPage.goToLoginPage();
            loginPage.login(username, password);

            String error = loginPage.getErrorMessage();

            Assert.assertTrue(
                    error.contains("Login was unsuccessful"),
                    "Error message not displayed for invalid password"
            );

        } finally {
            ExcelUtils.closeWorkbook();
        }
    }

    @Test
    public void loginWithEmptyFields() throws Exception {

        Login loginPage = new Login(driver);

        try {
            ExcelUtils.setExcelFile();

            String username = ExcelUtils.getCellData(3, 0);
            String password = ExcelUtils.getCellData(3, 1);

            loginPage.goToLoginPage();
            loginPage.login(username, password);

            String error = loginPage.getErrorMessage();

            Assert.assertTrue(
                    error.contains("Login was unsuccessful"),
                    "Validation message not shown for empty fields"
            );

        }
        finally {
            ExcelUtils.closeWorkbook();
        }
    }
}