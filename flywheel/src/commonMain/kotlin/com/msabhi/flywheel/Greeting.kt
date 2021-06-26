package com.msabhi.flywheel


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
