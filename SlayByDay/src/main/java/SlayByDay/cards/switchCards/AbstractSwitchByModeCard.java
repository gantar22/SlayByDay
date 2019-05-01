package SlayByDay.cards.switchCards;

import SlayByDay.SlayByDay;
import SlayByDay.actions.SwitchAction;
import SlayByDay.cards.AbstractMediumCard;
import SlayByDay.characters.TheMedium;
import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.Iterator;


// Source: https://github.com/Tempus/The-Disciple/blob/master/src/main/java/cards/switchCards/AbstractSelfSwitchCard.java#L259
public abstract class AbstractSwitchByModeCard extends AbstractMediumCard {

    public class switchCard {
        public CardType type;
        public CardTarget target;
        public int baseCost;
        public int cost;

        public boolean isMultiDamage = false;
        public boolean isInnate = false;
        public boolean exhaust = false;
        public boolean isEthereal = false;

        public int damage = -1;
        public int block = -1;
        public int magicNumber = -1;

        public int damageUp = -1;
        public int blockUp = -1;
        public int magicNumberUp = -1;
        public int costDown = -1;

        public String portraitImg;

        public String originalName;
        public String name;
        public String description;
        public String upgradeDescription;
        public String cardID;
        public String switchID;

        public switchCard(String CardID, String switchID, Integer cost, Integer costDown, Integer damage, Integer damageUp,
                          Integer block, Integer blockUp, Integer magicNum, Integer magicNumUp,
                          CardType type, CardTarget target, boolean isMultiDamage, boolean isInnate, boolean exhaust, boolean isEthereal)
        {
            this.type = type;
            this.target = target;
            this.baseCost = cost;
            this.cost = cost;

            this.isMultiDamage = isMultiDamage;
            this.isInnate = isInnate;
            this.exhaust = exhaust;
            this.isEthereal = isEthereal;

            this.damage = damage;
            this.block = block;
            this.magicNumber = magicNum;

            this.damageUp = damageUp;
            this.blockUp = blockUp;
            this.magicNumberUp = magicNumUp;
            this.costDown = costDown;

            this.portraitImg = SlayByDay.makeCardPath(CardID + ".png");

            CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardID);
            this.originalName = cardStrings.NAME;
            this.name = cardStrings.NAME;
            this.description = cardStrings.DESCRIPTION;
            this.upgradeDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.cardID = CardID;
            this.switchID = switchID;
        }
    }

    public List<switchCard> switchList;

    AbstractSwitchByModeCard cardToPreview = null;
    protected static final float CARD_TIP_PAD = 16.0F;
    public boolean bullshit = false;
    protected Class switchClass;

    public int damageUp = 0;
    public int blockUp = 0;
    public int magicNumberUp = 0;
    public int baseCost = 0;
    public int costDown = 0;
    public String upgradeDescription = "";
    public String switchID;
    public String currentID;

    private boolean switch_queued = false;

    public abstract String reasonCardID();
    public abstract String passionCardID();

    public AbstractSwitchByModeCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, Class previewCard) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        this.switchClass = previewCard;
        this.tags.add(TheMedium.Enums.MODE_SWITCH_CARD);
    }

    @Override
    public void update() {
        validateSwitchCardMode(false);
        super.update();
    }

    // Make sure that this card has switched correctly to whichever mode it's supposed to be in.
    public void validateSwitchCardMode(boolean instant) {
        if (this.switch_queued) {
            return;
        }
        if (AbstractDungeon.currMapNode == null) {
            return;
        }

        if ((TheMedium.Reason_Mode && this.currentID != reasonCardID()) || (!TheMedium.Reason_Mode && this.currentID != passionCardID())) {
            if (instant) {
                switchCardInDeck();
                switchTo(switchID);
            } else {
                AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
                switch_queued = true;
            }
        }
    }

    @Override
    public void renderInLibrary(SpriteBatch sb)
    {
        if (!(this.current_y >= -200.0F * Settings.scale) && (this.current_y <= Settings.HEIGHT + 200.0F * Settings.scale)) {
            return;
        }
        if ((SingleCardViewPopup.isViewingUpgrade) && (this.isSeen) && (!this.isLocked))
        {
            AbstractCard copy = makeStatEquivalentCopy();
            copy.current_x = this.current_x;
            copy.current_y = this.current_y;
            copy.drawScale = this.drawScale;
            copy.upgrade();
            copy.displayUpgrades();
            copy.render(sb);
        }
        else
        {
            super.renderInLibrary(sb);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard c = null;
        try {
            if (this.switchClass != null) {
                c = (AbstractSwitchByModeCard)this.switchClass.newInstance();
            }
        } catch (Throwable e) {
            System.out.println(e.toString());
        }
        return c;
    }

    public AbstractCard makeStatEquivalentCopy()
    {
        AbstractSwitchByModeCard card = (AbstractSwitchByModeCard)makeCopy();
        card.switchTo(this.currentID);

        if (this.upgraded) {
            card.upgrade();
        }
        card.name = this.name;
        card.target = this.target;
        card.upgraded = this.upgraded;
        card.timesUpgraded = this.timesUpgraded;
        card.baseDamage = this.baseDamage;
        card.baseBlock = this.baseBlock;
        card.baseMagicNumber = this.baseMagicNumber;
        card.cost = this.cost;
        card.baseCost = this.baseCost;
        card.costForTurn = this.costForTurn;
        card.isCostModified = this.isCostModified;
        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
        card.inBottleLightning = this.inBottleLightning;
        card.inBottleFlame = this.inBottleFlame;
        card.inBottleTornado = this.inBottleTornado;
        card.isSeen = this.isSeen;
        card.isLocked = this.isLocked;
        card.misc = this.misc;
        return card;
    }

    public switchCard findSwitch(String id) {
        for (switchCard card : switchList) {
            if (card.cardID.equals(id)) {
                this.misc = switchList.indexOf(card);
                return card;
            }
        }

        System.out.println(this.cardID + " could not find the switch card " + id);
        return null;
    }

    public void switchTo(Integer index) {
        this.switchTo(switchList.get(index).cardID);
    }

    public void switchTo(String id) {
        switchCard card = this.findSwitch(id);

        if (card != null) {
            // Determine cost reductions before everything gets changed
            int current_base_cost = this.upgraded ? this.baseCost - this.costDown : this.baseCost;

            boolean cost_modified = this.isCostModified;
            boolean cost_for_combat_is_0 = cost_modified && this.cost == 0;
            int cost_change_for_combat = this.cost - current_base_cost;

            boolean cost_modified_for_turn = this.isCostModifiedForTurn;
            boolean cost_for_turn_is_0 = cost_modified_for_turn && this.costForTurn == 0;
            int cost_change_for_turn = this.costForTurn - this.cost;

            // Set all card properties
            this.type = card.type;
            this.cost = card.cost;
            this.baseCost = card.baseCost;

            this.target = card.target;

            this.isMultiDamage = card.isMultiDamage;
            this.isInnate = card.isInnate;
            this.exhaust = card.exhaust;
            this.isEthereal = card.isEthereal;

            this.baseDamage = card.damage;
            this.baseBlock = card.block;
            this.baseMagicNumber = card.magicNumber;

            this.damageUp = card.damageUp;
            this.blockUp = card.blockUp;
            this.magicNumberUp = card.magicNumberUp;
            this.costDown = card.costDown;

            this.loadCardImage(card.portraitImg);
            this.textureImg = card.portraitImg;

            this.originalName = card.originalName;
            this.name = card.name;
            this.rawDescription = card.description;
            this.upgradeDescription = card.upgradeDescription;
            this.currentID = card.cardID;
            this.switchID = card.switchID;

            resetAttributes();

            if (this.upgraded) {
                upgradeName();
                if (damageUp != 0) {
                    upgradeDamage(damageUp);
                }
                if (blockUp != 0) {
                    upgradeBlock(blockUp);
                }
                if (magicNumberUp != 0) {
                    upgradeMagicNumber(magicNumberUp);
                }
                if (costDown != 0) {
                    upgradeBaseCost(baseCost - costDown);
                }
                this.rawDescription = card.upgradeDescription;
            }

            initializeTitle();
            resetAttributes();

            // Set cost in case it was reduced
            if (cost_for_combat_is_0) {
                modifyCostForCombat(-this.cost);
            } else if (cost_for_turn_is_0) {
                modifyCostForTurn(-this.costForTurn);
            } else if (cost_modified) {
                modifyCostForCombat(cost_change_for_combat);
            } else if (cost_modified_for_turn) {
                modifyCostForTurn(cost_change_for_turn);
            }

            initializeDescription();

            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.hasRelic("Bottled Flame")) {
                    ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).setDescriptionAfterLoading(); }
                if (AbstractDungeon.player.hasRelic("Bottled Lightning")) {
                    ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).setDescriptionAfterLoading(); }
                if (AbstractDungeon.player.hasRelic("Bottled Tornado")) {
                    ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).setDescriptionAfterLoading(); }
            }
        }

        this.switch_queued = false;
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.applyPowers();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (damageUp != 0) {
                upgradeDamage(damageUp);
            }
            if (blockUp != 0) {
                upgradeBlock(blockUp);
            }
            if (magicNumberUp != 0) {
                upgradeMagicNumber(magicNumberUp);
            }
            if (costDown != 0) {
                upgradeBaseCost(baseCost - costDown);
            }
            this.rawDescription = this.upgradeDescription;
            initializeDescription();
        }
    }

    // Card Preview
    @Override
    public void hover() {
        try {
            if (this.switchClass != null && this.cardToPreview == null) {
//                this.cardToPreview = (AbstractSwitchByModeCard)this.switchClass.newInstance();
                this.cardToPreview = (AbstractSwitchByModeCard)this.makeStatEquivalentCopy();
                this.cardToPreview.switchTo(this.switchID);
                if (this.upgraded && !this.cardToPreview.upgraded) { this.cardToPreview.upgrade(); }
            }
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

        super.hover();
        this.bullshit = true;
    }

    @Override
    public void unhover() {
        super.unhover();
        this.bullshit = false;
        this.cardToPreview = null;
    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        if ((!Settings.hideCards) && (this.bullshit))
        {
            if ((SingleCardViewPopup.isViewingUpgrade) && (this.isSeen) && (!this.isLocked))
            {
                AbstractCard copy = makeStatEquivalentCopy();
                copy.current_x = this.current_x;
                copy.current_y = this.current_y;
                copy.drawScale = this.drawScale;
                copy.upgrade();

                TipHelper.renderTipForCard(copy, sb, copy.keywords);
            } else {
                super.renderCardTip(sb);
            }
        }

        if ((this.cardToPreview != null) && (!Settings.hideCards) && (this.bullshit))
        {
            float tmpScale = this.drawScale / 1.5F;

            if ((AbstractDungeon.player != null) && (AbstractDungeon.player.isDraggingCard)) {
                return;
            }

            //						x    = card center	  + half the card width 			 + half the preview width 					  + Padding			* Viewport scale * drawscale
            if (this.current_x > Settings.WIDTH * 0.75F) {
                this.cardToPreview.current_x = this.current_x + (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (CARD_TIP_PAD)) * this.drawScale);
            } else {
                this.cardToPreview.current_x = this.current_x - (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (CARD_TIP_PAD)) * this.drawScale);
            }

            this.cardToPreview.current_y = this.current_y + ((AbstractCard.IMG_HEIGHT / 2.0F) - (AbstractCard.IMG_HEIGHT / 2.0F / 1.5F)) * this.drawScale;

            this.cardToPreview.drawScale = tmpScale;
            this.cardToPreview.render(sb);
        }
    }

    public boolean switchCardInDeck() {
        if (AbstractDungeon.currMapNode == null) { return false; }
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator(); i.hasNext();)
            {
                AbstractCard e = (AbstractCard)i.next();
                if (e instanceof AbstractSwitchByModeCard) {
                    AbstractSwitchByModeCard sCard = (AbstractSwitchByModeCard)e;
                    if (sCard.cardID.equals(this.cardID) && sCard.upgraded == this.upgraded && sCard.currentID == this.currentID)
                    {
                        sCard.switchTo(sCard.switchID);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
