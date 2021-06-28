package selenium;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageobjects.LoginPage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;


public class BasePageTest {

    private SoftAssertions softly;
    private static WebDriver webDriver;

    @BeforeTest
    @Parameters({"browser","path","headless"})
    public static void prepareTestEnv(String browser, String path, Boolean headless) throws Exception {
        if(browser.equalsIgnoreCase("Chrome")){
            System.setProperty("webdriver.chrome.driver", path);
            webDriver = new ChromeDriver();
        }
		else if(browser.equalsIgnoreCase("Firefox")){
            System.setProperty("webdriver.gecko.driver",path);
            webDriver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("Edge")){
            System.setProperty("webdriver.edge.driver", path);
            webDriver = new EdgeDriver();
        }
        else{
            throw new Exception("Browser not included in implementation");
        }

        webDriver.get("http://lucstictactoe.herokuapp.com/");
        webDriver.manage().window().maximize();
    }

    @BeforeMethod
    public void init() {
        PageFactory.initElements(webDriver, this);
        softly = new SoftAssertions();
    }

    @Test
    @Parameters({"player1","player2"})
    public void winningGameForFirstPlayer(String player1, String player2) {
        LoginPage loginPage = new LoginPage(webDriver, softly, player1, player2);
        loginPage
                .validateTitle()
                .validateLoginLabels()
                .inputPlayerNames()
                .submitChoice()
                .checkHeader()
                .testWiningGame()
                .validateEndgameLabelAfterPlayer1Win()
                .validateAndClickAgainButton()
                .isScoreActualAfterPlayer1Win();

    }


    @AfterMethod
    public void endingTest() {
        softly.assertAll();
    }

    @AfterClass
    public void endingAllTests() {
        webDriver.close();
    }
}
