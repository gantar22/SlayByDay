package SlayByDay.cards.switchCards;

import SlayByDay.actions.SwitchAction;
import SlayByDay.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class PredictionFeatherShieldSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Prediction", "FeatherShield", 1, 0, 0, 15, 5, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("FeatherShield", "Prediction", 1, 0, 0, 9, 3, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );


    public PredictionFeatherShieldSwitch(String switchID) {
        super("SlayByDay:PredictionFeatherShield", "None", null, 0, "None", CardType.SKILL,
                TheDefault.Enums.COLOR_GRAY, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.NONE, PredictionFeatherShieldSwitch.class);
        // TODO - change this ^^ card color to porple

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

        AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
    }
}
