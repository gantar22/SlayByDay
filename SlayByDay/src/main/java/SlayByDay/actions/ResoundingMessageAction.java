package SlayByDay.actions;

import SlayByDay.cards.switchCards.ResoundingMessageCullTheWeakSwitch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;


// Source: ModifyBlockAction.class, or IncreaseMiscAction.class
public class ResoundingMessageAction extends AbstractGameAction {
    UUID uuid;

    public ResoundingMessageAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.baseDamage += this.amount;
            ((ResoundingMessageCullTheWeakSwitch)c).damage_counter += this.amount;
            if (c.baseDamage < 0) {
                c.baseDamage = 0;
            }
            c.applyPowers();
            c.flash();
        }

        this.isDone = true;
    }
}
