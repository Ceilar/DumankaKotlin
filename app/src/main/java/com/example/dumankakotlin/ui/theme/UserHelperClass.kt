package com.example.dumankakotlin

class UserHelperClass {
    var email: String? = null
    var name: String? = null
    var uid: String? = null
    var words = 0
    var equations = 0

    constructor() {}
    constructor(email: String?, name: String?, uid: String?, words: Int, equations: Int) {
        this.email = email
        this.name = name
        this.uid = uid
        this.words = words
        this.equations = equations
    }
}