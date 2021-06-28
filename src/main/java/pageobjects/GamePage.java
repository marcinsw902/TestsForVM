package pageobjects;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GamePage {

    @FindBy(css = "div.game > p")
    private WebElement whoseTurnLabel;

    @FindBy(css = "div.board > div.tile")
    private static List<WebElement> grids;

    @FindBy(css = "div.title > h1")
    private static WebElement ticTacToeLabel;

    @FindBy(css = "div.statistics > h2")
    private List<WebElement> statistics;

    private WebDriver webDriver;
    private String player1;
    private String player2;
    private SoftAssertions softly;

    private static final int MovesNeededForFirstPLayerToWin = 6;
    private static final int FIRSTPLAYERSCORE = 0;
    private static final int SECONDPLAYERSCORE = 1;
    private static final int TIES = 2;

    GamePage(WebDriver webDriver, SoftAssertions softly, String player1, String player2) {
        this.webDriver = webDriver;
        this.player1 = player1;
        this.player2 = player2;
        this.softly = softly;
        PageFactory.initElements(webDriver, this);
    }

    public GamePage checkHeader() {
        softly.assertThat(ticTacToeLabel.getText())
                .as(String.format("Game name - validated"))
                .isEqualTo("Tic Tac Toe");
        softly.assertThat(statistics.get(FIRSTPLAYERSCORE).getText())
                .as(String.format("Player1 statistics format - validated"))
                .isEqualTo(String.format("%s:0", player1));
        softly.assertThat(statistics.get(SECONDPLAYERSCORE).getText())
                .as(String.format("Player2 statistics format - validated"))
                .isEqualTo(String.format("%s:0", player2));
        softly.assertThat(statistics.get(TIES).getText())
                .as(String.format("Ties format - validated"))
                .isEqualTo("Ties: 0");
        return this;
    }

    public GamePage isScoreActualAfterPlayer1Win() {
        softly.assertThat(statistics.get(FIRSTPLAYERSCORE).getText())
                .as(String.format("Score - validated"))
                .isEqualTo(String.format("%s:1", player1));
        return this;
    }

    GamePage validateTurnLabel(String actualPlayer, int whichMove) {
        PageFactory.initElements(webDriver, this);
        softly.assertThat(whoseTurnLabel.getText())
                .as(String.format("ActualPlayerLabel for %d move - Validated", whichMove))
                .isEqualTo(String.format("Its %s's turn", actualPlayer));
        return this;
    }

    public EndGamePopup testWiningGame() {
        for (int i = 0; i <= MovesNeededForFirstPLayerToWin; i++) {
            if (i % 2 == 0 || i == 0)
                validateTurnLabel(player1, i);
            else
                validateTurnLabel(player2, i);
            grids.get(i).click();
        }
        return new EndGamePopup(webDriver, softly, player1, player2);
    }
}
