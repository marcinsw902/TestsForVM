package pageobjects;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EndGamePopup {
    @FindBy(css = "div.screen > p")
    private WebElement endgameLabel;

    @FindBy(css = "div.screen > button.btn.btn-primary")
    private WebElement tryAgainButton;

    private WebDriver webDriver;

    private SoftAssertions softly;

    private String player1;

    private String player2;

    EndGamePopup(WebDriver webDriver,SoftAssertions softly,String player1,String player2){
        this.webDriver = webDriver;
        this.softly = softly;
        this.player1 = player1;
        this.player2 = player2;
        PageFactory.initElements(webDriver, this);
    }

    public EndGamePopup validateEndgameLabelAfterPlayer1Win(){
        softly.assertThat(endgameLabel.getText())
                .as(String.format("Popup label - validated"))
                .isEqualTo(String.format("You win %s !!", player1));
        return this;
    }
    public GamePage validateAndClickAgainButton(){
        softly.assertThat(tryAgainButton.getText())
                .as(String.format("New game button - validated"))
                .isEqualTo("Wanna try again?");
        tryAgainButton.click();
        return new GamePage(webDriver,softly,player1, player2);
    }


}
