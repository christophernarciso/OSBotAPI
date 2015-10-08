package omniapi.finders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import omniapi.OmniScript;

import org.osbot.rs07.api.model.Item;

public class BankFinder extends VirtualFinder<Item> {

	public BankFinder(OmniScript script) {
		super(script);
	}

	@Override
	public Item find(FinderCondition<Item> condition) {
		return (last = getBankStream().filter((item) -> (item != null && condition.meetsCondition(item))).findAny().orElse(null));
	}
	
	public Item find(String name) {
		return find((item) -> (item.getName().equalsIgnoreCase(name)));
	}
	
	public Item find(String name, FinderCondition<Item> condition) {
		return (last = getBankStream().filter((item) -> (item != null && item.getName().equalsIgnoreCase(name) && condition.meetsCondition(item))).findAny().orElse(null));
	}
	
	public boolean canFind(String name) {
		return (find(name) != null);
	}
	
	public boolean canFind(String name, FinderCondition<Item> condition) {
		return (find(name, condition) != null);
	}
	
	public List<Item> findAll(FinderCondition<Item> condition) {
		return getBankStream().filter((item) -> (item != null && condition.meetsCondition(item))).collect(Collectors.toList());
	}
	
	/* Private methods */
	private Stream<Item> getBankStream() {
		return Arrays.asList(getBank().getItems()).stream();
	}
}
