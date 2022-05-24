package org.d3if1135.ngampusrek.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1135.ngampusrek.db.NgampusDao
import org.d3if1135.ngampusrek.db.NgampusEntity
import org.d3if1135.ngampusrek.model.HasilReward
import org.d3if1135.ngampusrek.model.KategoriReward
import org.d3if1135.ngampusrek.model.hitungReward

class HitungViewModel(private val db: NgampusDao) : ViewModel() {
    private val hasilReward = MutableLiveData<HasilReward?>()
    private val navigasi = MutableLiveData<KategoriReward?>()
    fun hitungKursus(time: Float, isStudy: Boolean,majorGroup: String, name: String, courseGroup: String) {
        val dataUtbk = NgampusEntity(
            time = time,
            isStudy = isStudy,
            major_group = majorGroup,
            name = name,
            course_group = courseGroup
        )
        hasilReward.value = dataUtbk.hitungReward()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataUtbk)
            }
        }
//        val weekOfMonth = 4
//        val price = (weekOfMonth * time * 50000).toString().toDouble()
//        val kategori = getKategori(price, isStudy)
//        hasilReward.value = HasilReward(price, kategori)
    }

    fun getHasilUtbk(): LiveData<HasilReward?> = hasilReward

    fun mulaiNavigasi() {
        navigasi.value = hasilReward.value?.reward
    }
    fun selesaiNavigasi() {
        navigasi.value = null
    }
    fun getNavigasi() : LiveData<KategoriReward?> = navigasi
}