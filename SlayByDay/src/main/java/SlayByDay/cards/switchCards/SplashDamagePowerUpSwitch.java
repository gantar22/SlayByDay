package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheModal;
import SlayByDay.powers.PowerUpPower;
import SlayByDay.powers.SplashDamagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplashDamagePowerUpSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("SplashDamage", "PowerUp", 2, 0, 0, 0, 0, 6, 0,
                    CardType.POWER, CardTarget.SELF, false, false, false, false),

            new switchCard("PowerUp", "SplashDamage", 2, 0, 0, 0, 0, 1, 0,
                    CardType.POWER, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "SplashDamage";
    }
    public String passionCardID() {
        return "PowerUp";
    }

    public SplashDamagePowerUpSwitch(String switchID) {
        super("SlayByDay:SplashDamagePowerUp", "None", null, 0, "None", CardType.POWER,
                TheModal.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, SplashDamagePowerUpSwitch.class);

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

    public SplashDamagePowerUpSwitch() { this(null); }

    @Override
    public void upgrade() {
        this.upgradeBaseCost(1);
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "SplashDamage":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SplashDamagePower(p, p, this.magicNumber)));
                break;
            case "PowerUp":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PowerUpPower(p, p, this.magicNumber)));
                break;
        }
    }
}
