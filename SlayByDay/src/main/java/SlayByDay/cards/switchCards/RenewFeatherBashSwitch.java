package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import SlayByDay.patches.cards.BackToDeckOnPlayPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RenewFeatherBashSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Renew", "FeatherBash", 1, 0, 0, 0, 0, 0, 2, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("FeatherBash", "Renew", 1, 0, 2, 0, 2, 0, 2, 1,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Renew";
    }
    public String passionCardID() {
        return "FeatherBash";
    }

    public RenewFeatherBashSwitch(String switchID) {
        super("SlayByDay:RenewFeatherBash", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, RenewFeatherBashSwitch.class);

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

    @Override
    public void triggerWhenDrawn() {
        if (TheMedium.Reason_Mode) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        }
    }

    public RenewFeatherBashSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Renew":
                BackToDeckOnPlayPatch.toBePutBackInDeck.set(this, true);
                BackToDeckOnPlayPatch.inRandomSpot.set(this, true);
                break;
            case "FeatherBash":
                for (int i=0; i < this.magicNumber; i++) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                }

                // This uses the same action that Pummel uses. We could also write our own, near identical, action for this instead.
                for (int i=0; i < this.magicNumber; i++) {
                    AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                }

                break;
        }
    }
}
