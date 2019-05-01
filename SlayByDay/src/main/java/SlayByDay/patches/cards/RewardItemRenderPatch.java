package SlayByDay.patches.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import org.apache.logging.log4j.core.util.ReflectionUtil;


@SpirePatch(clz = RewardItem.class,method = "render")
public class RewardItemRenderPatch {
    public static void  Prefix(RewardItem __Instance, SpriteBatch sb)
    {
        if(__Instance.type == RewardItem.RewardType.POTION && __Instance.relicLink != null && __Instance.potion.slot != -2)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LINKED, __Instance.hb.cX - 64.0F, __Instance.y - 64.0F + 52.0F * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);


        }
    }
}
