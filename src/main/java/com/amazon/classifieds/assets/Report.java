package com.amazon.classifieds.assets;

public class Report {
	private int totalUser;
	private int activeUsers;
	private int deActivatedUser;
	private int totalClassifieds;
	private int pendingClassifieds;
	private int approvedClassifieds;
	private int rejectedClassifieds;
	private float averageProductPrice;
	
	public Report(int totalUser, int activeUsers, int deActivatedUser, int totalClassifieds, int pendingClassifieds,
			int approvedClassifieds, int rejectedClassifieds, float averageProductPrice) {
		super();
		this.totalUser = totalUser;
		this.activeUsers = activeUsers;
		this.deActivatedUser = deActivatedUser;
		this.totalClassifieds = totalClassifieds;
		this.pendingClassifieds = pendingClassifieds;
		this.approvedClassifieds = approvedClassifieds;
		this.rejectedClassifieds = rejectedClassifieds;
		this.averageProductPrice = averageProductPrice;
	}
	
	public int getTotalUser() {
		return totalUser;
	}
	public int getActiveUsers() {
		return activeUsers;
	}
	public int getDeActivatedUser() {
		return deActivatedUser;
	}
	public int getTotalClassifieds() {
		return totalClassifieds;
	}
	public int getPendingClassifieds() {
		return pendingClassifieds;
	}
	public int getApprovedClassifieds() {
		return approvedClassifieds;
	}
	public int getRejectedClassifieds() {
		return rejectedClassifieds;
	}
	public float getAverageProductPrice() {
		return averageProductPrice;
	}
	
	
	
	
}
