package com.example.app;

/**
 * Created by 2km on 14. 1. 13.
 */
public class Member {
    private String username;
    private String password;
    private String groupName;
    private String phoneNumber;
    private int groupMemberCount;

    public Member(String username, String password, String groupName, String phoneNumber ,int groupMemberCount) {
        this.username = username;
        this.password = password;
        this.groupName = groupName;
        this.phoneNumber = phoneNumber;
        this.groupMemberCount = groupMemberCount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getGroupMemberCount() {
        return groupMemberCount;
    }
}
