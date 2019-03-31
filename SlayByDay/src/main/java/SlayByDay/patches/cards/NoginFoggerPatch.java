package SlayByDay.patches.cards;

import SlayByDay.characters.TheModal;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(clz = AbstractPlayer.class,method = "useCard")
public class NoginFoggerPatch {
    public static boolean in_progress = false;
    public static SpireReturn  Prefix(AbstractPlayer __Instance, AbstractCard c, AbstractMonster monster, int energyOnUse)
    {
        System.out.println(TheModal.Reason_Mode);
        if(!TheModal.Reason_Mode && !in_progress)
        {
            monster = AbstractDungeon.getRandomMonster();
            in_progress = true;
            __Instance.useCard(c,monster,energyOnUse);
            in_progress = false;
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
