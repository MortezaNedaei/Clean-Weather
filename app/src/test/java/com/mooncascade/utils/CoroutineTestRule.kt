package com.mooncascade.utils


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestWatcher
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test


class CoroutineTestRule @ExperimentalCoroutinesApi constructor(
    val dispatcher: TestDispatcher = StandardTestDispatcher(),
    val scope: TestScope = TestScope(dispatcher),
) : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcherProvider = object :
        DispatcherProvider {
        override val io: CoroutineDispatcher = dispatcher
        override val ui: CoroutineDispatcher = dispatcher
        override val default: CoroutineDispatcher = dispatcher
        override val unconfined: CoroutineDispatcher = dispatcher
    }


    @ExperimentalCoroutinesApi
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }


    @ExperimentalCoroutinesApi
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun runBlockingTest(block: suspend TestScope.() -> Unit) = runTest {
        block()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAllEmissions() = runTest(dispatcher) {
        val values = mutableListOf<Int>()
        val stateFlow = MutableStateFlow(0)
        val job = launch {
            stateFlow.collect {
                values.add(it)
            }
        }
        stateFlow.value = 1
        stateFlow.value = 2
        stateFlow.value = 3
        job.cancel()
        // each assignment will immediately resume the collecting child coroutine,
        // so no values will be skipped.
        assertEquals(listOf(0, 1, 2, 3), values)
    }


}