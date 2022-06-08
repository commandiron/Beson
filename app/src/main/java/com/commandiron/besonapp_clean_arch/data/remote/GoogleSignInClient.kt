package com.commandiron.besonapp_clean_arch.data.remote

import android.content.Context
import com.commandiron.besonapp_clean_arch.core.StaticUrls.GOOGLE_SIGN_IN_CLIENT_SERVER_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(GOOGLE_SIGN_IN_CLIENT_SERVER_ID)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}

