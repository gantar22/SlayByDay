package SlayByDay.patches.cards;

import SlayByDay.SlayByDay;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;


@SpirePatch(clz= AbstractPlayer.class, method="draw",paramtypez = {int.class})
public  class DrawCardPatch {

    public static void Prefix(AbstractPlayer __Instance, int numCards)
    {
        System.out.print("drew: " + Integer.toString(numCards)  + " cards");
        SlayByDay.cards_drawn_this_turn += numCards;
    }
}
