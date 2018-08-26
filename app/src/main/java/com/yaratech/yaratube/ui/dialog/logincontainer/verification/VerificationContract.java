package com.yaratech.yaratube.ui.dialog.logincontainer.verification;

/**
 * Created by Vah on 8/17/2018.
 */

public interface VerificationContract {
    interface View{
        void closeDialog();

        void showMessage(String msg);
    }

    interface Presenter{
        void sendVerificationCode(String deviceId, String verificationCode,
                                  String nickname);
    }
}