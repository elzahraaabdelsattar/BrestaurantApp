package com.app.srestaurantapplication.util

import android.content.Context
import android.content.Intent
import com.app.srestaurantapplication.ui.ReviewActivity
import com.app.srestaurantapplication.ui.detailsFoodMenu.DetailsFoodMenuActivity
import com.app.srestaurantapplication.ui.foodMenu.FoodMenuActivity


fun Context.navigateToFoodMenuActivity() {
    startActivity(Intent(this, FoodMenuActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToDetailsFoodMenuActivity() {
    startActivity(Intent(this, DetailsFoodMenuActivity::class.java))
    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

}
fun Context.navigateToReviewActivity() {
    startActivity(Intent(this, ReviewActivity::class.java))
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