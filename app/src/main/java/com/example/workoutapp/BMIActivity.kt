package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMI)
        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBMI?.setNavigationOnClickListener {
            finish()
            onBackPressed()
        }

        binding?.btnCalculateBmi?.setOnClickListener{
            if(binding?.rbMetricUnits?.isChecked == true)
            {
                if(binding?.etMetricUnitWeight?.text.toString().isNotEmpty() &&
                    binding?.etMetricUnitHeight?.text.toString().isNotEmpty())
                {
                    val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                    val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                    val bmi: Float = weightValue / (heightValue*heightValue)

                    binding?.llDisplayBmiResult?.visibility = View.VISIBLE

                    if(bmi >= 30)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nYou are severely obese!\nPlease take care of your health!"
                    }
                    else if(bmi >= 25)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nThe BMI above indicates that you are overweight!\nYou need to practice more!"
                    }
                    else if(bmi >= 18.5)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nCongratulation!\nYou have a normal BMI!"
                    }
                    else
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nThe BMI above shows you are underweight!\nPlease take care of your health!"
                    }
                }
                else
                {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
            else if(binding?.rbUsUnits?.isChecked == true)
            {
                if(binding?.etUsUnitWeight?.text.toString().isNotEmpty() &&
                    binding?.etUsUnitFeet?.text.toString().isNotEmpty() &&
                    binding?.etUsUnitInch?.text.toString().isNotEmpty())
                {
                    val weightValue: Float = binding?.etUsUnitWeight?.text.toString().toFloat()
                    val feetValue: Float = binding?.etUsUnitFeet?.text.toString().toFloat()
                    val inchValue: Float = binding?.etUsUnitInch?.text.toString().toFloat()
                    val heightValue: Float = feetValue*12 + inchValue
                    val bmi: Float = (weightValue/(heightValue*heightValue))*703

                    binding?.llDisplayBmiResult?.visibility = View.VISIBLE

                    if(bmi >= 30)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nYou are severely obese!\nPlease take care of your health!"
                    }
                    else if(bmi >= 25)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nThe BMI above indicates that you are overweight!\nYou need to practice more!"
                    }
                    else if(bmi >= 18.5)
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nCongratulation!\nYou have a normal BMI!"
                    }
                    else
                    {
                        binding?.tvBmiDescription?.text = "$bmi\n\nThe BMI above shows you are underweight!\nPlease take care of your health!"
                    }
                }
                else
                {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding?.rbMetricUnits?.setOnClickListener{
            if(binding?.rbMetricUnits?.isChecked == true)
            {
                binding?.tilUsUnitWeight?.visibility = View.INVISIBLE
                binding?.tilUsUnitFeetInch?.visibility = View.INVISIBLE
                binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
                binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
            }
        }

        binding?.rbUsUnits?.setOnClickListener{
            if(binding?.rbUsUnits?.isChecked == true)
            {
                binding?.tilUsUnitWeight?.visibility = View.VISIBLE
                binding?.tilUsUnitFeetInch?.visibility = View.VISIBLE
                binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
                binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
            }
        }
    }
}