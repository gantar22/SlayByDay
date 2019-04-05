package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;


// Source: RemoveDebuffsAction.class
public class PunishmentAction extends AbstractGameAction {
    private AbstractCreature c;
    private AbstractCreature player;
    private AbstractCard card;

    public PunishmentAction(AbstractCreature c, AbstractCreature player, AbstractCard card) {
        this.c = c;
        this.player = player;
        this.card = card;
        this.duration = 0.5F;
    }

    public void update() {
        Iterator var1 = this.c.powers.iterator();
        int total_removed = 0;

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                if (p.amount < 1) {
                    total_removed++;
                } else {
                    total_removed += p.amount;
                }
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.c, this.player, p.ID));
            }
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(player, card.damage * total_removed, card.damageTypeForTurn)));

        this.isDone = true;
    }
}
