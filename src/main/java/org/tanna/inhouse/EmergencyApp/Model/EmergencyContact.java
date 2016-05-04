package org.tanna.inhouse.EmergencyApp.Model;

public class EmergencyContact {
    private long id;
    private long userContactNo;
    private long emergencyContactNo;
    private boolean isAccepted;

    public EmergencyContact() {
    }

    public EmergencyContact(long id, long userContactNo, long emergencyContactNo, boolean isAccepted) {
        this.id = id;
        this.userContactNo = userContactNo;
        this.emergencyContactNo = emergencyContactNo;
        this.isAccepted = isAccepted;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserContactNo() {
        return this.userContactNo;
    }

    public void setUserContactNo(long userContactNo) {
        this.userContactNo = userContactNo;
    }

    public long getEmergencyContactNo() {
        return this.emergencyContactNo;
    }

    public void setEmergencyContactNo(long emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public boolean isAccepted() {
        return this.isAccepted;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}