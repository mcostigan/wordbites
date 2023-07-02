package observer


interface Observer<T> {
    fun notifyOfChange(data: T)
}