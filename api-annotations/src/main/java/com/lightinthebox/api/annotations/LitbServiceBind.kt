package com.lightinthebox.api.annotations

import kotlin.reflect.KClass


/**
 * @param serviceName 注解的model类实现的网络服务名
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class LitbServiceBind(
    val serviceName: String = "",
    val path: String = ""
)
