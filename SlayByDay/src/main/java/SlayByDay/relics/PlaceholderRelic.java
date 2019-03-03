package SlayByDay.relics;

import SlayByDay.characters.TheModal;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;

import java.util.ArrayList;

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

    public static ArrayList<IOnSwitch> switchers;

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
        switchers = new ArrayList<IOnSwitch>();
    }

    // Undo nothing
    @Override
    public void onUnequip() {

    }

    @Override
    public void onPlayerEndTurn()
    {
        setCounter(this.counter - 1);
        if(this.counter == 0)
        {
            swap();
        }
    }

    public void subscribe(IOnSwitch listener)
    {
        switchers.add(listener);
    }

    public void swap()
    {
        if(TheModal.Reason_Mode)
        {
            TheModal.Reason_Mode = false;
            this.counter = PASSION_STARTING_COUNTER;
        } else {
            TheModal.Reason_Mode = true;
            this.counter = REASON_STARTING_COUNTER;
        }
        for(int i = 0; i < switchers.size(); i++)
        {
            switchers.get(i).OnSwitch(TheModal.Reason_Mode);
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}


