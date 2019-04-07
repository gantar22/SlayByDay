package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

import SlayByDay.relics.PlaceholderRelic;

public class PossessionExpulsionSwitch extends AbstractSwitchByModeCard {

    // Doesn't work
    // public static final String ID = SlayByDay.makeID("PossessionExpulsion");

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Possession", "Expulsion", 1, 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("Expulsion", "Possession", 1, 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false) );

    public PossessionExpulsionSwitch(String switchID) {
        super("SlayByDay:PossessionExpulsionSwitch", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.BASIC, CardTarget.NONE, PossessionExpulsionSwitch.class);

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

    public String reasonCardID() {
        return "Possession";
    }
    public String passionCardID() {
        return "Expulsion";
    }
}
