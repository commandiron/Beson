package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import com.commandiron.besonapp_clean_arch.data.remote.AuthResultContract
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException

@Composable
fun GoogleSignInLauncher(
    signInRequestCode: Int,
    enabled: Boolean,
    onFailed:() -> Unit,
    onSuccessful:(GoogleSignInAccount) -> Unit
) {
    val authResultLauncher =
        rememberLauncherForActivityResult(AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    onFailed()
                } else {
                    onSuccessful(account)
                }
            } catch (e: ApiException) {
                onFailed()
            }
        }
    if(enabled){
        authResultLauncher.launch(signInRequestCode)
    }
}