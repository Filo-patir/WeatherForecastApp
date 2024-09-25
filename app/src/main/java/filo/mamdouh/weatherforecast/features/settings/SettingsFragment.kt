package filo.mamdouh.weatherforecast.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import filo.mamdouh.weatherforecast.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var notiFlag: Boolean = false

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

    }

    private fun setUpViews() {
        setUpLocationAnimation()
        setUpWinSpeedAnimation()
        setUpTempAnimation()
        setUpLangAnimation()
        binding.appNotificationBtn.apply {
            setOnClickListener {
                    animate().setStartDelay(500).translationX(50f).alpha(0f)
                        .withStartAction {
                            binding.appNotificationBtn2.animate().setStartDelay(300).translationX(0f).alpha(1f).setDuration(700)
                                .withStartAction { binding.appNotificationBtn2.visibility = View.VISIBLE }
                        }.setDuration(700).withEndAction {
                            visibility = View.GONE
                        }.start()
            }
        }
        binding.appNotificationBtn2.apply {
            setOnClickListener {
                animate().setStartDelay(500).translationX(-50f).alpha(0f)
                    .withStartAction {
                        binding.appNotificationBtn.animate().setStartDelay(300).translationX(0f).alpha(1f).setDuration(700)
                            .withStartAction { binding.appNotificationBtn.visibility = View.VISIBLE }
                    }.setDuration(700).withEndAction {
                        visibility = View.GONE
                    }.start()
            }
        }
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
}