package com.framgia.moneymanagement.data.source.remote;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.framgia.moneymanagement.data.AuthenticationDataSource;
import com.framgia.moneymanagement.data.DataCallback;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    public AuthenticationRemoteDataSource(FirebaseAuth auth, FirebaseDatabase firebaseDatabase) {
        mAuth = auth;
        mFirebaseDatabase = firebaseDatabase;
    }

    @Override
    public void login(String userName, String password,
                      OnCompleteListener onCompleteListener,
                      OnFailureListener onFailureListener) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void saveUser(User user, OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener) {
        mFirebaseDatabase.getReference(User.Key.USER)
                .child(getCurrentUser().getUid()).setValue(user)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void loginWithGoogle(GoogleSignInAccount account,
                                OnCompleteListener onCompleteListener,
                                OnFailureListener onFailureListener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).
                addOnCompleteListener(onCompleteListener).
                addOnFailureListener(onFailureListener);
    }

    @Override
    public void loginWithFacebook(AccessToken token, final DataCallback<FirebaseUser> callback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callback.onGetDataSucces(task.getResult().getUser());
                } else {
                    callback.onGetDataFailed(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onGetDataFailed(e.getMessage());
            }
        });
    }

    @Override
    public void createAccount(String userName, String password,
                              OnCompleteListener onCompleteListener,
                              OnFailureListener onFailureListener) {
        mAuth.createUserWithEmailAndPassword(userName, password).
                addOnCompleteListener(onCompleteListener).
                addOnFailureListener(onFailureListener);
    }
}
