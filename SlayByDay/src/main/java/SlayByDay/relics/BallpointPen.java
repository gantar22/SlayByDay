package SlayByDay.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

// A work-in-progress just to experiment with relics. Based on a relic from Joey's doc.

public class BallpointPen extends CustomRelic implements OnReceivePowerRelic {
    // ID, images, text.
    public static final String ID = SlayByDay.makeID("BallpointPen");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

//    private static String power_just_added = "";

    public BallpointPen() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
    }

    @Override
    public void onEquip() {
    }

    @Override
    public void onUnequip() {
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature source) {
//        if (abstractPower.name.equalsIgnoreCase(power_just_added)) {
//            power_just_added = "";
//        } else {
//            power_just_added = abstractPower.name;
//            AbstractPower bonus_power = abstractPower;
//            bonus_power.amount = 1;
//            abstractCreature.addPower(bonus_power);
//        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        flash();
        return stackAmount + 1;
    }
}
