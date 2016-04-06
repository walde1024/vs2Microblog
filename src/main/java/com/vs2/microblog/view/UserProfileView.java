package com.vs2.microblog.view;

/**
 * Created by Walde on 06.04.16.
 */
public class UserProfileView {

    private String firstname;
    private String lastname;
    private String iFollowCount;
    private String followMeCount;
    private String email;

    public UserProfileView(String firstname, String lastname, String iFollowCount, String followMeCount, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.iFollowCount = iFollowCount;
        this.followMeCount = followMeCount;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getiFollowCount() {
        return iFollowCount;
    }

    public void setiFollowCount(String iFollowCount) {
        this.iFollowCount = iFollowCount;
    }

    public String getFollowMeCount() {
        return followMeCount;
    }

    public void setFollowMeCount(String followMeCount) {
        this.followMeCount = followMeCount;
    }
}
