package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SkimOverSacrificalCastingSwitch extends AbstractSwitchByModeCard{


    public List<AbstractSwitchByModeCard.switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("SkimOver", "SacrificialCasting", 1,1 , 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("SacrificialCasting", "SkimOver", 1, 1, 0, 0, 0, 0,  1,  0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false) );

    public String reasonCardID() {
        return "SkimOver";
    }
    public String passionCardID() {
        return "SacrificialCasting";
    }

    public SkimOverSacrificalCastingSwitch(String switchID) {
        super("SlayByDay:SkimOverSacrificialCasting", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE, SkimOverSacrificalCastingSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            this.validateSwitchCardMode(true);
        }
    }

    public SkimOverSacrificalCastingSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID)
        {
            case "SkimOver":
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
                AbstractDungeon.actionManager.addToBottom(new ShowCardAction(AbstractDungeon.player.hand.getTopCard()));

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if(isDone) return;
                        int mana = AbstractDungeon.player.hand.getTopCard().costForTurn;
                        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2 * mana));
                        isDone = true;
                        AbstractCard c = AbstractDungeon.player.hand.getTopCard();
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                });
                break;
            case"SacrificialCasting":
                AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p,p,1,true));
                AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));
                break;
        }
    }
}
