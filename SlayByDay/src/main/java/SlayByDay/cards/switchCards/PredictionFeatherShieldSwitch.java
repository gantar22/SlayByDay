package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class PredictionFeatherShieldSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Prediction", "FeatherShield", 1, 0, 0, 0, 15, 5, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("FeatherShield", "Prediction", 1, 0, 0, 0, 9, 3, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "Prediction";
    }
    public String passionCardID() {
        return "FeatherShield";
    }

    public PredictionFeatherShieldSwitch(String switchID) {
        super("SlayByDay:PredictionFeatherShield", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, PredictionFeatherShieldSwitch.class);

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

    public PredictionFeatherShieldSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Prediction":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
                break;
            case "FeatherShield":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                break;
        }
    }
}
