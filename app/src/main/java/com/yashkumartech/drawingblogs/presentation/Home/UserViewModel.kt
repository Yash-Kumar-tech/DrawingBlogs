package com.yashkumartech.drawingblogs.presentation.Home
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import com.yashkumartech.drawingblogs.domain.model.User
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
////class UserViewModel(application: Application):
////    AndroidViewModel(application) {
////        private val auth = Firebase.auth
////        private val userLiveData = MutableLiveData<FirebaseUser?>()
////        private val nameLiveData = MutableLiveData<String?>()
////
////    init {
////        userLiveData.value = auth.currentUser
////    }
////
////    fun getUserLiveData(): LiveData<FirebaseUser?> {
////        return userLiveData
////    }
////
////    fun getNameLiveData(): LiveData<String?> {
////        return nameLiveData
////    }
////
////    fun setUser(user: FirebaseUser?) {
////        userLiveData.value = user
////        getUserName(user?.uid)
////    }
////
////    fun createUser(user: FirebaseUser?, userName: String) {
////        val userObj = User(user!!.uid, email = user.email + "", userName = userName)
////        val db = Firebase.firestore
////        db.collection("users")
////            .document(user.uid)
////            .set(userObj)
////    }
////
////    fun getUserName(uid: String?) {
////        if(uid == null) return
////        val db = Firebase.firestore
////        db.collection("users")
////            .document(uid)
////            .get()
////            .addOnCompleteListener {task ->
////                if(task.isSuccessful) {
////                    val content = task.result
////                    nameLiveData.value = content["userName"].toString().replaceFirstChar { it.uppercaseChar()  }
////                } else {
////                    Log.d("Error", "Error")
////                }
////
////            }.addOnFailureListener {
////                Log.d("User", "Unknown error ${it.toString()}")
////            }
////    }
////}
//
//
//@HiltViewModel
//class UserViewModel @Inject constructor(): ViewModel() {
//    private val auth = Firebase.auth
//        private val userLiveData = MutableLiveData<FirebaseUser?>()
//        private val nameLiveData = MutableLiveData<String?>()
//
//    init {
//        userLiveData.value = auth.currentUser
//    }
//
//    fun getUserLiveData(): LiveData<FirebaseUser?> {
//        return userLiveData
//    }
//
//    fun getNameLiveData(): LiveData<String?> {
//        Log.d("user", nameLiveData.value.toString())
//        return nameLiveData
//    }
//
//    fun setUser(user: FirebaseUser?) {
//        userLiveData.value = user
//        getUserName(user?.uid)
//    }
//
//    fun createUser(user: FirebaseUser?, userName: String) {
//        val userObj = User(user!!.uid, email = user.email + "", userName = userName)
//        val db = Firebase.firestore
//        db.collection("users")
//            .document(user.uid)
//            .set(userObj)
//    }
//
//    private fun getUserName(uid: String?) {
//        if(uid == null) return
//        val db = Firebase.firestore
//        db.collection("users")
//            .document(uid)
//            .get()
//            .addOnCompleteListener { task ->
//                if(task.isSuccessful) {
//                    val content = task.result
//                    Log.d("User", content["userName"].toString())
//                    nameLiveData.value = content["userName"].toString().replaceFirstChar { it.uppercaseChar()  }
//                } else {
//                    Log.d("Error", "Error")
//                }
//
//            }.addOnFailureListener {
//                Log.d("User", "Unknown error $it")
//            }
//    }
//}