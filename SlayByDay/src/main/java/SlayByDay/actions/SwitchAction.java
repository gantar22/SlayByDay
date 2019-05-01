package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import SlayByDay.cards.switchCards.AbstractSwitchByModeCard;


// Source: https://github.com/Tempus/The-Disciple/blob/629718eee2905dd6eb794022106dace00c35efce/src/main/java/actions/SwitchAction.java
public class SwitchAction extends AbstractGameAction
{

    public AbstractSwitchByModeCard card;

    public SwitchAction(AbstractSwitchByModeCard card)
    {
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            CardCrawlGame.sound.play("HEAL_1");
            this.card.superFlash();
            this.card.lighten(false);
            this.card.switchCardInDeck();
            this.card.switchTo(this.card.switchID);
        }
        tickDuration();
        if (this.isDone) {
            CardCrawlGame.sound.play("CARD_OBTAIN");
        }
    }
}