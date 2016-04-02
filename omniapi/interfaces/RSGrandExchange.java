package omniapi.interfaces;

import org.osbot.rs07.api.def.ItemDefinition;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;
import omniapi.data.Entity;
import omniapi.data.NPC;
import omniapi.data.Widget;
import omniapi.data.Item;

//TODO: offers and stuff!
public class RSGrandExchange extends OmniScriptEmulator<OmniScript> implements RSInterface {

	public enum OfferType {
		FIXED_PRICE,
		PERCENTAGE_ABOVE_MEDIAN,
		PERCENTAGE_BELOW_MEDIAN,
		MEDIAN;
	}
	
	public enum CollectType {
		BANK,
		INVENTORY;
	}
	
	public RSGrandExchange(OmniScript script) {
		super(script);
	}

	@Override
	public int getRootID() {
		return 465;
	}

	@Override
	public int getChildID() {
		return 2;
	}

	@Override
	public int getGrandchildID() {
		return 0;
	}

	@Override
	public Widget getWidget() {
		return getWidgetFinder().get(getRootID(), getChildID(), getGrandchildID());
	}

	@Override
	public boolean isOpen() {
		return getWidget().exists();
	}

	@Override
	public boolean open() throws InterruptedException {
		if (isOpen()) return true;
		
		Entity bankEntity = getEntityFinder().find((entity) -> (entity.hasAction("Exchange")));
		
		if (!bankEntity.exists()) {
			NPC bankNPC = getNPCFinder().find((npc) -> (npc.hasAction("Exchange")));
			if (!bankNPC.exists()) return false;
			if (!bankNPC.interact("Exchange")) return false;
			return sleepUntil(() -> (isOpen()));
		}
		else {
			if (!bankEntity.interact("Exchange")) return false;
			return sleepUntil(() -> (isOpen()));
		}
	}

