package com.agile4j.utils.scope

import java.util.concurrent.ConcurrentHashMap

/**
 * @author liurenpeng
 * Created on 2020-06-15
 */
class ContextScope {

    private val map = ConcurrentHashMap<ContextScopeKey<*>, Any>()

    operator fun <T> set(key: ContextScopeKey<T>, value: T?) =
        if (value != null) map.put(key, value) else map.remove(key)

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: ContextScopeKey<T>): T? {
        var value = map[key] as T?
        if (value == null && key.initializer != null) {
            value = key.initializer.get()
            value?.let {map.put(key, value)}
        }
        return value?:key.defaultValue
    }

    companion object ScopeUtils {
        private val scopeThreadLocal = ThreadLocal<ContextScope>()

        fun copyScope(scope: ContextScope?) : ContextScope {
            val result = ContextScope()
            result.map.putAll(scope?.map?: emptyMap())
            return result
        }

        fun currentScope() : ContextScope? = scopeThreadLocal.get()

        fun beginScope() {
            if (scopeThreadLocal.get() == null) scopeThreadLocal.set(ContextScope())
        }

        fun endScope() = scopeThreadLocal.remove()

        fun runWithExistScope(scope: ContextScope?, runner: () -> Unit) =
            supplyWithExistScope(scope) {runner.invoke()}

        fun <R> supplyWithExistScope(scope: ContextScope?, supplier: () -> R) : R {
            val oldScope = scopeThreadLocal.get()
            scopeThreadLocal.set(scope)
            try {
                return supplier.invoke()
            } finally {
                if (oldScope != null) {
                    scopeThreadLocal.set(oldScope)
                } else {
                    scopeThreadLocal.remove()
                }
            }
        }

        fun runWithNewScope(runner: () -> Unit) =
            runWithExistScope(ContextScope()) {runner.invoke()}

        fun <R> supplyWithNewScope(supplier: () -> R) : R =
            supplyWithExistScope(ContextScope()) {supplier.invoke()}
    }
}