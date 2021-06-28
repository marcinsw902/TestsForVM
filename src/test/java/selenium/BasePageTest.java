package selenium;

import pageobjects.LoginPage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;


public class BasePageTest {

    private static SoftAssertions softly = new SoftAssertions();
    private static WebDriver webDriver;

    @BeforeTest
    @Parameters({"browser","path"})
    public static void prepareTestEnv(String browser, String path) throws Exception {
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


    @AfterClass
    public static void endingTests() {
        softly.assertAll();
        webDriver.close();
    }

}
