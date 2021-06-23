import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

public class LoginPage {
    @FindBy(css = ".form-group label")
    private List<WebElement> playerLabels;

    @FindBy(css = ".form-group .form-control")
    private List<WebElement> playerNameInput;

    @FindBy(css = "button.btn.btn-primary")
    private WebElement submitButton;

    private SoftAssertions softly;

    private WebDriver webDriver;
    private String player1;
    private String player2;

    LoginPage(WebDriver webDriver,SoftAssertions softly,String player1,String player2){
        this.webDriver = webDriver;
        this.player1 = player1;
        this.player2 = player2;
        this.softly = softly;
        PageFactory.initElements(webDriver, this);
    }

    LoginPage validateLoginLabels(){
        softly.assertThat(playerLabels.get(0).getText()).as(
                String.format("Player1 label - validated")
        ).isEqualTo("Name Player1");
        softly.assertThat(playerLabels.get(1).getText()).as(
                String.format("Player2 label - validated")
        ).isEqualTo("Name Player2");
        return this;
    }
    LoginPage inputPlayerNames(){
        playerNameInput.get(0).sendKeys(player1);
        playerNameInput.get(1).sendKeys(player2);
        return this;
    }
    GamePage submitChoice(){
        submitButton.click();
        return new GamePage(webDriver,softly,player1,player2);
    }
}