	@Override
	public boolean close() throws InterruptedException {
		if (!isOpen()) return true;
		
		Widget closeButton = getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535 && widget.getRootId() == getRootID()));
		if (!closeButton.interact()) return false;
		
		return sleepUntil(() -> (!isOpen()));
	}
	
	public boolean createBuyOffer(String itemName, int qty, int price) throws InterruptedException {
		if (itemName == null || itemName.length() < 1) return false;
		if (qty < 1 || price < 1) return false;
		
		if (!isOpen()) open();
		
		Widget buyOffer = getWidgetFinder().find((widget) -> (widget.getRootId() == getRootID() && widget.getSpriteIndex1() == 1108));
		if (!buyOffer.exists() || !buyOffer.interact()) return false;
		
		sleepUntil(() -> (getWidgetFinder().findFromText("Buy offer", (widget) -> (widget.getRootId() == getRootID())).exists()));
		
		for (String word : itemName.split(" ")) {
			sleep(rand(Constants.TICK, (int)(Constants.TICK * 1.5)));
			getKeyboard().typeString(word);
			Widget itemWidget = getWidgetFinder().findFromText(itemName, (widget) -> (widget.getRootId() == 162));
			if (!sleepUntil(() -> (itemWidget.exists() && itemWidget.getThirdLevelId() <= 25), 800)) {
				getKeyboard().typeKey((char) 32);
				continue;
			}
			if (!itemWidget.interact()) return false;
			break;
		}
		
		if (!sleepUntil(() -> (getWidgetFinder().findFromText(itemName).exists() && getWidgetFinder().getLastFound().getRootId() == getRootID() && getWidgetFinder().getLastFound().getSecondLevelId() == 23))) return false;
		
		int medianPrice = Integer.valueOf(getWidgetFinder().find((widget) -> (widget.getMessage().endsWith(" coins") && widget.getRootId() == getRootID() && widget.getSecondLevelId() == 23)).getMessage().split(" ")[0]);
		
		if (price != medianPrice) {
			sleep(rand((int)(Constants.TICK / 2), Constants.TICK));
			Widget enterPrice = getWidgetFinder().findFromAction("Enter price", (widget) -> (widget.getSpriteIndex1() == 1110));
			if (!enterPrice.exists() || !enterPrice.interact()) return false;
			
			Widget setPrice = getWidgetFinder().get(162, 31); //Widget setPrice = getWidgetFinder().findFromText("Set a price for each item:");
			sleepUntil(() -> (setPrice.exists()));
			
			getKeyboard().typeString(String.valueOf(price));
			
			sleepUntil(() -> (!setPrice.exists()));
		}
		
		if (qty > 1) {
			sleep(rand((int)(Constants.TICK / 2), Constants.TICK));
			Widget enterQuantity = getWidgetFinder().findFromAction("Enter quantity", (widget) -> (widget.getSpriteIndex1() == 1110));
			if (!enterQuantity.interact()) return false;
			
			Widget setPrice = getWidgetFinder().get(162, 31); //Widget setPrice = getWidgetFinder().findFromText("Set a price for each item:");
			sleepUntil(() -> (setPrice.exists()));
			
			getKeyboard().typeString(String.valueOf(qty));
			
			sleepUntil(() -> (!setPrice.exists()));
		}
		
		sleep(rand((int)(Constants.TICK / 2), Constants.TICK));
		
		Widget confirmButton = getWidgetFinder().findFromAction("Confirm", (widget) -> (widget.getRootId() == getRootID()));
		if (!confirmButton.interact()) return false;
		
		return sleepUntil(() -> (!confirmButton.exists()));
	}
	
	public boolean createSellOffer(String itemName, int price) throws InterruptedException {
		return createSellOffer(getInventoryFinder().find(itemName), price);
	}
	
	public boolean createSellOffer(Item item, int price) throws InterruptedException {
		if (!item.exists()) return false;
		if (!isOpen()) open();
		
		item.interact("Offer");
		
		sleepUntil(() -> (getWidgetFinder().findFromText("Sell offer", (widget) -> (widget.getRootId() == getRootID())).exists()));
		
		Widget enterPrice = getWidgetFinder().findFromAction("Enter price");
		if (!enterPrice.exists() || !enterPrice.interact()) return false;
		
		Widget setPrice = getWidgetFinder().get(162, 31); //Widget setPrice = getWidgetFinder().findFromText("Set a price for each item:");
		sleepUntil(() -> (setPrice.exists()));
		
		getKeyboard().typeString(String.valueOf(price));
		
		sleepUntil(() -> (!setPrice.exists()));
		
		Widget confirmButton = getWidgetFinder().findFromAction("Confirm", (widget) -> (widget.getRootId() == getRootID()));
		if (!confirmButton.interact()) return false;
		
		return sleepUntil(() -> (!confirmButton.exists()));
	}
	
	public boolean collectAllToInventory() throws InterruptedException {
		return collectAll(CollectType.INVENTORY);
	}
	
	public boolean collectAllToBank() throws InterruptedException {
		return collectAll(CollectType.BANK);
	}
	
	public boolean collectAll(CollectType collectType) throws InterruptedException {
		if (!isOpen()) open();
		
		Widget collectWidget = getWidgetFinder().find((widget) -> (widget.hasAction("Collect to inventory", "Collect to bank") && widget.getRootId() == getRootID()));
		collectWidget.interact((collectType.equals(CollectType.BANK) ? "Collect to bank" : "Collect to inventory"));
		
		return sleepUntil(() -> (!collectWidget.exists()));
	}
	
	private int maxSlots = -1; //Cache the max slots for future use
	public int getMaxSlots() {
		if (maxSlots > -1) return maxSlots;
		if (!isOpen()) return maxSlots;
		return (maxSlots = (getWidgetFinder().find((widget) -> (widget.getSpriteIndex1() == 1108)).exists() ? 3 : 8));
	}

	public int getAvailableSlots() {
		if (!isOpen()) return -1;
		return (getWidgetFinder().findAllFromText("Empty", (widget) -> (widget.getRootId() == getRootID() && widget.getThirdLevelId() == 16)).size() - ((getMaxSlots() == 3) ? 5 : 0));
	}
	
	public String getItemNameBySlot(int slot) {
		if (slot > getMaxSlots()) return "null";
		Widget itemWidget = getWidgetFinder().get(465, 6 + slot, 18);
		return (itemWidget.exists() ? ItemDefinition.forId(itemWidget.getRaw().getItemId()).getName() : "null");
	}
	
	public int getItemAmountBySlot(int slot) {
		if (slot > getMaxSlots()) return -1;
		Widget itemWidget = getWidgetFinder().get(465, 6 + slot, 18);
		return (itemWidget.exists() ? itemWidget.getRaw().getItemAmount() : -1);
	}
}
