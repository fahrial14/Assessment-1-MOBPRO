package org.d3if1135.ngampusrek.model

import org.d3if1135.ngampusrek.db.NgampusEntity

fun NgampusEntity.hitungReward(): HasilReward {
    val weekOfMonth = 4
    val price = (weekOfMonth * time * 50000).toString().toDouble()
    val kategori = if (isStudy) {
        when {
            price < 249000 -> KategoriReward.TAS
            price >= 599000 -> KategoriReward.LAPTOP
            else -> KategoriReward.HEADPHONE
        }
    } else {
        when {
            price < 299000 -> KategoriReward.TAS
            price >= 549000 -> KategoriReward.LAPTOP
            else -> KategoriReward.HEADPHONE
        }
    }
    return HasilReward(price, kategori)
}