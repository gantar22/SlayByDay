package SlayByDay.cards.switchCards;

import SlayByDay.actions.RemoveRandomDebuffsAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntrapSkillfulDodgeSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Entrap", "SkillfulDodge", 1, 0, 0, 0, 0, 0, 1, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("SkillfulDodge", "Entrap", 1, 0, 0, 0, 5, 0, 2, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "Entrap";
    }
    public String passionCardID() {
        return "SkillfulDodge";
    }

    public EntrapSkillfulDodgeSwitch(String switchID) {
        super("SlayByDay:EntrapSkillfulDodge", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, EntrapSkillfulDodgeSwitch.class);

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

    public EntrapSkillfulDodgeSwitch() { this(null); }

    @Override
    public void triggerWhenDrawn() {
        if (TheMedium.Reason_Mode) {
            this.flash();
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var1.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var1.next();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 1, false), 1, true));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 1, false), 1, true));
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Entrap":
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
                break;
            case "SkillfulDodge":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                break;
        }
    }
}
