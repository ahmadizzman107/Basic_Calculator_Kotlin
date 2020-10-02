package beni.dev.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.buttons.*
import kotlinx.android.synthetic.main.input_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    var hasCalculate:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize & load Mobile Ads
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        //Number Listeners
        num_0.setOnClickListener { appendOnClick(true,"0") }
        num_1.setOnClickListener { appendOnClick(true,"1") }
        num_2.setOnClickListener { appendOnClick(true,"2") }
        num_3.setOnClickListener { appendOnClick(true,"3") }
        num_4.setOnClickListener { appendOnClick(true,"4") }
        num_5.setOnClickListener { appendOnClick(true,"5") }
        num_6.setOnClickListener { appendOnClick(true,"6") }
        num_7.setOnClickListener { appendOnClick(true,"7") }
        num_8.setOnClickListener { appendOnClick(true,"8") }
        num_9.setOnClickListener { appendOnClick(true,"9") }

        //Operator Listeners
        add_butt.setOnClickListener { appendOnClick(false,"+") }
        min_butt.setOnClickListener { appendOnClick(false,"-") }
        multi_butt.setOnClickListener { appendOnClick(false,"*") }
        stroke_butt.setOnClickListener { appendOnClick(false,"/") }

        //Symbol Listeners
        left_bracket_butt.setOnClickListener { appendOnClick(false,"(") }
        right_bracket_butt.setOnClickListener { appendOnClick(false,")") }
        per_butt.setOnClickListener { appendOnClick(false,".") }

        //Process Listeners
        equal_butt.setOnClickListener { equal() }
        clear_butt.setOnClickListener {
            if(hasCalculate) {
                clear()
                hasCalculate = false
            }
            else {
                if (in_cal.length() != 0)
                    in_cal.text = in_cal.text.substring(0,in_cal.length()-1)
            }
        }
        clear_butt.setOnLongClickListener {
            clear()
            true
        }
    }

    private fun appendOnClick(clear:Boolean,string:String){
        if(hasCalculate){
            if(clear) {
                clear()
                in_cal.append(string)
            }
            else {
                in_cal.text = ""
                in_cal.append(out_cal.text)
                in_cal.append(string)
            }
            hasCalculate = false
        }else
             in_cal.append(string)
    }

    private fun clear(){
        in_cal.text = ""
        out_cal.text = ""
    }

    private fun equal(){
        try{
            val input = ExpressionBuilder(in_cal.text.toString()).build()
            val output = input.evaluate()
            val longOutput = output.toLong()
            if(output == longOutput.toDouble())
                out_cal.text = longOutput.toString()
            else
                out_cal.text = output.toString()
            hasCalculate = true
        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
    }
}