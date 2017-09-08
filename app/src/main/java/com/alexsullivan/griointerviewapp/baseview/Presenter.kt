package com.alexsullivan.griointerviewapp.baseview

abstract class Presenter<T: ViewInterface> {
    var view: T? = null
    open fun attach(view: T) { this.view = view}
    open fun detach(view: T) { this.view = null}
}