package SlayByDay.cards.switchCards;

import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import SlayByDay.powers.InsultPower;
import SlayByDay.relics.Anima;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.lwjgl.Sys;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InsultInjurySwitch extends AbstractSwitchByModeCard {

    public List<AbstractSwitchByModeCard.switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Insult", "Injury", 1, 0, 2, 1, 0, 0, 0, 0,
                    AbstractCard.CardType.ATTACK, AbstractCard.CardTarget.ENEMY, true, false, false, false),

            new AbstractSwitchByModeCard.switchCard("Injury", "Insult", 1, 0, 4, 2, 0, 0, 40, 10,
                    AbstractCard.CardType.ATTACK, AbstractCard.CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Insult";
    }
    public String passionCardID() {
        return "Injury";
    }

    public InsultInjurySwitch(String switchID) {
        super("SlayByDay:InsultInjury", "None", null, 0, "None", AbstractCard.CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE, InsultInjurySwitch.class);

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

    public InsultInjurySwitch() { this(null); }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Insult":
                for (int i = 0; i < SlayByDay.debuffs_applied_this_turn; i++) {
                    AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
                }            break;
            case "Injury":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn)));
                int r = AbstractDungeon.cardRandomRng.random(100);
                System.out.println("injury repeat chance: " + r + " and magic n: " + this.magicNumber);
                if(r > this.magicNumber)
                {
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(new InsultInjurySwitch(), m, 0));
                }
                break;
        }
    }
}



