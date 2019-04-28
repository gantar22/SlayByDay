package SlayByDay.cards.switchCards;

import SlayByDay.actions.ReinvestAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReinvestSuddenStrikeSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Reinvest", "SuddenStrike", 0, 0, 0, 0, 0, 0, 1, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("SuddenStrike", "Reinvest", 0, 0, 7, 3, 0, 0, 1, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Reinvest";
    }
    public String passionCardID() {
        return "SuddenStrike";
    }

    public ReinvestSuddenStrikeSwitch(String switchID) {
        super("SlayByDay:ReinvestSuddenStrike", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, ReinvestSuddenStrikeSwitch.class);

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

    public ReinvestSuddenStrikeSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Reinvest":
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ReinvestAction(this.magicNumber));
                break;
            case "SuddenStrike":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
                break;
        }
    }
}
