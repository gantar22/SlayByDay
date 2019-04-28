package SlayByDay.actions;

import SlayByDay.cards.switchCards.HoneLacerateSwitch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;


// Source: ModifyBlockAction.class, or IncreaseMiscAction.class
public class HoneAction extends AbstractGameAction {
    UUID uuid;

    public HoneAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.baseDamage += this.amount;
            ((HoneLacerateSwitch)c).damage_counter += this.amount;
            if (c.baseDamage < 0) {
                c.baseDamage = 0;
            }
            c.applyPowers();
            c.flash();
        }

        this.isDone = true;
    }
}
