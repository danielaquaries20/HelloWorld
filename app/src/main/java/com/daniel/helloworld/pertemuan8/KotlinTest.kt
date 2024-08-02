package com.daniel.helloworld.pertemuan8

class KotlinTest {
}

fun main() {
    //Set = Mengisi nilai
    //Get = Mengambil nilai
    var Pisang = Buah().apply {
        //Set
        rasa = "Manis"
        bentuk = "Lonjong"
        warna = "kuning"
    }

    println("Pisang :")
    println("Rasa: ${Pisang.rasa}")
    println("Bentuk: ${Pisang.bentuk}")
    println("Warna: ${Pisang.warna}")

    var Durian = BuahBerduri().apply {
        berduri = true
        rasa = "Manis"
        bentuk = "Bulat"
        warna = "Kuning Kecoklatan"
    }

    println("\nDurian :")
    println("Rasa: ${Durian.rasa}")
    println("Bentuk: ${Durian.bentuk}")
    println("Warna: ${Durian.warna}")
    println("Apakah Berduri: ${Durian.berduri}")

    var jeruk = Fruit("Asam", "Hijau", "Bulat")
    println("\nJeruk :")
    println("Rasa: ${jeruk.rasa}")
    println("Bentuk: ${jeruk.bentuk}")
    println("Warna: ${jeruk.warna}")

    println("\nJeruk: $jeruk")
    println("Pisang: ${Pisang.tampil()}")

    val jeruk2 = jeruk.copy("Manis", "Jingga")
    println("\nJeruk 2: $jeruk2")

}