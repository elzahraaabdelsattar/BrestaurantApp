package com.app.taybatApplication.util

import android.content.Context
import android.content.Intent
import com.app.taybatApplication.ui.*
import com.app.taybatApplication.ui.bag.Bag
import com.app.taybatApplication.ui.check.CheckActivity
import com.app.taybatApplication.ui.foodMenu.FoodMenuActivity
import com.app.taybatApplication.ui.myOrders.MyOrdersActivity
import com.app.taybatApplication.ui.offers.OffersActivity
import com.app.taybatApplication.ui.orderDescription.OrderDescriptionActivity
import com.app.taybatApplication.ui.registeration.ForgetPasswordActivity
import com.app.taybatApplication.ui.registeration.RegistrationActivity
import com.app.taybatApplication.ui.registeration.SignInActivity
import com.app.taybatApplication.ui.registeration.SignUpActivity


fun Context.navigateToFoodMenuActivity() {
    startActivity(Intent(this, FoodMenuActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}

fun Context.navigateToForgetPasswordActivity() {
    startActivity(Intent(this, ForgetPasswordActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToConfirmationActivity() {
    startActivity(Intent(this, ConfirmationActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}


fun Context.navigateToReviewActivity() {
    startActivity(Intent(this, ReviewActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToBoutUs() {
    startActivity(Intent(this, AboutUs::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToBag() {
    startActivity(Intent(this, Bag::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToComplaint() {
    startActivity(Intent(this, ComplaintsActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToOffers() {
    startActivity(Intent(this, OffersActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToMyOrders() {
    startActivity(Intent(this, MyOrdersActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToBranches() {
    startActivity(Intent(this,BranchesActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToReservation() {
    startActivity(Intent(this, ReservationActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}

fun Context.navigateToOrderDescription() {
    startActivity(Intent(this, OrderDescriptionActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToSignIn() {
    startActivity(Intent(this, SignInActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToSignUp() {
    startActivity(Intent(this, SignUpActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}

fun Context.navigateToRegistration() {
    startActivity(Intent(this, RegistrationActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToCheckActivity() {
    startActivity(Intent(this, CheckActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}

fun Context.navigateToPaymentActivity() {
    startActivity(Intent(this, PaymentActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}

fun Context.navigateToLocationActivity() {
    startActivity(Intent(this, LocationActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}







////
////fun Context.navigateToIncomingCall(username: String, isVideo: Boolean, audioUrl: String, videoUrl: String) {
////    startActivity(IncomingCallActivity.buildIntent(this, username, isVideo, audioUrl, videoUrl).apply {
////        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////    })
////}
////
////fun Context.navigateToHistoryDetails(item: HistoryUiModel? = null, itemId: Int? = null,notificationID: Int? = null) {
////    startActivity(HistoryDetailsActivity.buildIntent(this, id = itemId, item = item, notificationId = notificationID))
////}
////
////fun Context.navigateToInvoices() {
////    startActivity(InvoicesActivity.buildIntent(this))
////}
////
////fun Context.navigateToProfile() {
////    startActivity(ProfileActivity.buildIntent(this))
////}
////
////fun Context.navigateToChangePassword() {
////    startActivity(ChangePasswordActivity.buildIntent(this))
////}
////
////fun Context.navigateToNotifications() {
////    startActivity(NotificationsActivity.buildIntent(this))
//}