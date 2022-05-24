package org.d3if1135.ngampusrek.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
//import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if1135.ngampusrek.R
import org.d3if1135.ngampusrek.databinding.FragmentHitungBinding
import org.d3if1135.ngampusrek.db.NgampusDb
import org.d3if1135.ngampusrek.model.HasilReward
import org.d3if1135.ngampusrek.model.KategoriReward
import java.text.NumberFormat
import java.util.*

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = NgampusDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Create an ArrayAdapter
        val adapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.list_prodi_name, android.R.layout.simple_spinner_item) }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prodiSpinner.adapter = adapter

        binding.prodiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_course_rpla,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.prodiTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
                if (p2 == 2) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_course_tt,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.prodiTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
                if (p2 == 3) {
                    val adapterChild = context?.let {
                        ArrayAdapter.createFromResource(
                            it,
                            R.array.list_course_si,
                            android.R.layout.simple_spinner_item
                        )
                    }
                    binding.prodiTextView.text = ""
                    adapterChild?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.kursusSpinner.adapter = adapterChild
                    binding.kursusTextView.text = ""
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.buttonHitung.setOnClickListener{ hitungBiaya() }
        binding.rewardButton.setOnClickListener {
            viewModel.mulaiNavigasi()
        }
        binding.shareButton.setOnClickListener { shareData() }
        viewModel.getHasilUtbk().observe(requireActivity(), { showResult(it) })
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections
                .actionHitungFragmentToRewardFragment(it))
            viewModel.selesaiNavigasi()
        })
    }

    private fun hitungBiaya(){
        val name = binding.namaLengkapInp.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, R.string.name_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val majorGroup = binding.prodiSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(majorGroup)) {
            Toast.makeText(context, R.string.major_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val courseGroup = binding.kursusSpinner.selectedItem.toString()
        if (TextUtils.isEmpty(courseGroup)) {
            Toast.makeText(context, R.string.course_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val time = binding.waktuKursusInp.text.toString()
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(context, R.string.time_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungKursus(
            time.toFloat(),
            majorGroup == R.id.prodiSpinner.toString(),
            majorGroup, name, courseGroup
        )
    }

    private fun getKategoriLabel(kategori: KategoriReward): String {
        val stringRes = when (kategori) {
            KategoriReward.TAS -> R.string.tas
            KategoriReward.LAPTOP -> R.string.laptop
            KategoriReward.HEADPHONE -> R.string.headphone
        }
        return getString(stringRes)
    }

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }

    private fun showResult(result: HasilReward?) {
        if (result == null) return
        binding.paymentTextView.text = formatRupiah(result.price)
        binding.message.text = getString(R.string.short_message)
        binding.rewardTextView.text = getString(R.string.bonus, getKategoriLabel(result.reward))
        binding.buttonGroup.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_historiFragment
                )
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareData() {
        val prodi = binding.prodiSpinner.selectedItem.toString()
        val kursus = binding.kursusSpinner.selectedItem.toString()
        val message = getString(R.string.bagikan_template,
            binding.namaLengkapInp.text,
            prodi,
            kursus,
            binding.paymentTextView.text,
            binding.rewardTextView.text
        )
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}

