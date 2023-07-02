package observer

class Observable<T> {
    private var observers: MutableCollection<Observer<T>> = mutableListOf()
    fun subscribe(observer: Observer<T>) {
        observers.add(observer)
    }

    fun publish(data: T) {
        observers.forEach { it.notifyOfChange(data) }
    }

}