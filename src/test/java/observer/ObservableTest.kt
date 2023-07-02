package observer

import org.junit.jupiter.api.Test
import org.mockito.kotlin.eq

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class ObservableTest {

    var mockObserver1: Observer<Any> = mock()
    var mockObserver2: Observer<Any> = mock()
    var observable = Observable<Any>()


    @Test
    fun `should notify each subscriber of the event`() {
        observable.subscribe(mockObserver1)
        observable.subscribe(mockObserver2)

        val data = "test"
        observable.publish(data)
        verify(mockObserver1).notifyOfChange(eq(data))
        verify(mockObserver2).notifyOfChange(eq(data))
    }
}