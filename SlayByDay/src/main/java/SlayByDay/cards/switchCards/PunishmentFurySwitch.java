package SlayByDay.cards.switchCards;

import SlayByDay.actions.PunishmentAction;
import SlayByDay.characters.TheModal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PunishmentFurySwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Punishment", "Fury", 2, 2, 1, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false),

            new switchCard("Fury", "Punishment", 2, 4, 6, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Punishment";
    }
    public String passionCardID() {
        return "Fury";
    }

    public PunishmentFurySwitch(String switchID) {
        super("SlayByDay:PunishmentFury", "None", null, 0, "None", CardType.SKILL,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.NONE, PunishmentFurySwitch.class);

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

    public PunishmentFurySwitch() { this(null); }

    @Override
    public void upgrade() {
        this.upgradeBaseCost(0);
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Punishment":
                AbstractDungeon.actionManager.addToBottom(new PunishmentAction(m, p, this));
                break;
            case "Fury":
                // todo - use magicNum instead of damage, because we don't want it affected by strength.
                // OR check how Heavy Blade modifies this? It's just hardcoded into AbstractCard
                break;
        }
    }
}
