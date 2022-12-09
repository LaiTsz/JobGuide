package com.project.job_guide.data.db.repository

import com.project.job_guide.data.model.CreateUser
import com.project.job_guide.data.model.Login
import com.project.job_guide.data.db.remote.FirebaseAuthSource
import com.project.job_guide.data.db.remote.FirebaseAuthStateObserver
import com.project.job_guide.data.Result
import com.google.firebase.auth.FirebaseUser

class AuthRepository{
    private val firebaseAuthService = FirebaseAuthSource()

    fun observeAuthState(stateObserver: FirebaseAuthStateObserver, b: ((Result<FirebaseUser>) -> Unit)){
        firebaseAuthService.attachAuthStateObserver(stateObserver,b)
    }

    fun loginUser(login: Login, b: ((Result<FirebaseUser>) -> Unit)) {
        b.invoke(Result.Loading)
        firebaseAuthService.loginWithEmailAndPassword(login).addOnSuccessListener {
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener {
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun createUser(createUser: CreateUser, b: ((Result<FirebaseUser>) -> Unit)) {
        b.invoke(Result.Loading)
        firebaseAuthService.createUser(createUser).addOnSuccessListener {
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener {
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun logoutUser() {
        firebaseAuthService.logout()
    }
}