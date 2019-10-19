package SlayByDay.relics;

import basemod.abstracts.CustomRelic;
import basemod.devcommands.relic.Relic;
import basemod.devcommands.relic.RelicAdd;
import basemod.devcommands.relic.RelicRemove;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

// A work-in-progress just to experiment with relics. Based on a relic from Joey's doc.

public class LeftHemisphere extends CustomRelic {
    // ID, images, text.
    public static final String ID = SlayByDay.makeID("LeftHemisphere");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

//    private static String power_just_added = "";

    public LeftHemisphere() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player.hasRelic(RightHemisphere.ID)) {
            AbstractDungeon.player.relics.add(new UnitedMind());
            AbstractDungeon.player.loseRelic(RightHemisphere.ID);
            AbstractDungeon.player.loseRelic(this.relicId);
        }
    }
}
