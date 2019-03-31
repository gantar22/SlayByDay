package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;


// Source: ExhumeAction.class
public class RecollectAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final boolean upgrade = false;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> recollections = new ArrayList<>();

    public RecollectAction(boolean upgrade) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator c;
        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.exhaustPile.size() == 1) {
                if (((AbstractCard)this.p.exhaustPile.group.get(0)).cardID.equals("Recollect")) {
                    this.isDone = true;
                } else {
                    AbstractCard _c = this.p.exhaustPile.getTopCard();
                    _c.unfadeOut();
                    this.p.drawPile.addToRandomSpot(_c);
                    if (AbstractDungeon.player.hasPower("Corruption") && _c.type == AbstractCard.CardType.SKILL) {
                        _c.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(_c);
                    if (this.upgrade && _c.canUpgrade()) {
                        _c.upgrade();
                    }

                    _c.unhover();
                    _c.fadingOut = false;
                    this.isDone = true;
                }
            } else {
                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    derp = (AbstractCard)c.next();
                    derp.stopGlowing();
                    derp.unhover();
                    derp.unfadeOut();
                }

                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    derp = (AbstractCard)c.next();
                    if (derp.cardID.equals("Recollect")) {
                        c.remove();
                        this.recollections.add(derp);
                    }
                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.recollections);
                    this.recollections.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                    derp = (AbstractCard)c.next();
                    this.p.drawPile.addToRandomSpot(derp);
                    if (AbstractDungeon.player.hasPower("Corruption") && derp.type == AbstractCard.CardType.SKILL) {
                        derp.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(derp);
                    if (this.upgrade && derp.canUpgrade()) {
                        derp.upgrade();
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.exhaustPile.group.addAll(this.recollections);
                this.recollections.clear();

                for(c = this.p.exhaustPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard)c.next();
                    derp.unhover();
                    derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecollectAction");
        TEXT = uiStrings.TEXT;
    }
}
