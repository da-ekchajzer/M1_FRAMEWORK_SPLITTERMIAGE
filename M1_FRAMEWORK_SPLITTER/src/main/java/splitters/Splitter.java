package splitters;

import java.util.Map;

public interface Splitter {
	
	public Map<String, String> split(String inMessage);
	
}
