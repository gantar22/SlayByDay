package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import SlayByDay.powers.FinalityPower;
import SlayByDay.powers.InfinityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InfinityFinalitySwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Infinity", "Finality", 3, 1, 0, 0, 0, 0, 4, 0,
                    CardType.POWER, CardTarget.SELF, false, false, false, false),

            new switchCard("Finality", "Infinity", 3, 1, 0, 0, 0, 0, 4, 0,
                    CardType.POWER, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "Infinity";
    }
    public String passionCardID() {
        return "Finality";
    }

    public InfinityFinalitySwitch(String switchID) {
        super("SlayByDay:InfinityFinality", "None", null, 0, "None", CardType.POWER,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.SELF, InfinityFinalitySwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }
    }

    public InfinityFinalitySwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Infinity":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InfinityPower(p, p, this.magicNumber)));
                break;
            case "Finality":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FinalityPower(p, p, this.magicNumber)));
                break;
        }
    }
}
