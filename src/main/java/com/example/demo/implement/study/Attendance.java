package com.example.demo.implement.study;

public enum Attendance {
	
	YES(true),
	NO(false);

	private final boolean isPresent;

	Attendance(boolean isPresent) {
		this.isPresent = isPresent;
	}

	public boolean isPresent() {
		return isPresent;
	}
}
