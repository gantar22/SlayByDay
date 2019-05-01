package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import SlayByDay.powers.FinalityPower;
import SlayByDay.powers.InfinityPower;
import SlayByDay.powers.PowerUpPower;
import SlayByDay.powers.SplashDamagePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpreadInfectionDoubleSweepSwitch extends AbstractSwitchByModeCard {


    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("SpreadInfection", "DoubleSweep", 1,0 , 4, 2, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false),

            new switchCard("DoubleSweep", "SpreadInfection", 1, 0, 6, 0, 0, 0,  1,  1,
                CardType.ATTACK, CardTarget.ALL_ENEMY, true, false, false, false) );

    public String reasonCardID() {
        return "SpreadInfection";
    }
    public String passionCardID() {
        return "DoubleSweep";
    }

    public SpreadInfectionDoubleSweepSwitch(String switchID) {
        super("SlayByDay:SpreadInfectionDoubleSweep", "None", null, 0, "None", CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.ENEMY, SpreadInfectionDoubleSweepSwitch.class);

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

    public SpreadInfectionDoubleSweepSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo.DamageType dt = this.damageTypeForTurn;
        int d = this.damage;
        switch (this.currentID) {
            case "SpreadInfection":
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        System.out.println("running the new action: " + isDone);
                        if(isDone) return;
                        m.damage(new DamageInfo(p,d,dt));
                        System.out.println("health: " + m.currentHealth + " is dying: " + m.isDying);
                        if((m.isDying || m.currentHealth <= 0) && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                        {
                            System.out.println("The monster: " + m + "is dying");
                            ArrayList<AbstractPower> ps = m.powers;
                            ps.removeIf(p -> p.type != AbstractPower.PowerType.DEBUFF);
                            AbstractMonster nm;
                            nm = AbstractDungeon.getRandomMonster(m);

                            if(nm == null)
                            {
                                System.out.println("we got no new monster to get sick");
                                isDone = true;
                                return;
                            }
                            for(int i = 0; i < ps.size(); i++)
                            {
                                System.out.println("Pulling debuff: " + ps.get(i) + " off");
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(nm,p,ps.get(i),ps.get(i).amount));
                            }
                        }
                        isDone = true;
                    }
                });
                break;
            case "DoubleSweep":
                int[] damages = new int[AbstractDungeon.getMonsters().monsters.size()];
                Arrays.fill(damages, this.damage);
                for(int i = 0; i < 2;i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p,damages, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p,-this.magicNumber)));
                break;
        }
    }
}
