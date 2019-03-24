package SlayByDay.relics;

import SlayByDay.characters.TheModal;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;
import basemod.interfaces.OnCardUseSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

public class PlaceholderRelic extends CustomRelic implements OnCardUseSubscriber {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.

    public static final int REASON_STARTING_COUNTER = 5;
    public static final int PASSION_STARTING_COUNTER = 5;
    public static final String ID = SlayByDay.makeID("PlaceholderRelic");
    public static PlaceholderRelic instance;

    public static ArrayList<IOnSwitch> switchers;

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public PlaceholderRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = REASON_STARTING_COUNTER;
        switchers = new ArrayList<IOnSwitch>();
        instance = this;
        BaseMod.subscribe(this);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }


    // Do nothing
    @Override
    public void onEquip() {


    }

    // Undo nothing
    @Override
    public void onUnequip() {

    }

    @Override
    public void onPlayerEndTurn()
    {
        if(!TheModal.Reason_Mode)
        {
            addCounter(-1);
        }
    }

    void addCounter(int i)
    {
        if(this.counter + i <= 0)
        {
            swap();
            return;
        }
        setCounter(this.counter + i);
        if(TheModal.Reason_Mode)
        {
            if(this.counter > REASON_STARTING_COUNTER)
            {
                this.counter = REASON_STARTING_COUNTER;
            }
        }
        if(!TheModal.Reason_Mode)
        {
            if(this.counter > PASSION_STARTING_COUNTER)
            {
                this.counter = PASSION_STARTING_COUNTER;
            }
        }
    }

    public static void subscribe(IOnSwitch listener)
    {
        switchers.add(listener);
    }

    public static void Switch_Mode()
    {
        instance.swap();
    }

    public void swap()
    {
        if(TheModal.Reason_Mode)
        {
            TheModal.Reason_Mode = false;
            this.counter = PASSION_STARTING_COUNTER;
            System.out.println("switch to passion");
        } else {
            TheModal.Reason_Mode = true;
            this.counter = REASON_STARTING_COUNTER;
            System.out.println("switch to reason");
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

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(abstractCard.type == AbstractCard.CardType.ATTACK && TheModal.Reason_Mode)
        {
            addCounter(-2);
        } else if(abstractCard.type == AbstractCard.CardType.SKILL && TheModal.Reason_Mode)
        {
            addCounter(1);
        }
    }
}


