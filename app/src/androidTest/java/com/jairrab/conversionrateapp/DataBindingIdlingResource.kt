package com.jairrab.conversionrateapp

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import java.util.*
import kotlin.collections.ArrayList

/**
 * An espresso idling resource implementation that reports idle status for all data binding
 * layouts.
 * <b/>
 * Since this application only uses fragments, the resource only checks the fragments instead
 * of the whole view tree.
 */
class DataBindingIdlingResource(
    private val activityTestRule: ActivityTestRule<*>
) : IdlingResource {
    // list of registered callbacks
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()
    // give it a unique id to workaround an espresso bug where you cannot register/unregister
    // an idling resource w/ the same name.
    private val id = UUID.randomUUID().toString()
    // holds whether isIdle is called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private var wasNotIdle = false

    override fun getName() = "DataBinding $id"

    override fun isIdleNow(): Boolean {
        val idle = !getBindings().union(getActivtyBinding()).any { it.hasPendingBindings() }
        @Suppress("LiftReturnOrAssignment")
        if (idle) {
            if (wasNotIdle) {
                // notify observers to avoid espresso race detector
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true
            // check next frame
            activityTestRule.activity.findViewById<View>(android.R.id.content).postDelayed({
                isIdleNow
            }, 16)
        }
        return idle
    }


    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }

    /**
     * Find all binding classes in all currently available fragments.
     */
    private fun getBindings(): List<ViewDataBinding> {
        return (activityTestRule.activity as? FragmentActivity)
                   ?.supportFragmentManager
                   ?.fragments
                   ?.mapNotNull {
                       it.view?.let { view ->
                           DataBindingUtil.getBinding<ViewDataBinding>(
                               view
                           )
                       }
                   } ?: emptyList()
    }

    /*private fun getActivtyBinding(): List<ViewDataBinding> {
        val decorView = activityTestRule.activity.window.decorView
        val contentView = decorView.findViewById(android.R.id.content) as ViewGroup

        val childs = contentView.childCount
        val childBindings = ArrayList<ViewDataBinding>(childs)
        for (i in 0 until childs) {
            val childAt = contentView.getChildAt(i)
            //Bind all childs of the content view e.g. all child views of the activity
            val binding = DataBindingUtil.getBinding<ViewDataBinding>(childAt)
            if (binding != null) {
                childBindings.add(binding)
            }
        }

        return childBindings.toList()
    }*/

    private fun getActivtyBinding(): List<ViewDataBinding> {
        val decorView = activityTestRule.activity.window.decorView
        val contentView = decorView.findViewById(android.R.id.content) as ViewGroup

        val childs = contentView.childCount
        val childBindings = ArrayList<ViewDataBinding>(childs)
        for (i in 0 until childs) {
            val childAt = contentView.getChildAt(i)
            val childsOfView = (childAt as ViewGroup).childCount
            for (j in 0 until childsOfView) {
                val viewChild = childAt.getChildAt(j)
                // Loop through all children to check if we have a RecyclerView
                if (viewChild is RecyclerView) {
                    for (k in 0 until viewChild.childCount) {
                        // Loop through all children of the RecyclerView to get their bindings and add them to our queue.
                        DataBindingUtil.getBinding<ViewDataBinding>(viewChild.getChildAt(k))?.let {
                            childBindings.add(it)
                        }
                    }
                }
            }

            // Add all 'root' views as well
            val binding = DataBindingUtil.getBinding<ViewDataBinding>(childAt)
            if (binding != null) {
                childBindings.add(binding)
            }
        }

        return childBindings.toList()
    }
}