package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DefensiveManeuversOffensiveRushSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("DefensiveManeuvers", "OffensiveRush", 1, 0, 0, 0, 6, 2, 2, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("OffensiveRush", "DefensiveManeuvers", 1, 0, 3, 0, 0, 0, 4, 1,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "DefensiveManeuvers";
    }
    public String passionCardID() {
        return "OffensiveRush";
    }

    public DefensiveManeuversOffensiveRushSwitch(String switchID) {
        super("SlayByDay:DefensiveManeuversOffensiveRush", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.BASIC, AbstractCard.CardTarget.NONE, DefensiveManeuversOffensiveRushSwitch.class);

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

    public DefensiveManeuversOffensiveRushSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "DefensiveManeuvers":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
                break;
            case "OffensiveRush":
                // This uses the same action that Pummel uses. We could also write our own, near identical, action for this instead.
                for (int i=0; i < magicNumber; i++) {
                    AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
        }
    }
}
