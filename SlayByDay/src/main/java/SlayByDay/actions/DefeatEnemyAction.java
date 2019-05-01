package SlayByDay.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Source: https://github.com/adbronico/StS-PaladinMod/blob/98e56b8e4c4aef49833dc2681245b5b956f7e242/src/main/java/paladinmod/actions/DefeatEnemyAction.java
public class DefeatEnemyAction extends AbstractGameAction
{
    public DefeatEnemyAction(AbstractMonster monster)
    {
        this.target = monster;
    }

    @Override
    public void update()
    {
        AbstractMonster monster = (AbstractMonster) target;

        monster.currentHealth = 0;
        monster.healthBarUpdatedEvent();
        monster.die();

        this.isDone = true;
    }
}