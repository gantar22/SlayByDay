package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TranquilizePreyOnTheWeakSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Tranquilize", "PreyOnTheWeak", 1, 0, 3, 0, 0, 0, 3, 2,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false),

            new switchCard("PreyOnTheWeak", "Tranquilize", 1, 0, 9, 3, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Tranquilize";
    }
    public String passionCardID() {
        return "PreyOnTheWeak";
    }

    public TranquilizePreyOnTheWeakSwitch(String switchID) {
        super("SlayByDay:TranquilizePreyOnTheWeak", "None", null, 0, "None", CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, TranquilizePreyOnTheWeakSwitch.class);

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

    public TranquilizePreyOnTheWeakSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Tranquilize":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
                break;
            case "PreyOnTheWeak":
                int multiplier = m.hasPower(WeakPower.POWER_ID) ? 2 : 1;
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage * multiplier, this.damageTypeForTurn)));
                break;
        }
    }
}
