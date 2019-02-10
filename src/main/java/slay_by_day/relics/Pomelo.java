package slay_by_day.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slay_by_day.SlayByDay;

public class Pomelo extends CustomRelic {
    public static final String ID = "SlayByDay:Pomelo";
    private static final int HP_PER_CARD = 1;

    public Pomelo() {
        super(ID, getTexture(), // you could create the texture in this class if you wanted too
                RelicTier.UNCOMMON, LandingSound.MAGICAL); // this relic is uncommon and sounds magic when you click it
    }

    private static String getTexture() {
        return SlayByDay.RELIC_IMG_PATH + "Pomelo.png";
    }

    @Override
    public String getUpdatedDescription() {
        return "Hardcoded description";
//        return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onEquip() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.isEthereal) { // when equipped (picked up) this relic counts how many ethereal cards are in the player's deck
                count++;
            }
        }
        AbstractDungeon.player.increaseMaxHp(count * HP_PER_CARD, true);
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new Pomelo();
    }

}
