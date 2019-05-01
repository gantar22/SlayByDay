package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class RemoveRandomDebuffsAction extends AbstractGameAction {
    private AbstractCreature c;
    private int amount;

    public RemoveRandomDebuffsAction(AbstractCreature c, int amount) {
        this.c = c;
        this.amount = amount;
        this.duration = 0.5F;
    }

    public void update() {
        ArrayList<AbstractPower> powers = (ArrayList<AbstractPower>)this.c.powers.clone();
        powers.removeIf((AbstractPower power) -> power.type != AbstractPower.PowerType.DEBUFF);

        while (powers.size() > 0 && this.amount > 0) {
            int power_index = AbstractDungeon.cardRandomRng.random(0, powers.size() - 1);
            AbstractPower power = powers.get(power_index);
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.c, this.c, power.ID));
            powers.remove(power);
            this.amount--;
        }

        this.isDone = true;
    }
}
