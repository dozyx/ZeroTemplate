package cn.dozyx.zerofate.java

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


/**
 * @author dozeboy
 * @date 2018/7/8
 */

fun <T> printHashCode(t: T) {
    println(t?.hashCode())
    SingleInstance.foo()
}

fun main(args: Array<String>) {
    intDefault0()
}

class IntDefaut0Adapter : JsonDeserializer<Int> {
    override fun deserialize(json: JsonElement?,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): Int {
        if (json?.getAsString().equals("")) {
            return 0
        }
        try {
            return json!!.getAsInt()
        } catch (e: NumberFormatException) {
            return 0
        }
    }
}

fun intDefault0(){
    val jsonStr = """
        {
            "name":"承香墨影",
            "age":""
        }
    """.trimIndent()
    val user = GsonBuilder()
            .registerTypeAdapter(
                    Int::class.java,
                    IntDefaut0Adapter())
            .create()
            .fromJson(jsonStr,User1::class.java)
    println("user: ${user.toString()}")
}

class User1{
    var name = ""
    var age = 0
    override fun toString(): String {
        return """
            {
                "name":"${name}",
                "age":${age}
            }
        """.trimIndent()
    }
}


interface Processor<T> {
    fun process(): T
}

fun foo2() {
    val nothing:Nothing? = null
    nothing.hashCode()
}

class NoResultProcessor : Processor<Unit> {
    override fun process() {
        //...
        var a = "111"
        var ins = object : A() {
            override fun foo() {
                a + "222"
            }
        }
        a + "333"

        var b: String? = null
    }
}

open class A() {
    init {
        println("init")
    }

    open fun foo() {}

    companion object {
        fun foo() {
            println("foo")
        }
    }
}