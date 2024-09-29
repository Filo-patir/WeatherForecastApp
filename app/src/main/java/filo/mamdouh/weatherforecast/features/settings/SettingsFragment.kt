package filo.mamdouh.weatherforecast.features.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        windSpeedSetup()
        localizationSetup()
        tempSetup()
        binding.apply {
            arabicRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    langSetup(false)
                }
            }
            englishRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    langSetup(true)
                }
            }
        }

    }

    private fun setUpViews() {
        setUpLocationAnimation()
        setUpWinSpeedAnimation()
        setUpTempAnimation()
        setUpLangAnimation()
    }

    private fun setUpLocationAnimation() {
        binding.apply {
            locationBtn.setOnClickListener {
                if (locationRadioGroup.visibility == View.VISIBLE) {
                    locationBtn.animate().rotation(0f).start()
                    locationRadioGroup.apply {
                        animate().setStartDelay(600).translationY(-100f).setDuration(700).start()
                        animate().setStartDelay(500).alpha(0f).setDuration(1000).withEndAction {
                            visibility = View.GONE
                        }.start()
                    }
                } else {
                    locationBtn.animate().rotation(90f).setDuration(1000).start()
                    locationRadioGroup.apply {
                        animate().setStartDelay(600).translationY(0f).setDuration(800).start()
                        animate().setStartDelay(500).alpha(1f).setDuration(1000)
                            .withStartAction { visibility = View.VISIBLE }.start()
                    }
                }
            }
        }
    }

    private fun setUpTempAnimation() {
        binding.apply {
            tempBtn.setOnClickListener {
                if (tempRadioGroup.visibility == View.VISIBLE) {
                    tempBtn.animate().rotation(0f).start()
                    tempRadioGroup.apply {
                        animate().setStartDelay(600).translationY(-100f).setDuration(700).start()
                        animate().setStartDelay(500).alpha(0f).setDuration(1000).withEndAction {
                            visibility = View.GONE
                        }.start()
                    }
                }
                else {
                    tempBtn.animate().rotation(90f).setDuration(1000).start()
                    tempRadioGroup.apply {
                        animate().setStartDelay(600).translationY(0f).setDuration(800).start()
                        animate().setStartDelay(500).alpha(1f).setDuration(1000).withStartAction { visibility = View.VISIBLE }.start()
                    }
                }
            }
            celciusRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setUnit("C")
                    tempTxt.text = ContextCompat.getString(requireContext(), R.string.celcius_c)
                    Toast.makeText(requireContext(), "metric", Toast.LENGTH_SHORT).show()
                }
            }
            fhRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setUnit("F")
                    tempTxt.text = ContextCompat.getString(requireContext(), R.string.fahrenheit_f)
                    Toast.makeText(requireContext(), "imperial", Toast.LENGTH_SHORT).show()
                }
            }
            kelvinRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setUnit("K")
                    tempTxt.text = ContextCompat.getString(requireContext(), R.string.kelvin_c)
                    Toast.makeText(requireContext(), "standard", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun setUpWinSpeedAnimation() {
        binding.apply {
            winSpeedBtn.setOnClickListener {
                if (winSpeedRadioGroup.visibility == View.VISIBLE) {
                    winSpeedBtn.animate().rotation(0f).start()
                    winSpeedRadioGroup.apply {
                        animate().setStartDelay(600).translationY(-100f).setDuration(700).start()
                        animate().setStartDelay(500).alpha(0f).setDuration(1000).withEndAction {
                            visibility = View.GONE
                        }.start()
                    }
                }
                else {
                    winSpeedBtn.animate().rotation(90f).setDuration(1000).start()
                    winSpeedRadioGroup.apply {
                        animate().setStartDelay(600).translationY(0f).setDuration(800).start()
                        animate().setStartDelay(500).alpha(1f).setDuration(1000).withStartAction { visibility = View.VISIBLE }.start()
                    }
                }
            }
            meterPerSecondRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setWindSpeed("m/s")
                    windSpeedTxt.text = ContextCompat.getString(requireContext(), R.string.meter_sec)
                    Toast.makeText(requireContext(), "mps", Toast.LENGTH_SHORT).show()
                }
            }
            milesPerHourRB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.setWindSpeed("mph")
                    windSpeedTxt.text = ContextCompat.getString(requireContext(), R.string.miles_hour)
                    Toast.makeText(requireContext(), "mph", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun setUpLangAnimation() {
        binding.apply {
            languageBtn.setOnClickListener {
                if (languageRadioGroup.visibility == View.VISIBLE) {
                    languageBtn.animate().rotation(0f).start()
                    languageRadioGroup.apply {
                        animate().setStartDelay(600).translationY(-100f).setDuration(700).start()
                        animate().setStartDelay(500).alpha(0f).setDuration(1000).withEndAction {
                            visibility = View.GONE
                        }.start()
                    }
                }
                else {
                    languageBtn.animate().rotation(90f).setDuration(1000).start()
                    languageRadioGroup.apply {
                        animate().setStartDelay(600).translationY(0f).setDuration(800).start()
                        animate().setStartDelay(500).alpha(1f).setDuration(1000).withStartAction { visibility = View.VISIBLE }.start()
                    }
                }
            }
        }
    }

    private fun langSetup(flag: Boolean){
        if (flag){
            binding.englishRB.isChecked = true
            viewModel.setLocalization("en")
        }
        else{
            binding.arabicRB.isChecked = false
            viewModel.setLocalization("ar")
        }
        Toast.makeText(context, "Please Restart the app to apply the changes", Toast.LENGTH_SHORT).show()
    }
    private fun changLanguage(code: String) {
        val local = Locale(code)
        Locale.setDefault(local)
        val config = Configuration()
        config.setLocale(local)
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()
    }

    private fun tempSetup(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.unit.collect{
                    binding.apply {
                        when(it){
                            "C" -> {
                                tempTxt.text = ContextCompat.getString(requireContext(), R.string.celcius_c)
                                celciusRB.isChecked = true
                            }
                            "F" -> {
                                tempTxt.text = ContextCompat.getString(requireContext(), R.string.fahrenheit_f)
                                fhRB.isChecked = true
                            }
                            "K" -> {
                                tempTxt.text = ContextCompat.getString(requireContext(), R.string.kelvin_c)
                                kelvinRB.isChecked = true
                            }
                            else -> {
                                tempTxt.text = ContextCompat.getString(requireContext(), R.string.celcius_c)
                                celciusRB.isChecked = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun localizationSetup(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.local.collect{
                    binding.apply {
                        arabicRB.isChecked = !it
                        englishRB.isChecked = it
                        languageTxt.text = if (it) "English" else "Arabic"
                    }
                }
            }
        }
    }

    private fun windSpeedSetup(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.speed.collect{
                    binding.apply {
                        meterPerSecondRB.isChecked = it
                        languageTxt.text = if (it) ContextCompat.getString(requireContext(), R.string.meter_sec) else ContextCompat.getString(requireContext(), R.string.miles_hour)
                    }
                }
            }
        }
    }
}