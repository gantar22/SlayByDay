package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import SlayByDay.powers.DevilishLuckPower;
import SlayByDay.powers.PowerUpPower;
import SlayByDay.powers.PrecisionPower;
import SlayByDay.powers.SplashDamagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PrecisionDevilishLuckSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Precision", "DevilishLuck", 1, 1, 0, 0, 0, 0, 10, 10,
                    CardType.POWER, CardTarget.SELF, false, false, false, false),

            new switchCard("DevilishLuck", "Precision", 1, 1, 0, 0, 0, 0, 10, 10,
                    CardType.POWER, CardTarget.SELF, false, false, false, false) );

    public PrecisionDevilishLuckSwitch() {this(null);}
    public PrecisionDevilishLuckSwitch(String switchID) {
        super("SlayByDay:PrecisionDevilishLuck", "None", null, 0, "None", CardType.POWER,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.SELF, PrecisionDevilishLuckSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
            // determinism gang disapproves
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }

    }

    public String reasonCardID() {
        return "Precision";
    }
    public String passionCardID() {
        return "DevilishLuck";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Precision":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PrecisionPower(p, p, this.magicNumber), this.magicNumber));
                break;
            case "DevilishLuck":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DevilishLuckPower(p, p, this.magicNumber), this.magicNumber));
                break;
        }
    }
}
