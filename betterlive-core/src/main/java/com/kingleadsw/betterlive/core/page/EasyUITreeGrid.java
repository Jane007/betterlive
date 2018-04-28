package com.kingleadsw.betterlive.core.page;



import java.util.ArrayList;
import java.util.List;

public abstract class EasyUITreeGrid {
	
	private static final String OPEN = "open";
	private static final String CLOSED = "closed";
	
	protected String state = CLOSED;
	protected List<EasyUITreeGrid> children = new ArrayList<EasyUITreeGrid>(0);

	public String getState() {
		return state;
	}

	public void open() {
		this.state = OPEN;
	}
	
	public void close() {
		this.state = CLOSED;
	}
	
	public void addChild(EasyUITreeGrid child) {
		children.add(child);
	}
	
	public List<EasyUITreeGrid> getChildren() {
		return children;
	}
	
}
