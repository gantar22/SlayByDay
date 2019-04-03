package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheModal;
import SlayByDay.powers.FlightPlayerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlyRoostSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Fly", "Roost", 2, 1, 0, 0, 0, 0, 1, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new switchCard("Roost", "Fly", 2, 1, 0, 0, 0, 0, 2, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Fly";
    }
    public String passionCardID() {
        return "Roost";
    }

    public FlyRoostSwitch(String switchID) {
        super("SlayByDay:FlyRoost", "None", null, 0, "None", CardType.SKILL,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.NONE, FlyRoostSwitch.class);

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

    public FlyRoostSwitch() { this(null); }

    public void applyPowers() {
        if (!TheModal.Reason_Mode && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            this.baseDamage = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount + this.magicNumber;
        }
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Fly":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlightPlayerPower(p, this.magicNumber), this.magicNumber));
                break;
            case "Roost":
                this.calculateCardDamage(m);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                break;
        }
    }
}
