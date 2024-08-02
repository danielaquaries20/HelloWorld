package com.daniel.helloworld.pertemuan6

import com.daniel.helloworld.kuadrat

fun main() {
    //Membuat Layout
    //Edit Text, Button, View Group?
    //Bagaimana caranya mengambil data "text" di Edit Text?
    //Bagaimana membuat fungsi perulangan gambar yang akan di tampilkan di "container" View Group kita?
        // Fungsi itu nanti yang akan di eksekusi ketika Button di klik!

    //Add View
}

fun ch07() {
    tampilkanIdentitas("Budi")

    val rangkingNilai = { nilai: Int ->
        when (nilai) {
            in 0..60 -> "Cukup"
            in 61..80 -> "Baik"
            in 81..100 -> "Baik Sekali"
            else -> "Tidak terdefinisikan"
        }
    }
    println("Rangking Nilai: ${rangkingNilai(75)}")

    val pertanyaan = "Apakah Budi baik"
    println(pertanyaan.tambahTanya())

    println(hitung(10, 5, ::pengurangan))

    val operasiKaliDua = operation()
    println(operasiKaliDua(6))

    var nilai = 101
    val reaction = when (nilai) {
        in 0..60 -> "Cukup"
        in 61..80 -> "Baik"
        in 81..100 -> "Baik Sekali"
        else -> "Tidak terdefinisikan"
    }
    println(reaction)

    val barangBelanja =
        arrayOf(
            "Baju",
            "Celana",
            "Jam Tangan",
            "Handphone",
            "Buah",
            "Sayur",
            "Beras",
            1,
            2,
            3,
            12.5,
            9234230,
            50f
        )
    val nilaiSiswa = intArrayOf(40, 50, 60, 70, 80, 90, 100)
    nilaiSiswa.forEach { data ->
        print(" $data,")
    }

    println()
    for (i in 1..10) {
        print("$i -> ")
    }

    var angka = 0
    while (angka < 5) {
        println("While")
        angka++
    }
    //angka = 5
    println(angka)
    do {
        println("Do-While")
        angka++
    } while (angka < 5)

}


fun operation(): (Int) -> Int {
    return ::kalidua
}

fun kalidua(x: Int) = x * 2
fun kaliTiga(x: Int) = x * 3
fun kaliEmpat(x: Int) = x * 4
fun bagiTiga(x: Int) = x / 3

fun hitung(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

fun penambahan(x: Int, y: Int) = x + y

fun pengurangan(x: Int, y: Int) = x - y


fun String.tambahTanya(): String {
    return "$this?"
}

fun tampilkanIdentitas(nama: String, sekolah: String = "SMKN 1 Kendal") {
    println("Nama: $nama, Sekolah: $sekolah")
}