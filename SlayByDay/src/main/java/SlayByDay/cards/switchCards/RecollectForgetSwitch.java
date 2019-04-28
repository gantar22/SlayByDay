package SlayByDay.cards.switchCards;

import SlayByDay.actions.RecollectAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecollectForgetSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Recollect", "Forget", 1, 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("Forget", "Recollect", 1, 1, 0, 0, 0, 0, 2, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "Recollect";
    }
    public String passionCardID() {
        return "Forget";
    }

    public RecollectForgetSwitch(String switchID) {
        super("SlayByDay:RecollectForget", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, RecollectForgetSwitch.class);

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

    public RecollectForgetSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Recollect":
                AbstractDungeon.actionManager.addToBottom(new RecollectAction(false));
                break;
            case "Forget":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, true));
                break;
        }
    }
}
