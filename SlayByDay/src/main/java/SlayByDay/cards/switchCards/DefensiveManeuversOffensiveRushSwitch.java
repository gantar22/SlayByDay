package SlayByDay.cards.switchCards;

import SlayByDay.actions.SwitchAction;
import SlayByDay.characters.TheModal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DefensiveManeuversOffensiveRushSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("DefensiveManeuvers", "OffensiveRush", 1, 0, 0, 6, 0, 2, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("OffensiveRush", "DefensiveManeuvers", 1, 3, 0, 0, 0, 4, 1,
                    CardType.ATTACK, CardTarget.ENEMY, true, false, false, false) );


    public DefensiveManeuversOffensiveRushSwitch(String switchID) {
        super("SlayByDay:DefensiveManeuversOffensiveRush", "None", null, 0, "None", CardType.SKILL,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.BASIC, AbstractCard.CardTarget.NONE, DefensiveManeuversOffensiveRushSwitch.class);

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

    public DefensiveManeuversOffensiveRushSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "DefensiveManeuvers":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
                break;
            case "OffensiveRush":
                // Todo - multi damage. Might need custom action, as in Pummel/Whirlwind/FiendFire
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
    }
}
