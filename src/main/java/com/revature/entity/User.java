package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private int user_id;

    public User(){}
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        StringBuilder printablePassword = new StringBuilder();
        //prevent application error when no password is entered
        if(password.length() > 0)
            printablePassword = new StringBuilder(password.charAt(0) + "*".repeat(password.length()-1));
        return "User{" +
                "user_id" + user_id +
                ",username='" + username + '\'' +
                ", password='" + printablePassword + '\'' +
                '}';
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
