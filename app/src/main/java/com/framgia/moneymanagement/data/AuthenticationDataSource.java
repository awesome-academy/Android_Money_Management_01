package com.framgia.moneymanagement.data;

import com.facebook.AccessToken;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationDataSource {
    interface Remote {
        void login(String userName, String password,
                   OnCompleteListener onCompleteListener,
                   OnFailureListener onFailureListener);

        FirebaseUser getCurrentUser();

        void saveUser(User user,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener);

        void loginWithGoogle(GoogleSignInAccount account,
                             OnCompleteListener onCompleteListener,
                             OnFailureListener onFailureListener);

        void loginWithFacebook(AccessToken token, DataCallback<FirebaseUser> callback);
    }

    interface Local {

    }
}
