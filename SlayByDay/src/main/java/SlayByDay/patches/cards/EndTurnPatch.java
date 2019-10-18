package SlayByDay.patches.cards;

import SlayByDay.SlayByDay;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;


@SpirePatch(clz= GameActionManager.class, method="endTurn")
public class EndTurnPatch {

    public static void Prefix(GameActionManager __Instance)
    {
        SlayByDay.cards_drawn_this_turn = 0;
        SlayByDay.debuffs_applied_this_turn = 0;
    }
}