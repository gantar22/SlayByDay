package SlayByDay.cards.switchCards;

import SlayByDay.actions.PunishmentAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PunishmentFurySwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Punishment", "Fury", 2, 0, 2, 1, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false),

            new switchCard("Fury", "Punishment", 2, 0, 0, 0, 0, 0, 4, 2,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Punishment";
    }
    public String passionCardID() {
        return "Fury";
    }

    public PunishmentFurySwitch(String switchID) {
        super("SlayByDay:PunishmentFury", "None", null, 0, "None", CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.NONE, PunishmentFurySwitch.class);

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

    public PunishmentFurySwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Punishment":
                AbstractDungeon.actionManager.addToBottom(new PunishmentAction(m, p, this));
                break;
            case "Fury":
                AbstractPower Strength = p.getPower(StrengthPower.POWER_ID);
                int strength_total = Strength.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, Strength));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, strength_total * this.magicNumber, this.damageTypeForTurn)));
                break;
        }
    }
}
