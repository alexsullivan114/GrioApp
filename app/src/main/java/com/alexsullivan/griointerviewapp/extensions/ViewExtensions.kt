package com.alexsullivan.griointerviewapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable

fun EditText.textObservable(): Observable<String> {
    return Observable.create<String>({
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                it.onNext(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //no-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //no-op
            }
        })
    })
}