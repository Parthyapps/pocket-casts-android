package au.com.shiftyjelly.pocketcasts.account.onboarding.recommendations

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import au.com.shiftyjelly.pocketcasts.account.onboarding.import.OnboardingImportFlow
import au.com.shiftyjelly.pocketcasts.account.onboarding.import.OnboardingImportFlow.importFlowGraph
import au.com.shiftyjelly.pocketcasts.utils.Network
import au.com.shiftyjelly.pocketcasts.localization.R as LR

object OnboardingRecommendationsFlow {

    const val route = "onboardingRecommendationsFlow"

    private const val start = "start"
    private const val search = "search"

    fun NavGraphBuilder.onboardingRecommendationsFlowGraph(
        flow: String,
        onBackPressed: () -> Unit,
        onComplete: () -> Unit,
        navController: NavController,
    ) {
        navigation(
            route = this@OnboardingRecommendationsFlow.route,
            startDestination = start
        ) {

            importFlowGraph(navController, flow)

            composable(start) {
                OnboardingRecommendationsStartPage(
                    onImportClicked = { navController.navigate(OnboardingImportFlow.route) },
                    onSearch = with(LocalContext.current) {
                        {
                            if (Network.isConnected(this)) {
                                navController.navigate(search)
                            } else {
                                Toast.makeText(
                                    this,
                                    this.getString(LR.string.error_check_your_internet_connection),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onBackPressed = onBackPressed,
                    onComplete = onComplete,
                )
            }
            composable(search) {
                OnboardingRecommendationsSearchPage(
                    onBackPressed = { navController.popBackStack() },
                )
            }
        }
    }
}
