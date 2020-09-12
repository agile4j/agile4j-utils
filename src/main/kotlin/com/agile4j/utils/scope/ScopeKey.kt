package com.agile4j.utils.scope

import com.agile4j.utils.scope.Scope.ScopeUtils.currentScope
import java.util.function.Supplier

/**
 * @author liurenpeng
 * Created on 2020-06-15
 */
class ScopeKey<T> private constructor(
    val defaultValue: T?,
    val initializer: Supplier<T>?
) {

    /**
     * @return true if in a scope and set success.
     */
    fun set(value: T): Boolean {
        val scope = currentScope() ?: return false
        scope[this] = value
        return true
    }

    fun get(): T? = currentScope()?.get(this) ?: defaultValue

    companion object ScopeKeyUtils {

        fun <T> withDefaultValue(defaultValue: T?) = ScopeKey(defaultValue, null)

        fun <T> withInitializer(initializer: Supplier<T>?) = ScopeKey(null, initializer)
    }
}