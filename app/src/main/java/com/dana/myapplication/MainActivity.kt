package com.dana.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dana.myapplication.databinding.ActivityMainBinding
import com.dana.myapplication.network.RestRepo
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity(), OnClickListner {
    var binding: ActivityMainBinding? = null
    var viewModel: MainActivityViewModel? = null

    private val DATE_PATTERN =
        "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$"



    val isValidPan:Boolean=true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding!!.onclick=this


        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                validateValues()
            }

            override fun afterTextChanged(editable: Editable) {}
        }


        binding!!.etBday.addTextChangedListener(textWatcher)
        binding!!.etBmonth.addTextChangedListener(textWatcher)
        binding!!.etByear.addTextChangedListener(textWatcher)
        binding!!.etPanNumber.addTextChangedListener(textWatcher)

        validateValues()
    }

    private fun validateValues() {
        val b: Button = binding?.btNext as Button

        b.isEnabled=false


        val s1: String = binding!!.etBday.text.toString()
        val s2: String = binding!!.etBmonth.text.toString()
        val s3: String = binding!!.etByear.text.toString()

        val s4: String = binding!!.etPanNumber.text.toString()

        val status:Boolean = !(s1 == "" || s2 == "" || s3=="")

        if (status)
        {

             val   matcher : Matcher= Pattern.compile(DATE_PATTERN).matcher("$s3-$s2-$s1");


             if (matcher.matches()) {

                    if(validateDate(s1,s2,s3.toInt()))
                    {

                        if (s4 != "")
                        {
                            b.isEnabled=true
                        }
                    }

                    else{

                       // Toast.makeText(getApplicationContext(), "Invalid Birthday!  $s1-$s2-$s3", Toast.LENGTH_SHORT).show();

                    }
             }

             else{

               //  Toast.makeText(getApplicationContext(), "Invalid Birthday! $s1-$s2-$s3", Toast.LENGTH_SHORT).show();

             }



        }





    }


    fun validateDate(day: String?,month:String,year:Int): Boolean {

                if (day == "31" &&
                    (month == "4" || month == "6" ||
                            month == "9" || month == "11" ||
                            month == "04" || month == "06" || month == "09")) {
                   return false
                }

                else if (month == "2" || month == "02") {

                    if (year % 4 == 0) {
                       return !(day == "30" || day == "31")
                    } else {
                       return !(day == "29" || day == "30" || day == "31")
                    }
                }

       return true
    }


    override fun onClick(view: View?) {

        if (view!!.id == R.id.bt_next) {
            if (Validate()) {
                validatePan()
            }
        }

       else if (view.id == R.id.tv_noPan) {

           finish()
        }

    }


    // pan validation always fails because it is dummy api
    //in MainActivity isValidPan is a reference variable for check what if pan is valid or else not valid
    private fun validatePan() {
        viewModel!!.validate(object : RestRepo.IRestRepo {
            override fun success(status: Boolean) {
                if (status) {
                    goToNext()
                }else
                {
                  disableButton()
                }
            }

          override  fun fail() {
              if (isValidPan) {
                 goToNext()
              }else
              {
                  disableButton()
              }



          }
        }, binding!!.etPanNumber.text.toString())
    }

    private fun disableButton() {
        toast("invalid pan number")
        binding!!.btNext.isEnabled=false

    }

    private fun goToNext() {
        toast("details Submitted successfully")
        startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        finish()
    }

    private fun Validate(): Boolean {
        if (binding!!.etPanNumber.text.length < 0) {
            toast("please enter pan number")
            return false
        }
        if (binding!!.etBday.text.length< 0) {
            toast("please enter BirthDay")
            return false
        }
        if (binding!!.etBmonth.text.length < 0) {
            toast("please enter BirthMonth")
            return false
        }
        if (binding!!.etByear.text.length < 0) {
            toast("please enter BirthYear")
            return false
        }

        return true
    }

    private fun toast(info: String) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
    }




}