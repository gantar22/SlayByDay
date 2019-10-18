package SlayByDay.cards.switchCards;

import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import SlayByDay.powers.ClarityPower;
import SlayByDay.powers.FervorPower;
import SlayByDay.relics.Anima;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ClarityFervorSwitch extends AbstractSwitchByModeCard {


    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Clarity", "Fervor", 3, 1,0, 0, 0, 0, 5, 0,
                    CardType.POWER, CardTarget.SELF, false, false, false, false),

            new switchCard("Fervor", "Clarity", 3, 1, 0, 0, 0, 0, 5, 1,
                    CardType.POWER, CardTarget.SELF, false, false, false, false) );


    @Override
    public String reasonCardID() {
        return "Clarity";
    }

    @Override
    public String passionCardID() {
        return "Fervor";
    }

    public ClarityFervorSwitch(String switchID) {
        super("SlayByDay:ClarityFervor", "None", null, 0, "None", CardType.POWER,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, ClarityFervorSwitch.class);

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

    public ClarityFervorSwitch()
    {
        this(null);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Clarity":
                Anima.reason_bonus_counter += 5;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,
                        new ClarityPower(p,p,1)));
                break;
            case "Fervor":
                Anima.passion_bonus_counter += 5;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new FervorPower(p,p,1)));
                break;
        }
    }
}
