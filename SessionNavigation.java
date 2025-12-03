package cms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SessionNavigation {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private boolean isLoggedIn() {
        return driver.findElements(By.className("account")).size() > 0;
    }

    private void login() {
        if (!isLoggedIn()) {
            driver.get("http://demowebshop.tricentis.com/login");
            driver.findElement(By.id("Email")).sendKeys("cmsautomation2025@gmail.com");
            driver.findElement(By.id("Password")).sendKeys("Cms@12345");
            driver.findElement(By.cssSelector("input.login-button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("account")));
        }
    }

    private void logout() {
        if (driver.findElements(By.linkText("Log out")).size() > 0) {
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
            logoutLink.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log in")));
        }
    }

    private void ensureLoginPageVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
    }

    @Test
    public void verifyLoginStateOnRefresh() {
        login();
        driver.navigate().refresh();
        Assert.assertTrue(driver.findElements(By.className("account")).size() > 0,
                "User should still be logged in after refresh");
    }
    



    @Test
    public void verifyLoginRequiredForContactList() {
        driver.get("http://demowebshop.tricentis.com/logoff"); 
        driver.get("http://demowebshop.tricentis.com/contactus");
        ensureLoginPageVisible();
        Assert.assertTrue(driver.findElements(By.id("Email")).size() > 0,
                "Contact page should require login if not logged in");
    }

    @Test
    public void verifyCannotGoBackToContactListAfterLogout() {
        login();
        driver.get("http://demowebshop.tricentis.com/contactus");
        logout();
        driver.navigate().back();
        ensureLoginPageVisible();
        Assert.assertTrue(driver.findElements(By.id("Email")).size() > 0,
                "User should not see contact page after logout and back navigation");
    }
}