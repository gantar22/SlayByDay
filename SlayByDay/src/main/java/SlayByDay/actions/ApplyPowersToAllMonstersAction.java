package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;

import java.util.Iterator;

// Source: https://github.com/adbronico/StS-PaladinMod/blob/98e56b8e4c4aef49833dc2681245b5b956f7e242/src/main/java/paladinmod/actions/DefeatEnemyAction.java
public class ApplyPowersToAllMonstersAction extends AbstractGameAction
{
    public ApplyPowersToAllMonstersAction() {}

    @Override
    public void update() {
        if (isDone) {
            return;
        }
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var1.next();
            mo.applyPowers();
            for (AbstractPower p : mo.powers) {
                p.updateDescription();
            }
        }
        isDone = true;
    }
}