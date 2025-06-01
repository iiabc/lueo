package com.hiusers.mc.lueo.system

abstract class BaseListContainer<T> {

    private val list: MutableList<T> = mutableListOf()

    fun add(value: T) {
        list.add(value)
    }

    open fun clear() {
        list.clear()
    }

    fun getList() = list

    fun getAll(): List<T> {
        return list
    }

}