package com.daniel.helloworld.mytest.class_object.plant

class Buah(
    vWarna: String? = null,
    vRasa: String? = null,
    vBentuk: String? = null,
    vNama: String? = null,
    vTubuh: String? = null,
    vDaun: String? = null,
    vAkar: String? = null,
    vKembangbiak: String? = null
) : Tumbuhan() {
    private var warna: String?
    private var rasa: String?
    private var bentuk: String?

    init {
        warna = vWarna
        rasa = vRasa
        bentuk = vBentuk
        setValueTumbuhan(vNama, vTubuh, vDaun, vAkar, vKembangbiak)
    }
}