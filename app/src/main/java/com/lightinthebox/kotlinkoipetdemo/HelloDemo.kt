package com.lightinthebox.kotlinkoipetdemo

import com.lightinthebox.api.annotations.LitbServiceBind

@LitbServiceBind("HelloWang")
data class HelloDemo(
    var hello: String
)
