package br.com.uware.jogodavelha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var fimJogo: Boolean = false
    var jogador: Int = 1
    private var btnList = ArrayList<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnList = arrayListOf<Button>(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
        btnRestart.visibility = View.GONE
        for(x in 0..btnList.size-1) {
            btnList[x].setOnClickListener {
                joga(it as Button)
            }
        }
        btnRestart.setOnClickListener {
            restart()
        }
    }
    private fun joga(btn: Button){
        marca(btn)
        if(jogador<9 && !checkGame()) jogaComp()
    }
    private fun marca(btn: Button){
        if(jogador % 2 != 0) {
            btn.text = "X"
        }
        else{
            btn.text = "O"
        }
        btn.isClickable = false
        checkGame()
        jogador++
    }
    private fun jogaComp(){
        if(checkPos() != -1){
            Log.d("Posicao: ",checkPos().toString())
            marca(btnList[checkPos()])
        }
        else {
            var bol: Boolean = true
            do {
                var rand = Random.nextInt(9)
                if (btnList[rand].text == "") {
                    marca(btnList[rand])
                    bol = false
                } else {
                    bol = true
                }
            } while (bol)
        }
    }
    private fun checkPos(): Int{
        for(x in 0..2){
            if(btnList[x].text != "" || btnList[x+3].text != "" || btnList[x+6].text != "") {
                if (btnList[x].text == btnList[x + 3].text && btnList[x + 6].text == "") {
                    return x + 6
                }
                if (btnList[x + 6].text == btnList[x + 3].text && btnList[x].text == "") {
                    return x
                }
                if (btnList[x + 6].text == btnList[x].text && btnList[x + 3].text == "") {
                    return x + 3
                }
            }
        }
        var numb = listOf<Int>(0,3,6)
        for(x in numb){
            if(btnList[x].text != "" || btnList[x+2].text != "" || btnList[x+2].text != "") {
                if (btnList[x].text == btnList[x + 1].text && btnList[x + 2].text == "") {
                    return x + 2
                }
                if (btnList[x + 2].text == btnList[x + 1].text && btnList[x].text == "") {
                    return x
                }
                if (btnList[x].text == btnList[x + 2].text && btnList[x + 1].text == "") {
                    return x + 1
                }
            }
        }
        numb = listOf(0,4,8)
        if(checkDiag(numb) != -1) return checkDiag(numb)
        numb = listOf(2,4,6)
        if(checkDiag(numb) != -1) return checkDiag(numb)
        return -1
    }
    private fun checkDiag(numb: List<Int>): Int {
        if(btnList[numb[0]].text != "" && btnList[numb[0]].text == btnList[numb[1]].text && btnList[numb[2]].text == "") return numb[2]
        else if(btnList[numb[1]].text != "" && btnList[numb[1]].text == btnList[numb[2]].text && btnList[numb[0]].text == "") return numb[0]
        else if(btnList[numb[0]].text != "" && btnList[numb[0]].text == btnList[numb[2]].text && btnList[numb[1]].text == "") return numb[1]
        else return -1
    }
    private fun restart(){
        for(x in 0..btnList.size-1){
            btnList[x].text = ""
            btnList[x].isClickable = true
        }
        tvResult.text = ""
        jogador = 1
        fimJogo = false
        btnRestart.visibility = View.GONE
    }
    private fun checkGame(): Boolean{
        val numb = listOf<Int>(0,3,6)
        for(x in numb){
            if (btnList[x].text != "" && btnList[x].text == btnList[x+1].text && btnList[x+1].text == btnList[x + 2].text) {
                ganhador()
                return true
            }
        }
        for(x in 0..2){
            if(btnList[x].text != "" && btnList[x].text == btnList[x+3].text && btnList[x+3].text == btnList[x+6].text){
                ganhador()
                return true
            }
        }
        if ((btnList[0].text != "" && btnList[0].text == btnList[4].text && btnList[4].text == btnList[8].text)
            || (btnList[2].text != "" && btnList[2].text == btnList[4].text && btnList[4].text == btnList[6].text)) {
            ganhador()
            return true
        }
        else if(jogador == 9){
            tvResult.text = "Empate"
            btnRestart.visibility = View.VISIBLE
            return true
        }
        return false
    }
    private fun ganhador(){
        if(!fimJogo) {
            if (jogador % 2 != 0) {
                tvResult.text = "Ganhador: X"
            } else {
                tvResult.text = "Ganhador: O"
            }
            endGame()
            btnRestart.visibility = View.VISIBLE
            fimJogo = true
        }
    }
    private fun endGame(){
        for(x in 0..btnList.size-1){
            btnList[x].isClickable = false
        }
    }
}
