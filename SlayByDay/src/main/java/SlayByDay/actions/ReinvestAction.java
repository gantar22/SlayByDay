package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class ReinvestAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private int numberOfCards;

    public ReinvestAction(int numberOfCards) {
        this.p = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() <= this.numberOfCards) {
                while (this.p.hand.size() > 0) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToDeck(c, true);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                String UI_header = get_UI_header();
                System.out.println("UI_header: " + UI_header);
                AbstractDungeon.handCardSelectScreen.open(UI_header, this.numberOfCards, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToDeck(c, true)) {
                    c = (AbstractCard)var1.next();
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    private String get_UI_header() {
        return TEXT[0];
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReinvestAction");
        TEXT = uiStrings.TEXT;
    }
}
