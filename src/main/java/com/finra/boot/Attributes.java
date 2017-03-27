package com.finra.boot;

public class Attributes {

    public String getAccessTime() {
		return accessTime;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public boolean getIsOther() {
		return isOther;
	}

	public boolean getIsRegularFile() {
		return isRegularFile;
	}

	private final String accessTime;
    private final String modifiedTime;
    private final boolean isOther;
    private final boolean isRegularFile;

    public Attributes(String accessTime, String modifiedTime,boolean isOther, boolean isRegularFile) {
        this.accessTime = accessTime;
        this.modifiedTime = modifiedTime;
        this.isOther = isOther;
        this.isRegularFile = isRegularFile;
    }

    
}
