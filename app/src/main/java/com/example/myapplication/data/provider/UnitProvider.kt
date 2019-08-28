package com.example.myapplication.data.provider

import com.example.myapplication.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}