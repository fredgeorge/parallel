# parallel
A sample use of Kotlin Coroutines by Fred George
at _fredgeorge@acm.org_ in June 2021.

The root problem was creating a parallel composite command (Step)
that would really execute in parallel. The new Kotlin Coroutines
were used.

Note that delay() is not the same as sleep(); the latter actually
stops the entire application. delay() is only available in the 
coroutine scope, however.
