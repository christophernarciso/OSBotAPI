package omniapi.webwalker.data;

public class IntList {

	private int[] src;
	private int count = 0;
	
	private int cachedCount = 0;
	private int[] cachedGet;
	
	public IntList() {
		src = new int[1];
	}
	
	public IntList(int initialSize) {
		src = new int[initialSize];
	}
	
	public void add(int i) {
		if (count >= src.length) resize(true);
		src[count] = i;
		count++;
	}
	
	public void remove(int index) {
		//dont need to remove right now
	}
	
	public boolean contains(int val) {
		for (int i = 0; i < count; i++) {
			if (src[i] == val) return true;
		}
		return false;
	}
	
	private void resize(boolean size) {
		int newLength = (size ? src.length * 2 : src.length);
		int[] newSrc = new int[newLength];
		int actualIndex = 0;
		for (int i = 0; i < src.length; i++) {
			newSrc[i] = src[i];
		}
		
		src = newSrc;
	}
	
	public int size() {
		return count;
	}
	
	
	
	public int[] get() {
		if (cachedCount == count && cachedGet.length > 0) {
			return cachedGet;
		}
		int[] retVal = new int[count];
		for (int i = 0; i < count; i++) retVal[i] = src[i];
		cachedGet = retVal;
		return retVal;
	}
}
