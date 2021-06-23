import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;


public class BasePageTest {

    private enum DriverType {
        CHROME, FIREFOX
    }

    private static SoftAssertions softly = new SoftAssertions();
    private static WebDriver webDriver;
    private static LoginPage loginPage;
    private static DriverType driverType = DriverType.CHROME;
    private final static String ChromePath = "drivers/chromedriver.exe";
    private final static String FirefoxPath = "drivers/geckodriver.exe";

    @BeforeClass
    public static void prepareTestEnv() throws Exception {
        switch (driverType) {
            case CHROME:
                    System.setProperty("webdriver.chrome.driver", ChromePath);
                    webDriver = new ChromeDriver();
//            case FIREFOX:
//                    System.setProperty("webdriver.gecko.driver", FirefoxPath);
//                    webDriver = new FirefoxDriver();
        }
        webDriver.get("http://lucstictactoe.herokuapp.com/");
        webDriver.manage().window().maximize();
    }

    @Before
    public void init() {
        PageFactory.initElements(webDriver, this);
    }

    @Test
    public void validateTitle() {
        softly.assertThat(webDriver.getTitle())
                .as(String.format("Site Title - validated"))
                .isEqualTo("Luc's Tic Tac Toe");
    }

    @Test
    public void winningGameForFirstPlayer() {
        loginPage = new LoginPage(webDriver, softly, "playerone", "playertwo");
        loginPage
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
        webDriver.quit();
    }

}
