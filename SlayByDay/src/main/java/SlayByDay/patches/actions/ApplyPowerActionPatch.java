package SlayByDay.patches.actions;


import SlayByDay.SlayByDay;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import javax.swing.*;
import java.lang.reflect.Field;

@SpirePatch(clz= ApplyPowerAction.class, method="update")
public class ApplyPowerActionPatch {

    public static void Postfix(ApplyPowerAction __Instance) throws IllegalAccessException, NoSuchFieldException {
        Field f = __Instance.getClass().getDeclaredField("powerToApply");
        Field g = ApplyPowerAction.class.getDeclaredField("startingDuration");
        Field h = AbstractGameAction.class.getDeclaredField("duration");
        h.setAccessible(true);
        g.setAccessible(true);
        f.setAccessible(true);
        if(((AbstractPower)f.get(__Instance)).type == AbstractPower.PowerType.DEBUFF
                && __Instance.source == AbstractDungeon.player
                && (float)g.get(__Instance) == (float)h.get(__Instance)
        )
        {
            if(!__Instance.isDone)
                SlayByDay.debuffs_applied_this_turn++;
        }
    }
}
