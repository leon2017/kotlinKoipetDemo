package com.lightinthebox.kotlinkoipetdemo

import com.lightinthebox.api.annotations.LitbRequestBasic

class TestDemo(override var path: String = "",
               override var params: List<Pair<String, Any>> = mutableListOf()) : LitbRequestBasic() {
}