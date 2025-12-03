package cms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AddItemsCart extends BaseTest {

   
    public void login(String email, String password) {
        driver.findElement(By.className("ico-login")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.login-button")).click();
    }

 
    public int getCartQuantity() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartQty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-qty")));
        String qtyText = cartQty.getText(); // e.g., "(2)"
        return Integer.parseInt(qtyText.replaceAll("[()]", ""));
    }

    // 1. Add single item to cart
    @Test
    public void addSingleItemToCart() {
        login("testuserdemo@gmail.com", "Test@123");
        driver.findElement(By.linkText("Books")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
        addButton.click();

        wait.until(d -> getCartQuantity() == 1);

        Assert.assertEquals(getCartQuantity(), 1, "Item not added to cart!");
    }

    // 2. Add multiple items to cart
    @Test
    public void addMultipleItemsToCart() {
        login("testuserdemo@gmail.com", "Test@123");
        driver.findElement(By.linkText("Books")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> addButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input[value='Add to cart']")));

        int expectedQty = 0;

        for (int i = 0; i < 2 && i < addButtons.size(); i++) {
            WebElement button = addButtons.get(i);
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                expectedQty++;
                // Wait until cart updates
                int finalExpectedQty = expectedQty;
                wait.until(d -> getCartQuantity() == finalExpectedQty);
            }
        }

        Assert.assertEquals(getCartQuantity(), 2, "Multiple items not added to cart!");
    }

    // 3. Add item without login
    @Test
    public void addItemWithoutLogin() {
        driver.findElement(By.linkText("Books")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
        addButton.click();

        wait.until(d -> getCartQuantity() == 1);

        Assert.assertEquals(getCartQuantity(), 1, "Item not added to cart!");
    }
}


