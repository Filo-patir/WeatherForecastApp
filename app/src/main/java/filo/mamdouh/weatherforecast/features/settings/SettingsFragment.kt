package filo.mamdouh.weatherforecast.features.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.local.collect{
                    binding.apply {
                        arabicRB.isChecked = !it
                        englishRB.isChecked = it
                    }
                }
            }
        }
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
}