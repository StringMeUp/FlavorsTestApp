package com.string.me.up.eqandroidtestapp.details

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.*
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.string.me.up.eqandroidtestapp.R
import com.string.me.up.eqandroidtestapp.databinding.FragmentDetailsBinding
import com.string.me.up.eqandroidtestapp.model.ItemDetails
import com.string.me.up.eqandroidtestapp.util.*


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel
    private val downloadReceiver = DefaultReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel = ViewModelProvider(this@DetailsFragment)[DetailsViewModel::class.java]
        setLifecycle(binding, detailsViewModel)
        val itemDetails = arguments?.get(Constants.single_item) as ItemDetails

        binding.buttonDownload.setOnClickListener {
            requireContext().downloadImage(itemDetails.image)
        }

        detailsViewModel.setItemDetails(itemDetails)
        detailsViewModel.androidRatings.observe(viewLifecycleOwner, { androidRatings ->
            if (androidRatings == null) {
                binding.textViewNotRated.show()
            } else {
                binding.textViewNotRated.hide()
            }
        })

        detailsViewModel.iosRatings.observe(viewLifecycleOwner, { iosRatings ->
            if (iosRatings == null) {
                binding.textViewNotRated.show()
            } else {
                binding.textViewNotRated.hide()
            }
        })

        detailsViewModel.type.observe(viewLifecycleOwner, { type ->
            type?.let {
                if (it == getString(R.string.label_device_android)) {
                    binding.imageViewType.setImageResource(R.drawable.android_bug)
                    binding.imageViewPlayStore.setImageResource(R.drawable.play_icon)
                    binding.textViewPlayStore.setText(R.string.label_play_store)
                } else {
                    binding.imageViewType.setImageResource(R.drawable.apple)
                    binding.imageViewPlayStore.setImageResource(R.drawable.appstore)
                    binding.textViewPlayStore.setText(R.string.label_app_store)
                }
            }
        })

        detailsViewModel.appSize.observe(viewLifecycleOwner, { appSize ->
            if (appSize == null)
                binding.textViewAppSize.text = getString(R.string.label_app_size_unknown)
            else binding.textViewAppSize.text = SpannableStringBuilder().append(appSize.toString())
                .append(getString(R.string.label_app_size_in_bytes))
        })
    }

    private fun setLifecycle(binding: FragmentDetailsBinding, detailsViewModel: DetailsViewModel) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = detailsViewModel
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(
            downloadReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    override fun onResume() {
        super.onResume()
        activity?.unregisterReceiver(downloadReceiver)
    }
}