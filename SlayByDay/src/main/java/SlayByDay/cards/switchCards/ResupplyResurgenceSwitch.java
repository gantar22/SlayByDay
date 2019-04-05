package SlayByDay.cards.switchCards;

import SlayByDay.actions.ResupplyAction;
import SlayByDay.actions.ResurgenceAction;
import SlayByDay.characters.TheModal;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResupplyResurgenceSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Resupply", "Resurgence", -1, 0, 0, 0, 0, 0, 1, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false),

            new switchCard("Resurgence", "Resupply", -1, 0, 0, 0, 0, 0, 1, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false) );

    public String reasonCardID() {
        return "Resupply";
    }
    public String passionCardID() {
        return "Resurgence";
    }

    public ResupplyResurgenceSwitch(String switchID) {
        super("SlayByDay:ResupplyResurgence", "None", null, 0, "None", CardType.SKILL,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.NONE, ResupplyResurgenceSwitch.class);

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

    public ResupplyResurgenceSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        switch (this.currentID) {
            case "Resupply":
                AbstractDungeon.actionManager.addToBottom(new ResupplyAction(p, magicNumber, upgraded, freeToPlayOnce, energyOnUse));
                break;
            case "Resurgence":
                AbstractDungeon.actionManager.addToBottom(new ResurgenceAction(p, magicNumber, upgraded, freeToPlayOnce, energyOnUse));
                break;
        }
    }
}
