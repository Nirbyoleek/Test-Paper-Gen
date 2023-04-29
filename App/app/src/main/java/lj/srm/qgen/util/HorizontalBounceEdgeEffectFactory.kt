package lj.srm.qgen.util


import android.graphics.Canvas
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView

private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

class HorizontalBounceEdgeEffectFactory : RecyclerView.EdgeEffectFactory() {

    override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {

        return object : EdgeEffect(recyclerView.context) {

            var translationAnim: SpringAnimation? = null

            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            private fun handlePull(deltaDistance: Float) {

                val sign = if (direction == DIRECTION_RIGHT) -1 else 1
                val translationXDelta = sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                recyclerView.translationX += translationXDelta

                translationAnim?.cancel()
            }

            override fun onRelease() {
                super.onRelease()
                if (recyclerView.translationX != 0f) {
                    translationAnim = createAnim()?.also { it.start() }
                }
            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)

                val sign = if (direction == DIRECTION_RIGHT) -1 else 1
                val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                translationAnim?.cancel()
                translationAnim = createAnim().setStartVelocity(translationVelocity)?.also { it.start() }
            }

            override fun draw(canvas: Canvas?): Boolean {
                // don't paint the usual edge effect
                return false
            }

            override fun isFinished(): Boolean {

                return translationAnim?.isRunning?.not() ?: true
            }

            private fun createAnim() = SpringAnimation(recyclerView, SpringAnimation.TRANSLATION_X)
                    .setSpring(SpringForce()
                            .setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
                    )

        }
    }
}