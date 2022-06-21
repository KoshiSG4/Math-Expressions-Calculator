package com.example.android.cw1

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
//import java.util.*
import kotlin.random.Random.Default.nextInt


class GameScreen : AppCompatActivity() {

    private var noOfCorrectAns = 0
    private var noOfWrongAns = 0
    private var totalTime = 30000L
    private var timer: CountDownTimer = object : CountDownTimer(30000, 1000) {
        override fun onTick(p0: Long) {}
        override fun onFinish() {}
    }
    private var timePassed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        //Display game window
        var result = displayGame()
        timer = newTimer(totalTime)     //initialize the countdown timer
        timer.start()                   //start the countdown timer

        var displayResultFinal = findViewById<TextView>(R.id.result)

        //create greater than comparator button
        val greaterBtn = findViewById<Button>(R.id.greater_btn)
        greaterBtn.setOnClickListener {
            if (result == "Greater") {
                noOfCorrectAns++
                increaseTimeAccordingToCorrectAnswers()
                displayResultFinal.setText("Correct").toString()
                displayResultFinal.setTextColor(Color.GREEN)
            } else {
                noOfWrongAns++
                displayResultFinal.setText("Wrong").toString()
                displayResultFinal.setTextColor(Color.RED)
            }
            result =
                displayGame()      //invoking game display to change the expressions every time a button is pressed
        }

        //create eaqual comparator button
        val equalBtn = findViewById<Button>(R.id.equal_btn)
        equalBtn.setOnClickListener {
            if (result == "Equal") {
                noOfCorrectAns++
                increaseTimeAccordingToCorrectAnswers()
                displayResultFinal.setText("Correct").toString()
                displayResultFinal.setTextColor(Color.GREEN)
            } else {
                noOfWrongAns++
                displayResultFinal.setText("Wrong").toString()
                displayResultFinal.setTextColor(Color.RED)
            }
            result =
                displayGame()      //invoking game display to change the expressions every time a button is pressed
        }

        //create less than comparator button
        val lessBtn = findViewById<Button>(R.id.less_btn)
        lessBtn.setOnClickListener {
            if (result == "Less") {
                noOfCorrectAns++
                increaseTimeAccordingToCorrectAnswers()
                displayResultFinal.setText("Correct").toString()
                displayResultFinal.setTextColor(Color.GREEN)
            } else {
                noOfWrongAns++
                displayResultFinal.setText("Wrong").toString()
                displayResultFinal.setTextColor(Color.RED)
            }
            result =
                displayGame()       //invoking game display to change the expressions every time a button is pressed
        }

    }


    private var exp = ""

    //generate random math expressions
    private fun generateRandMathExpression(): String {
        exp = ""
        var question: String = ""
        var noOfTerms = Random.nextInt(2,5)

        when (noOfTerms) {
            2 -> { question = randNumber().plus(randOperator().displayOperatorValue.plus(randNumber())) }

            3 -> { question = "(".plus(randNumber().plus(randOperator().displayOperatorValue.plus(randNumber().plus(")".plus(randOperator().displayOperatorValue.plus(randNumber())))))) }

            4 -> { question = "((".plus(randNumber().plus(randOperator().displayOperatorValue.plus(randNumber().plus(")".plus(randOperator().displayOperatorValue.plus(randNumber().plus(")".plus(randOperator().displayOperatorValue.plus(randNumber()))))))))) }
        }
        exp = question
        return question
    }

    //generate random number
    private fun randNumber(): String {
        return nextInt(1, 20).toString()
    }

    //generate random operator
    private fun randOperator(): Operator {
        return Operator.values()[nextInt(Operator.values().size)]
    }

    //calculate sub math expressions
    private fun calculateMathExp(num1:Int,num:String,symbol:Char): Int {
         var subExpResult = 0
        when(symbol){
            '+' -> {subExpResult=num1+num.toInt() }
            '-' -> {subExpResult=num1-num.toInt() }
            '*' -> {subExpResult=num1*num.toInt() }
            '/' -> { if (num1%num.toInt() != 0) {
                    mathExpParser()
                    }else{
                        subExpResult=num1/num.toInt()
                    } }
        }
        if (subExpResult>100){
            mathExpParser()
        }

        return subExpResult
    }

    //Parsing the math string type expression to char sequences to pass to the calculations
    private fun mathExpParser(): Pair<String,Int> {
        exp = generateRandMathExpression()
        var num: String = ""
        var num1 = 0
        var symbol:Char = ' '
        var count = 0
        var result=0
        for (i in exp){
            if (i in '0'..'9'){
                num+=i.toString()
            }else{
                if ((i != '(') && (i != ')')){
                    count++
                    when(count){        //checks the number of subexpressions
                        1->{num1+= num.toInt()}
                        2->{num1= calculateMathExp(num1, num, symbol)
                            if (((symbol=='/') && (num1%num.toInt() !=0))||(num1>100)){
                                break
                            }}
                        3->{num1=calculateMathExp(num1, num, symbol)
                            if (((symbol=='/') && (num1%num.toInt() !=0))||(num1>100)){
                                break
                            }}
                    }
                    num = ""
                }
                when(i){        //checks the symbol
                    '+' ->{ symbol=i}
                    '-' ->{ symbol=i}
                    '*' ->{ symbol=i}
                    '/' ->{ symbol=i}
                }
            }
        }
        result = calculateMathExp(num1, num, symbol)
        return Pair(exp,result)
    }

    //display game screen
    private fun displayGame() :String {

        var (exp1,result1) = mathExpParser()
        var (exp2,result2) = mathExpParser()

        val mathExpression1TextView = findViewById<TextView>(R.id.expression_1)
        mathExpression1TextView.text = exp1

        val mathExpression2TextView = findViewById<TextView>(R.id.expression_2)
        mathExpression2TextView.text = exp2

        var finalResult = ""

        //checks the result
        when {
            result1>result2 -> { finalResult="Greater" }
            result1==result2 -> { finalResult="Equal" }
            result1<result2 -> { finalResult="Less" }
        }

        return finalResult
    }

    //create new countdown timer(reference: https://developer.android.com/reference/kotlin/android/os/CountDownTimer)
    private fun newTimer(millisInFuture:Long): CountDownTimer{
        return object : CountDownTimer(millisInFuture, 1000) {
            val timerTextView = findViewById<TextView>(R.id.timer)
            override fun onTick(millisUntilFinished: Long) {
                timePassed += 1000
                timerTextView.setText("Seconds Remaining: " + millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                    displayFinalResultActivity()        //display result summary when timer finishes
            }
        }
    }

    //increase time by 10s for every correct five answers
    private fun increaseTimeAccordingToCorrectAnswers(){
        if (noOfCorrectAns%5 == 0){
            timer.cancel()
            totalTime += 10000
            timer = newTimer(totalTime-timePassed)
            timer.start()
        }
    }

    //create result summary screen
    private fun displayFinalResultActivity(){
        val array = arrayListOf<Int>(noOfCorrectAns,noOfWrongAns)
        val resultWindowIntent = Intent(this,ResultWindow::class.java)
        resultWindowIntent.putIntegerArrayListExtra("answers",array)    //passing the final results to the result summary screen
        startActivity(resultWindowIntent)
        this.finish()
    }

}

//initializing the operators
enum class Operator(var displayOperatorValue: String){
    Plus("+"), Minus("-"),Multiplier("*"),Divider("/");
}

