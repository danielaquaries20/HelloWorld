package com.daniel.helloworld

import com.daniel.helloworld.mytest.class_object.person.Person
import net.objecthunter.exp4j.ExpressionBuilder

fun main() {
    trialCh11()
    //Comment
}

fun trialCh11() {
//    penambahan(10,5)
    val penambahan2 = {x: Int, y: Int -> x+y}
    println(penambahan(10, 5))
    println(penambahan2(10, 5))

    var msg : String?
    msg = "Belajar"


    msg?.let {data ->
        println(data)
    }
    msg?.run {
        println(this)
    }
    /*if (msg != null) {
        println(msg)
    }*/
}


fun trialCh8() {
    var person = Person("Budi", "430924131487", "9 - Jan - 2001")
    println(person)
    /*var mangga: Buah? = Buah(
        "Kuning", "Manis", "Bulat", "Mangga",
        "Tinggi",
        "Lonjong-Hijau",
        "Tunggang",
        "Generatif dan Vegetatif buatan"
    )*/

    /* mangga?.tubuh = "Tinggi"
     mangga?.daun = "Lonjong"
     mangga?.akar = "Tunggang"
     mangga?.perkembangbiakan = "Generatif dan Vegetatif buatan"
     mangga?.nama = "Mangga"*/
    /*mangga?.setValueTumbuhan(
        "Mangga",
        "Tinggi",
        "Lonjong-Hijau",
        "Tunggang",
        "Generatif dan Vegetatif buatan"
    )*/
    /* println("Nama tumbuhan: ${mangga?.getNama()}")
     println("Tubuh: ${mangga?.getTubuh()}")
     println("Daun: ${mangga?.getDaun()}")
     println("Akar: ${mangga?.getAkar()}")
     println("Perkembangbiakan: ${mangga?.getPerkembangbiakan()}")*/
}


fun trialCh7() {
    tampilkanIdentitas("Daniel")
    val rangkingNilai = { nilai: Int ->
        when (nilai) {
            in 0..50 -> "E"
            in 51..60 -> "D"
            in 61..70 -> "C"
            in 71..80 -> "B"
            in 81..95 -> "A"
            in 96..100 -> "A+"
            else -> "Tak terdefinisi"
        }
    }
    println("Nilai: ${rangkingNilai(75)}")
    val desc = "Apakah Daniel pintar"
    println(desc.tanya())

    println(hitung(6, 10, ::penambahan))

    val pangkatDua = operation()
    println(pangkatDua(5))
    arrayLooping()
}

fun kondisi() {
    if (false) {
        //State True
    } else {
        //State False
    }
    val temperature = -10
    val heat = 190
    when {
        temperature >= 30 -> "Panas Broo"
        temperature < 30 && temperature > 10 -> "Its Okey gaes"
        temperature <= 10 -> "Dingin broow"

    }
}

fun arrayLooping() {
    val barang = arrayOf(1, "Celana", 5f, 'B', 40.6)
    val barangs = arrayOf("Baju", "Celana", "Bunga")
    val nilaiSiswa = intArrayOf(4, 1, 3, 4, 1, 3)

    barangs.forEach {
        print("$it-")
    }
    println()

    for (i in 0..10) {
        println(i)
    }

    var count = 100
    var loop = true
    while (loop) {
        if (count >= 100) loop = false
        print("${count++}-")
    }

    /*do {
        print("${count++}-")
        if (count >= 100) loop = false
    } while (loop)*/
}

fun hitung(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

fun penambahan(x: Int, y: Int) = x + y

fun operation(): (Int) -> Int {
    return ::kuadrat
}

fun kuadrat(x: Int) = x * x

fun String.tanya(): String {
    return "$this?"
}

fun tampilkanIdentitas(nama: String, sekolah: String = "SMKN 11 Semarang") {
    println("$nama - $sekolah")
}


fun pertemuan6() {
    val a: Int = 5
    val b: Int = 11

    var work = "(2 + 4) * 5 - 4 / 2"
    println(work)
    work += " + (9-3)"
    println(work)

    println(work)
    val expression = ExpressionBuilder(work).build()
    val result = expression.evaluate()

    println(result)

    /*Aritmatika
    //tambah
    println(a+b)

    //kurang
    println(a-b)

    //kali
    println(a*b)

    //bagi
    println(b/a)

    //modulo
    println(b%a)*/

}