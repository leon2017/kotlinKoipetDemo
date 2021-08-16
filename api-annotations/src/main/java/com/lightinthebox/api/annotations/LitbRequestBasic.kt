package com.lightinthebox.api.annotations

abstract class LitbRequestBasic {

    abstract var path: String

    abstract var params: List<Pair<String,Any>>
}