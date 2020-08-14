package com.renovavision.heroesapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.SkeletonNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.animation.ModelAnimator.INFINITE
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_hero.*

class HeroActivity : AppCompatActivity(R.layout.activity_hero) {

    private lateinit var arFragment: ArFragment
    private lateinit var data: Model
    private var renderable: ModelRenderable? = null
    private var animator: ModelAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getSerializableExtra(MODEL_NAME) as Model

        val uri = Uri.parse(data.model)

        arFragment = sceneformFragment as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, _: MotionEvent ->
            if (plane.type != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                return@setOnTapArPlaneListener
            }
            val anchor = hitResult.createAnchor()
            placeObject(arFragment, anchor, uri)
        }

        animateModel(data.emptyState)

        firstActionBtn.setOnClickListener { animateModel(data.firstAction) }
        idleBtn.setOnClickListener { animateModel(data.emptyState) }

        if (data.secondAction != EMPTY) {
            secondActionBtn.setOnClickListener { animateModel(data.secondAction) }
        } else {
            secondActionBtn.visibility = View.GONE
        }
    }

    private fun animateModel(name: String) {
        animator?.let { it ->
            if (it.isRunning) {
                it.end()
            }
        }
        renderable?.let { modelRenderable ->

            val data = modelRenderable.getAnimationData(name)
            animator = ModelAnimator(data, modelRenderable)

            if (name == this.data.emptyState) {
                animator?.repeatCount = INFINITE
            }

            animator?.start()
        }
    }

    private fun placeObject(fragment: ArFragment, anchor: Anchor, model: Uri) {
        ModelRenderable.builder()
            .setSource(fragment.context, model)
            .build()
            .thenAccept {
                renderable = it
                addToScene(fragment, anchor, it)
            }
            .exceptionally {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .setTitle("Error")
                    .create()
                    .show()

                null
            }
    }

    private fun addToScene(fragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)

        val skeletonNode = SkeletonNode()
        skeletonNode.renderable = renderable

        val node = TransformableNode(fragment.transformationSystem)
        node.addChild(skeletonNode)
        node.setParent(anchorNode)

        fragment.arSceneView.scene.addChild(anchorNode)
    }
}