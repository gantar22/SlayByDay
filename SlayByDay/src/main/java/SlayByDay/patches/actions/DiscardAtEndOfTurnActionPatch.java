package SlayByDay.patches.actions;


import SlayByDay.SlayByDay;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.lang.reflect.Field;

@SpirePatch(clz= DiscardAtEndOfTurnAction.class, method="update")
public class DiscardAtEndOfTurnActionPatch {
    public static void Prefix(DiscardAtEndOfTurnAction __Instance) throws NoSuchFieldException, IllegalAccessException {
        Field f = AbstractGameAction.class.getDeclaredField("duration");
        Field g = __Instance.getClass().getDeclaredField("DURATION");
        f.setAccessible(true);
        g.setAccessible(true);
        if((float)f.get(__Instance) == (float)g.get(__Instance) && !__Instance.isDone)
        {
            SlayByDay.cards_retained_last_turn = 0;
            for(int i = 0; i < AbstractDungeon.player.hand.group.size();i++)
            {
                if(AbstractDungeon.player.hand.group.get(i).retain)
                {
                    SlayByDay.cards_retained_last_turn++;
                }
            }
            System.out.println("retained: "+ Integer.toString(SlayByDay.cards_retained_last_turn));
        }
    }
}
