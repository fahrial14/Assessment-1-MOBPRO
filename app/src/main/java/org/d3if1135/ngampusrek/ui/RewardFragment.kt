package org.d3if1135.ngampusrek.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if1135.ngampusrek.R
import org.d3if1135.ngampusrek.databinding.FragmentRewardBinding
import org.d3if1135.ngampusrek.model.KategoriReward

class RewardFragment : Fragment() {
    private lateinit var binding: FragmentRewardBinding
    private val args: RewardFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRewardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriReward) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriReward.TAS -> {
                actionBar?.title = getString(R.string.tas)
                binding.imageView.setImageResource(R.drawable.tas)
                binding.textView.text = getString(R.string.detail_tas)
            }
            KategoriReward.LAPTOP -> {
                actionBar?.title = getString(R.string.laptop)
                binding.imageView.setImageResource(R.drawable.laptop)
                binding.textView.text = getString(R.string.detail_laptop)
            }
            KategoriReward.HEADPHONE -> {
                actionBar?.title = getString(R.string.headphone)
                binding.imageView.setImageResource(R.drawable.headphone)
                binding.textView.text = getString(R.string.detail_hedphone)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { updateUI(args.reward) }
}
