package com.example.mvipattern.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvipattern.databinding.ActivityMainBinding
import com.example.mvipattern.intents.MainIntents
import com.example.mvipattern.viewmodel.MainViewModel
import com.example.mvipattern.viewstates.MainViewState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val numberViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        render()
    }

    private fun initUI() {
        binding.addNumber.setOnClickListener {
            // call function that send the intent
            lifecycleScope.launch {
                numberViewModel.intentChannel.send(MainIntents.AddNumber)
            }
        }

        binding.addNumbers.setOnClickListener {
            // call function that send the intent
            lifecycleScope.launch {
                numberViewModel.intentChannel.send(
                    MainIntents.AddNumbers(
                        binding.numberEditText.text.toString().toInt()
                    )
                )
            }
        }

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, APIActivity::class.java))
            finish()
        }
    }

    //function that render ViewState
    private fun render() {
        lifecycleScope.launchWhenStarted {
            numberViewModel.viewState.collect {
                when (it) {
                    is MainViewState.Idle -> {
                        binding.numberTextView.text = "Idle"
                        Log.i("BViewState", "render: Idle----> $it")
                    }

                    is MainViewState.Number -> {
                        binding.numberTextView.text = it.number.toString()
                        Log.i("BViewState", "render: Number----> $it")
                    }
/*                    is MainViewState.Numbers -> {
                        binding.numberTextView.text = (it.firstNumber + it.secondNumber).toString()
                        Log.i("BViewState", "render: Numbers----> $it")
                    }*/
                    is MainViewState.Error -> {
                        binding.numberTextView.error = it.error
                        binding.numberTextView.text = Double.POSITIVE_INFINITY.toString()
                        Log.i("BViewState", "render: Error----> $it")
                    }
                }
            }
        }
    }
}