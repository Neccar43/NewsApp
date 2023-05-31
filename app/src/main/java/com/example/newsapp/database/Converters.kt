package com.example.newsapp.database

import androidx.room.TypeConverter
import com.example.newsapp.model.Source

class Converters {
    //primitive tipler haricindeki verileri room da saklamak için dönüştücü kullanmamız gerek
    @TypeConverter
    fun fromSource(source: Source):String{
        //id kısmına gerek olmadığı için sadece name kısmını aldık
        //room her source nesnesi ile işlem görmeye çalıştığında onu stringmiş gibi işleyecek
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }

}