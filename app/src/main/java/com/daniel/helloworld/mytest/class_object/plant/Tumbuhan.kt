package com.daniel.helloworld.mytest.class_object.plant

open class Tumbuhan(
    vNama: String? = null,
    vTubuh: String? = null,
    vDaun: String? = null,
    vAkar: String? = null,
    vKembangbiak: String? = null
) {
    private var nama: String?
    private var tubuh: String?
    private var daun: String?
    private var akar: String?
    private var perkembangbiakan: String?

    //Modifier
    //public -> Dapat diakses dimana saja asalkan class tersebut sudah di inisialisasi
    //private -> Hanya bisa diakses di dalam class tersebut
    //protected -> Hanya bisa diakses di dalam class tersebut dan class yang jadi turunannya

    init {
        nama = vNama
        tubuh = vTubuh
        daun = vDaun
        akar = vAkar
        perkembangbiakan = vKembangbiak
    }

    fun setValueTumbuhan(
        vNama: String? = null,
        vTubuh: String? = null,
        vDaun: String? = null,
        vAkar: String? = null,
        vKembangbiak: String? = null
    ) {
        nama = vNama
        tubuh = vTubuh
        daun = vDaun
        akar = vAkar
        perkembangbiakan = vKembangbiak
    }

    fun setNama(value: String) {
        nama = value
    }

    fun getNama(): String? = nama
    fun getTubuh(): String? = tubuh
    fun getDaun(): String? = daun
    fun getAkar(): String? = akar
    fun getPerkembangbiakan(): String? = perkembangbiakan


}