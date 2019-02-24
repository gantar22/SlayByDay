package SlayByDay.relics;

import SlayByDay.characters.TheModal;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;
import com.sun.org.apache.regexp.internal.RE;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

public class PlaceholderRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.

    public static final int REASON_STARTING_COUNTER = 5;
    public static final int PASSION_STARTING_COUNTER = 5;
    public static final String ID = SlayByDay.makeID("PlaceholderRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public PlaceholderRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    // Do nothing
    @Override
    public void onEquip() {
        this.counter = REASON_STARTING_COUNTER;
    }

    // Undo nothing
    @Override
    public void onUnequip() {

    }

    @Override
    public void atTurnStart()
    {
        this.counter--;
        if(this.counter == 0)
        {
            if(TheModal.Reason_Mode)
            {
                TheModal.Reason_Mode = false;
                this.counter = PASSION_STARTING_COUNTER;
            } else {
                TheModal.Reason_Mode = true;
                this.counter = REASON_STARTING_COUNTER;
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
