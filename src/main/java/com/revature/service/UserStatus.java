package com.revature.service;

import com.revature.entity.User;

public class UserStatus {
    private User user;
    private boolean continueLoop;

    public UserStatus(){
        continueLoop = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getContinueLoop() {
        return continueLoop;
    }

    public void setContinueLoop(boolean continueLoop) {
        this.continueLoop = continueLoop;
    }
}
