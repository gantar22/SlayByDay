package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClawSlashClawSmashSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("ClawSlash", "ClawSmash", 1, 0, 5, 1, 0, 0, 2, 1,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false),

            new switchCard("ClawSmash", "ClawSlash", 1, 0, 8, 4, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "ClawSlash";
    }
    public String passionCardID() {
      return "ClawSmash";
    }

    public ClawSlashClawSmashSwitch(String switchID) {
        super("SlayByDay:ClawSlashClawSmash", "None", null, 0, "None", CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, ClawSlashClawSmashSwitch.class);

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

    public ClawSlashClawSmashSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "ClawSlash":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
                break;
            case "ClawSmash":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
        }
    }
}
