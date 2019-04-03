package SlayByDay.actions;

import SlayByDay.cards.switchCards.BolsterGuardSwitch;
import SlayByDay.cards.switchCards.HoneLacerateSwitch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;


// Source: ModifyBlockAction.class, or IncreaseMiscAction.class
public class BolsterAction extends AbstractGameAction {
    UUID uuid;

    public BolsterAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.baseBlock += this.amount;
            ((BolsterGuardSwitch)c).block_counter += this.amount;
            if (c.baseBlock < 0) {
                c.baseBlock = 0;
            }
        }

        this.isDone = true;
    }
}
