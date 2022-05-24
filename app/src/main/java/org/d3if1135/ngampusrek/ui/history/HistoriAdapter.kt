import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if1135.ngampusrek.R
import org.d3if1135.ngampusrek.databinding.ItemHistoriBinding
import org.d3if1135.ngampusrek.db.NgampusEntity
import org.d3if1135.ngampusrek.model.KategoriReward
import org.d3if1135.ngampusrek.model.hitungReward
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter:
    ListAdapter<NgampusEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<NgampusEntity>() {
                override fun areItemsTheSame(
                    oldData: NgampusEntity, newData: NgampusEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: NgampusEntity, newData: NgampusEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: NgampusEntity) = with(binding) {
            val hasilReward = item.hitungReward()
            kategoriTextView.text = hasilReward.reward.toString().substring(0, 1)
            val colorRes = when(hasilReward.reward) {
                KategoriReward.TAS -> R.color.tas
                KategoriReward.LAPTOP-> R.color.laptop
                else -> R.color.headphone
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            biayaTextView.text = root.context.getString(R.string.hasil_x, hasilReward.price)
            rewardTextView.text = root.context.getString(R.string.reward_x, hasilReward.reward.toString())
            val prodi = root.context.getString(
                when (hasilReward.reward) {
                    KategoriReward.TAS -> R.string.tt
                    KategoriReward.LAPTOP -> R.string.rpla
                    else -> {R.string.si}
                }
            )
            dataTextView.text = root.context.getString(R.string.data_x, prodi) }
    }
}