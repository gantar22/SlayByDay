package SlayByDay.cards.switchCards;

import SlayByDay.actions.RemoveRandomDebuffsAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReactiveMeasuresProactiveMeasuresSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("ReactiveMeasures", "ProactiveMeasures", 1, 0, 0, 0, 0, 0, 3, 2,
                    CardType.SKILL, CardTarget.ENEMY, false, false, false, false),

            new switchCard("ProactiveMeasures", "ReactiveMeasures", 1, 0, 8, 2, 9, 2, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "ReactiveMeasures";
    }
    public String passionCardID() {
        return "ProactiveMeasures";
    }

    public ReactiveMeasuresProactiveMeasuresSwitch(String switchID) {
        super("SlayByDay:ReactiveMeasuresProactiveMeasures", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, ReactiveMeasuresProactiveMeasuresSwitch.class);

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

    public ReactiveMeasuresProactiveMeasuresSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        m.flashIntent();
        m.createIntent();
        boolean not_attacking = m == null || m.intent != AbstractMonster.Intent.ATTACK && m.intent != AbstractMonster.Intent.ATTACK_BUFF && m.intent != AbstractMonster.Intent.ATTACK_DEBUFF && m.intent != AbstractMonster.Intent.ATTACK_DEFEND;
        switch (this.currentID) {
            case "ReactiveMeasures":
                if (not_attacking) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
                }
                break;
            case "ProactiveMeasures":
                if (!not_attacking) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                break;
        }
    }
}
