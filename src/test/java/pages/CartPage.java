package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Login method
    public void login(String email, String password) {
        driver.findElement(By.className("ico-login")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.login-button")).click();
    }

    // Add a single item from Books category
    public void goToBooksAndAddSingleItem() {
        driver.findElement(By.linkText("Books")).click();
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
        addButton.click();
        wait.until(d -> getCartQuantity() >= 1);
    }

    // Add multiple items
    public void addMultipleItems(int count) {
        driver.findElement(By.linkText("Books")).click();
        List<WebElement> addButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input[value='Add to cart']")));

        int addedCount = 0;
        for (int i = 0; i < count && i < addButtons.size(); i++) {
            WebElement button = addButtons.get(i);
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                addedCount++;
                int finalAddedCount = addedCount;
                wait.until(d -> getCartQuantity() == finalAddedCount);
            }
        }
    }

    
    public int getCartQuantity() {
        WebElement cartQty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-qty")));
        String qtyText = cartQty.getText(); 
        return Integer.parseInt(qtyText.replaceAll("[()]", ""));
    }
}