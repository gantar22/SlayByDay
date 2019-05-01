package SlayByDay.cards.switchCards;

import SlayByDay.actions.RemoveRandomDebuffsAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PurgeTheSoulReadiedStanceSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("PurgeTheSoul", "ReadiedStance", 1, 0, 0, 0, 0, 0, 1, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("ReadiedStance", "PurgeTheSoul", 1, 0, 0, 0, 0, 0, 2, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "PurgeTheSoul";
    }
    public String passionCardID() {
        return "ReadiedStance";
    }

    public PurgeTheSoulReadiedStanceSwitch(String switchID) {
        super("SlayByDay:PurgeTheSoulReadiedStance", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, PurgeTheSoulReadiedStanceSwitch.class);

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

    public PurgeTheSoulReadiedStanceSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "PurgeTheSoul":
                AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffsAction(p, this.magicNumber));
                break;
            case "ReadiedStance":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
                break;
        }
    }
}
