package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MagicDefenseSharpenFeathersSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("MagicDefense", "SharpenFeathers", 2, 1, 0, 0, 0, 0, 2, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false),

            new switchCard("SharpenFeathers", "MagicDefense", 2, 1, 0, 0, 0, 0, 5, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false) );

    public String reasonCardID() {
        return "MagicDefense";
    }
    public String passionCardID() {
        return "SharpenFeathers";
    }

    public MagicDefenseSharpenFeathersSwitch(String switchID) {
        super("SlayByDay:MagicDefenseSharpenFeathers", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, MagicDefenseSharpenFeathersSwitch.class);

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

    public MagicDefenseSharpenFeathersSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "MagicDefense":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
                break;
            case "SharpenFeathers":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
                break;
        }
    }
}
