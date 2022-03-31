package org.d3if1135.ngampusrek

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if1135.ngampusrek.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this, R.array.list_prodi_name, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prodiSpinner.adapter = adapter

        binding.prodiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_course_rpla,
                        android.R.layout.simple_spinner_item
                    )
                    binding.prodiTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
                if (p2 == 2) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_course_tt,
                        android.R.layout.simple_spinner_item
                    )
                    binding.prodiTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
                if (p2 == 3) {
                    val adapterChild = ArrayAdapter.createFromResource(
                        this@MainActivity,
                        R.array.list_course_si,
                        android.R.layout.simple_spinner_item
                    )
                    binding.prodiTextView.text = ""
                    adapterChild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
                binding.buttonHitung.setOnClickListener{ hitungBiaya() }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun hitungBiaya(){
        val name = binding.namaLengkapInp.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val major = binding.prodiSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(major)) {
            Toast.makeText(this, R.string.major_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val course = binding.kursusSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(course)) {
            Toast.makeText(this, R.string.course_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val time = binding.waktuKursusInp.text.toString()
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, R.string.time_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val timeHours = time.toDouble()
        when (binding.prodiSpinner.selectedItem.toString()) {
            "D3 Rekayasa Perangkat Lunak Aplikasi" -> {
                val priceRpla = timeHours * getString(R.string.rpla).toDouble()
                binding.paymentTextView.text = formatRupiah(priceRpla)
                binding.message.text = getString(R.string.short_message)
            }
            "D3 Teknolongi Telekomunikasi" -> {
                val priceTt = timeHours * getString(R.string.tt).toDouble()
                binding.paymentTextView.text = formatRupiah(priceTt)
                binding.message.text = getString(R.string.short_message)
            }
            else -> {
                val priceSi = timeHours * getString(R.string.si).toDouble()
                binding.paymentTextView.text = formatRupiah(priceSi)
                binding.message.text = getString(R.string.short_message)
            }
        }
    }

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }
}

