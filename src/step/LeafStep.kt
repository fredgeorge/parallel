/*
 * Copyright (c) 2021 by Fred George
 * May be used freely except for training; license required for training.
 * @author Fred George  fredgeorge@acm.org
 */

package step

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LeafStep(vararg steps: Step) {
    private val steps = steps.toList()
    fun execute(): Boolean {
        return runBlocking {
            val jobs = steps.map { step -> async { step.execute() } }
            var result = true
            for (job in jobs) result = result && job.await()
            result
        }
    }

    interface Step {
        suspend fun execute(): Boolean
    }

    internal class SuccessStep : Step {

        override suspend fun execute(): Boolean {
            delay(2000) // Sleep interrupts all threads!
            return true
        }
    }

    internal class FailureStep : Step {

        override suspend fun execute(): Boolean {
            delay(5000) // Sleep interrupts all threads!
            return false
        }
    }
}