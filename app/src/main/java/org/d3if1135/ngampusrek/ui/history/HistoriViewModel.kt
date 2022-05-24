package org.d3if1135.ngampusrek.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1135.ngampusrek.db.NgampusDao

class HistoriViewModel(private val db: NgampusDao) : ViewModel() {
    val data = db.getLastUtbk()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}