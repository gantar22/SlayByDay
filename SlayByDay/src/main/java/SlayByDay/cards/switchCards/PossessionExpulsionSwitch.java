package SlayByDay.cards.switchCards;

import SlayByDay.actions.SwitchAction;
import SlayByDay.characters.TheModal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

import SlayByDay.relics.PlaceholderRelic;
import static SlayByDay.SlayByDay.makeCardPath;

public class PossessionExpulsionSwitch extends AbstractSwitchByModeCard {

    // Doesn't work
    // public static final String ID = SlayByDay.makeID("PossessionExpulsion");

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Possession", "Expulsion", 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("Expulsion", "Possession", 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false) );

    public PossessionExpulsionSwitch(String switchID) {
        super("SlayByDay:PossessionExpulsionSwitch", "None", null, 0, "None", CardType.SKILL,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.BASIC, CardTarget.NONE, PossessionExpulsionSwitch.class);

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

    public PossessionExpulsionSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        PlaceholderRelic.Switch_Mode();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
