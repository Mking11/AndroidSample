package com.mking11.androidDemo.common.utils

abstract class DtoToDboMapper<Dto,Dbo> {
    abstract fun toDto():Dto
    abstract fun toDbo():Dbo
}