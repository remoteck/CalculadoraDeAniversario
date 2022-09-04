package br.remoteck.calculadoradeaniversario

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDateSelected: TextView? = null
    private var tvDateMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDate: Button = findViewById(R.id.btn_date)
        tvDateSelected = findViewById(R.id.tv_date_selected)
        tvDateMinutes = findViewById(R.id.tv_date_minutes)

        btnDate.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //Mês começa em 0 -> Exemplo: Janeiro = 0
        val dpd = DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDay ->

            val selectedDate = "$selectedDay/${1 + selectedMonth}/$selectedYear"

            tvDateSelected?.text = selectedDate

            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = simpleDateFormat.parse(selectedDate)
            theDate?.let {
                val selectedDateInMinutes = theDate.time / 6000
                val currentDate =
                    simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))

                val currentDateInMinutes = currentDate.time / 6000

                currentDate?.let {
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                    tvDateMinutes?.text = differenceInMinutes.toString()
                }
            }
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 //24h em milisegundos
        dpd.show()

    }
}