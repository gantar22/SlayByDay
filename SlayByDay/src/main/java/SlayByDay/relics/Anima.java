package SlayByDay.relics;

import SlayByDay.SlayByDay;
import SlayByDay.cards.switchCards.InsultInjurySwitch;
import SlayByDay.characters.TheMedium;
import SlayByDay.powers.InsultPower;
import SlayByDay.powers.LosePassionOnDamage;
import SlayByDay.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.lwjgl.Sys;

import java.util.ArrayList;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

class basic_relic_data {
    boolean reason_mode;
    int counter;
    public basic_relic_data(boolean b, int c)
    {
        reason_mode = b;
        counter = c;
    }
}

public class Anima extends CustomRelic implements BetterOnLoseHpRelic, CustomSavable<basic_relic_data> {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.

    public static final int REASON_STARTING_COUNTER = 5;
    public static final int PASSION_STARTING_COUNTER = 5;
    public static final int PASSION_STAT_GAIN = 1;
    public static final String ID = SlayByDay.makeID("Anima");
    public static Anima instance;
    public boolean started_turn = false;


    public static ArrayList<IOnSwitch> switchers;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mediumstarterRelic1.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mediumstarterRelic1.png"));
    private static final String my_retain_id = "my_retain";
    private static int persistant_counter = REASON_STARTING_COUNTER;
    private basic_relic_data old_data;

    public Anima() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        persistant_counter = REASON_STARTING_COUNTER;
        this.counter = REASON_STARTING_COUNTER;
        if(switchers == null)
            switchers = new ArrayList<IOnSwitch>();
        instance = this;
        //BaseMod.subscribe(this);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        started_turn = true;
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
        if(!TheMedium.Reason_Mode)
        {
            addCounter(0);
        }
    }

    public void addCounter(int i)
    {
        if(this.counter + i <= 0)
        {
            swap();
            return;
        }
        setCounter(this.counter + i);
        if(TheMedium.Reason_Mode)
        {
            if(this.counter > REASON_STARTING_COUNTER)
            {
                this.counter = REASON_STARTING_COUNTER;
            }
        }
        if(!TheMedium.Reason_Mode)
        {
            if(this.counter > PASSION_STARTING_COUNTER)
            {
                this.counter = PASSION_STARTING_COUNTER;
            }
        }
    }

    public static void subscribe(IOnSwitch listener) {
        switchers.add(listener);
    }
    public static void unsubscribe(IOnSwitch listener)
    {
        switchers.remove(listener);
    }

    void apply_stats_on_switch(boolean reason_mode)
    {
        if(!reason_mode)
        {
            apply_passion_stats(PASSION_STAT_GAIN);

        } else
        {
            apply_passion_stats(-PASSION_STAT_GAIN);

        }

        if(reason_mode)
        {
            AbstractPower my_retain = new RetainCardPower(AbstractDungeon.player,1);
            my_retain.ID = my_retain_id;
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction( AbstractDungeon.player,AbstractDungeon.player,my_retain)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,my_retain_id)
            );
        }
    }

    public static void Switch_Mode()
    {
        instance.swap();
    }

    public void swap()
    {
        flash();
        if(TheMedium.Reason_Mode)
        {
            TheMedium.Reason_Mode = false;
            setCounter(PASSION_STARTING_COUNTER);
            System.out.println(this.counter);
            System.out.println("switch to passion");
        } else {
            TheMedium.Reason_Mode = true;
            setCounter(REASON_STARTING_COUNTER);
            System.out.println("switch to reason");
        }

        for(int i = 0; i < switchers.size(); i++)
        {
            switchers.get(i).OnSwitch(TheMedium.Reason_Mode);
        }
        apply_stats_on_switch(TheMedium.Reason_Mode);

        if(AbstractDungeon.player.hand.findCardById("SlayByDay:InsultInjury") != null && !AbstractDungeon.player.hasPower(InsultPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new InsultPower(AbstractDungeon.player,AbstractDungeon.player,0)));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onCardDraw(AbstractCard drawnCard) {

        if(drawnCard.cardID == "SlayByDay:InsultInjury" && !AbstractDungeon.player.hasPower(InsultPower.POWER_ID) && started_turn)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new InsultPower(AbstractDungeon.player,AbstractDungeon.player,0)));
        }
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    if(i > 0 && !TheMedium.Reason_Mode)
                    {
                        flash();
                        addCounter(1);
                    }
                    isDone = true;
                }
            });
        }
        return i;
    }

    @Override
    public void atBattleStart()
    {
        if(old_data != null)
        {
            if(TheMedium.Reason_Mode != old_data.reason_mode)
                swap();
            else{
                swap();
                swap();
            }
            setCounter(old_data.counter);
            old_data = null;
        } else {
            setCounter(persistant_counter);
            if(TheMedium.Reason_Mode)
            {
                AbstractPower my_retain = new RetainCardPower(AbstractDungeon.player,1);
                my_retain.ID = my_retain_id;
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction( AbstractDungeon.player,AbstractDungeon.player,my_retain)
                );
                System.out.println("HI ADRIAN! LKA");
            } else {
                apply_passion_stats(PASSION_STAT_GAIN);
                System.out.println("HI ADRIAN! FGH");
            }
        }
    }

    @Override
    public void onVictory()
    {
        persistant_counter = this.counter;
        started_turn = false;
    }

    void apply_passion_stats(int amount)
    {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new DexterityPower(AbstractDungeon.player, amount),
                        amount)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player, amount),
                        amount)
        );
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        System.out.println("playing: " + c.name + " at " + (m != null ? m.name : "null"));

        int dif = 0;

            System.out.println("Card: " + c.name + " is doing " + c.damage + " damage.");


        if(TheMedium.Reason_Mode)
        {
            if(c.type == AbstractCard.CardType.ATTACK && TheMedium.Reason_Mode)
            {
                dif -= 2;
                flash();
            } else if(c.type == AbstractCard.CardType.SKILL && TheMedium.Reason_Mode)
            {
                flash();
                dif += 1;
            }
        }
        int delta_counter = dif;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if(delta_counter != 0)
                {
                    flash();
                    addCounter(delta_counter);
                }
                isDone = true;
            }
        });

    }



    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if(!TheMedium.Reason_Mode && info.type == DamageInfo.DamageType.NORMAL)
                {
                    Anima.instance.flash();
                    if(target != null && target.isPlayer) System.out.println("lol");
                    Anima.instance.addCounter(-2);
                }
                isDone = true;
            }
        });

    }


    @Override
    public basic_relic_data onSave() {
        return new basic_relic_data(TheMedium.Reason_Mode,this.counter);
    }

    @Override
    public void onLoad(basic_relic_data my_basic_relic_data) {
       // switchers = new ArrayList<>(Arrays.asList(basic_relic_data.list));
        //save the mode and check it somewhere else to assign it
        //register some indication that you ought to swap at your first chance
        System.out.println("ADRIAN!!! we loaded something!");
        if(switchers == null)
        {
            System.out.println("loaded a null switch array");
            switchers = new ArrayList<>();
        }
        switchers.add(new IOnSwitch() {
            @Override
            public void OnSwitch(boolean Reason_Mode) {
                TheMedium.class.cast(AbstractDungeon.player).switch_mode(Reason_Mode);
            }
        });
        old_data = new basic_relic_data(my_basic_relic_data.reason_mode,my_basic_relic_data.counter);

    }
}


