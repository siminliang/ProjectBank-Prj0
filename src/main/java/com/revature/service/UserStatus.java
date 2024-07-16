package com.revature.service;

import com.revature.entity.User;

public class UserStatus {
    private User user;
    private boolean continueLoop;

    /*
        class created for storing current logged-in user information
        replacing the need to use control map for account management
     */

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

    public static void welcomeArt() {
        String yellow = "\u001B[33m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        System.out.println(yellow +
                "                 ;i.                             \n" +
                "                  M$L                    .;i.     \n" +
                "                  M$Y;                .;iii;;.   \n" +
                "                 ;$YY$i._           .iiii;;;;;   \n" +
                "                .iiiYYYYYYiiiii;;;;i;iii;; ;;;   \n" +
                "              .;iYYYYYYiiiiiiYYYiiiiiii;;  ;;;   \n" +
                "           .;iYYYY$$$$YYYYYYYYYYYYYYYii;; ;;;;   \n" +
                "         .YYY$$$$$$$$YYYYYY$$$$iiiY$$$$$$ii;;;;  \n" +
                "        :YYYF`,  TYYYYY$$$$$YYYYYYYi$$$$$iiiii;  \n" +
                "        Y$MM: \\  :YYYY$$P\"````\"T$YYMMMMMMMMiiYY.\n" +
                "     `.;$$M$$b.,dYY$$Yi; .(     .YYMMM$$$MMMMYY \n" +
                "   .._$MMMMM$!YYYYYYYYYi;.`\"  .;iiMMM$MMMMMMMYY \n" +
                "    ._$MMMP` ```\"\"4$$$$$iiiiiiii$MMMMMMMMMMMMMY;\n" +
                "     MMMM$:       :$$$$$$$MMMMMMMMMMM$$MMMMMMMYYL\n" +
                "    :MMMM$$.    .;PPb$$$$MMMMMMMMMM$$$$MMMMMMiYYU:\n" +
                "     iMM$$;;: ;;;;i$$$$$$$MMMMM$$$$MMMMMMMMMMYYYYY\n" +
                "     `$$$$i .. ``:iiii!*\"``.$$$$$$$$$MMMMMMM$YiYYY\n" +
                "      :Y$$iii;;;.. ` ..;;i$$$$$$$$$MMMMMM$$YYYYiYY:\n" +
                "       :$$$$$iiiiiii$$$$$$$$$$$MMMMMMMMMMYYYYiiYYYY.\n" +
                "        `$$$$$$$$$$$$$$$$$$$$MMMMMMMM$YYYYYiiiYYYYYY\n" +
                "         YY$$$$$$$$$$$$$$$$MMMMMMM$$YYYiiiiiiYYYYYYY\n" +
                "        :YYYYYY$$$$$$$$$$$$$$$$$$YYYYYYYiiiiYYYYYYi'\n\n" +
                green +
                "                Welcome to Doge Bank! \n" +
                "            Doge has coins, if you has wares" +
                reset);
    }
}
