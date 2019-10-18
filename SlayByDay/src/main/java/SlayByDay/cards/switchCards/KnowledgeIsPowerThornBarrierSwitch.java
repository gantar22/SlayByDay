package SlayByDay.cards.switchCards;

import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class KnowledgeIsPowerThornBarrierSwitch extends AbstractSwitchByModeCard {


    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("KnowledgeIsPower", "ThornBarrier", 2, 0, 2, 1, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, true, false, true, false),

            new switchCard("ThornBarrier", "KnowledgeIsPower", 2, 0, 0, 0, 10, 0, 1, 1,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false) );


    @Override
    public String reasonCardID() {
        return "KnowledgeIsPower";
    }

    @Override
    public String passionCardID() {
        return "ThornBarrier";
    }

    public KnowledgeIsPowerThornBarrierSwitch(String switchID) {
        super("SlayByDay:KnowledgeIsPowerThornBarrier", "None", null, 0, "None", CardType.ATTACK,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, KnowledgeIsPowerThornBarrierSwitch.class);

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

    public KnowledgeIsPowerThornBarrierSwitch()
    {
        this(null);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "KnowledgeIsPower":
                for(int i = 0; i < SlayByDay.cards_drawn_this_turn + SlayByDay.cards_retained_last_turn - 5; i++)
                    AbstractDungeon.actionManager.addToBottom(
                                new PummelDamageAction(m,new DamageInfo(p,this.damage,this.damageType))
                    ); //possible infinite (draw card on damage)?
                break;
            case "ThornBarrier":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new ThornsPower(p,this.magicNumber)));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
                break;
        }
    }
}
