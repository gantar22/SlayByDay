package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TacticalRetreatBlitzSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("TacticalRetreat", "Blitz", 1, 0, 0, 0, 5, 3, 1, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("Blitz", "TacticalRetreat", 1, 0, 12, 3, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "TacticalRetreat";
    }
    public String passionCardID() {
        return "Blitz";
    }

    public TacticalRetreatBlitzSwitch(String switchID) {
        super("SlayByDay:TacticalRetreatBlitz", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, TacticalRetreatBlitzSwitch.class);

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

    public TacticalRetreatBlitzSwitch() { this(null); }

    @Override
    public void upgrade() {
        this.upgradeBaseCost(1);
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "TacticalRetreat":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                if (p.hasPower(VulnerablePower.POWER_ID)) {
                    if (p.getPower(VulnerablePower.POWER_ID).amount > this.magicNumber) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, -this.magicNumber, false), -this.magicNumber));
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
                    }
                }
                break;
            case "Blitz":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, false));
                AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(p, p, p.currentBlock));
                break;
        }
    }
}
