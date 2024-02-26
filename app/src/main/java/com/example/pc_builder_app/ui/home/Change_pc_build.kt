package com.example.pc_builder_app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pc_builder_app.MainActivity
import com.example.pc_builder_app.R
import com.example.pc_builder_app.api.api_resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Change_pc_build : AppCompatActivity() {

    private val processorMap = mutableMapOf<Int, Pair<String, Float>>()
    private val graphicsCardMap = mutableMapOf<Int, Pair<String, Float>>()
    private val ramMap = mutableMapOf<Int, Pair<String, Float>>()
    private val motherboardMap = mutableMapOf<Int, Pair<String, Float>>()
    private val powerUnitMap = mutableMapOf<Int, Pair<String, Float>>()
    private val frameMap = mutableMapOf<Int, Pair<String, Float>>()
    private val memoryMap = mutableMapOf<Int, Pair<String, Float>>()

    private var sel_frame: Int? = null
    private var sel_powrunit: Int? = null
    private var sel_motherboard: Int? = null
    private var sel_ram: Int? = null
    private var sel_graphicscart: Int? = null
    private var sel_processor: Int? = null
    private var sel_memory: Int? = null

    private var price_processor: Int = 0
    private var price_frame: Int = 0
    private var price_powrunit: Int = 0
    private var price_motherboard: Int = 0
    private var price_ram: Int = 0
    private var price_memory: Int = 0
    private var price_graphicscart: Int = 0
    private var full_price: Int = 0

    fun plus_comp(): String {
        full_price = price_processor + price_ram + price_frame + price_graphicscart + price_memory + price_motherboard + price_powrunit
        Log.e("777", full_price.toString())
        return "Цена - ${full_price.toString()}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pc_build)

        supportActionBar?.hide()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val processor_name = intent.getStringExtra("processor_name")
        val graphics_name = intent.getStringExtra("graphics_name")
        val ram_name = intent.getStringExtra("ram_name")
        val memory_name = intent.getStringExtra("memory_name")
        val motherboard_name = intent.getStringExtra("motherboard_name")
        val power_unit_name = intent.getStringExtra("power_unit_name")
        val frame_name = intent.getStringExtra("frame_name")

        val spinnerProcessor = findViewById<Spinner>(R.id.spinner_processor)
        val spinnerGraphicsCard = findViewById<Spinner>(R.id.spinner_graphics_card)
        val spinnerRam = findViewById<Spinner>(R.id.spinner_ram)
        val spinnerMemory = findViewById<Spinner>(R.id.spinner_memory)
        val spinnerMotherboard = findViewById<Spinner>(R.id.spinner_motherboard)
        val spinnerPowerUnit = findViewById<Spinner>(R.id.spinner_power_unit)
        val spinnerFrame = findViewById<Spinner>(R.id.spinner_frame)
        val button_add = findViewById<Button>(R.id.button_add)
        val price_text = findViewById<TextView>(R.id.text_price)

        price_text.text = full_price.toString()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = api_resource()

                val processorList = data.get_all_comp_cat("Процессор")
                val graphicsCardList = data.get_all_comp_cat("Видеокарта")
                val ramList = data.get_all_comp_cat("Оперативная память")
                val memoryList = data.get_all_comp_cat("Память")
                val motherboardList = data.get_all_comp_cat("Материнская плата")
                val powerUnitList = data.get_all_comp_cat("Блок питания")
                val frameList = data.get_all_comp_cat("корпус")

                memoryList.forEach { component ->
                    memoryMap[component.id] = Pair(component.name, component.price)
                }

                processorList.forEach { component ->
                    processorMap[component.id] = Pair(component.name, component.price)
                }

                graphicsCardList.forEach { component ->
                    graphicsCardMap[component.id] = Pair(component.name, component.price)
                }

                ramList.forEach { component ->
                    ramMap[component.id] = Pair(component.name, component.price)
                }

                motherboardList.forEach { component ->
                    motherboardMap[component.id] = Pair(component.name, component.price)
                }

                powerUnitList.forEach { component ->
                    powerUnitMap[component.id] = Pair(component.name, component.price)
                }

                frameList.forEach { component ->
                    frameMap[component.id] = Pair(component.name, component.price)
                }

                val processorAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, processorMap.values.map { it.first })
                spinnerProcessor.adapter = processorAdapter
                val defaultProcessor = processor_name
                processorAdapter.add(defaultProcessor) // Добавляем текст по умолчанию в адаптер
                spinnerProcessor.setSelection(processorAdapter.count - 1)

                //val sel_spin_processor: String = spinnerProcessor.selectedItem.toString()
                //sel_processor = processorMap.entries.firstOrNull { it.value.first == sel_spin_processor }?.key

                val graphicsCardAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, graphicsCardList.map { it.name })
                spinnerGraphicsCard.adapter = graphicsCardAdapter
                val defaultGraphicsCart = graphics_name
                graphicsCardAdapter.add(defaultGraphicsCart) // Добавляем текст по умолчанию в адаптер
                spinnerGraphicsCard.setSelection(graphicsCardAdapter.count - 1)

                //val sel_spin_graphicscart: String = spinnerGraphicsCard.selectedItem.toString()
                //sel_graphicscart = graphicsCardMap.entries.firstOrNull { it.value.first == sel_spin_graphicscart }?.key

                val memoryAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, memoryList.map { it.name })
                spinnerMemory.adapter = memoryAdapter
                val defaultMemory = memory_name
                memoryAdapter.add(defaultMemory) // Добавляем текст по умолчанию в адаптер
                spinnerMemory.setSelection(memoryAdapter.count - 1)

                //val sel_spin_memory: String = spinnerMemory.selectedItem.toString()
                //sel_memory = memoryMap.entries.firstOrNull { it.value.first == sel_spin_memory }?.key


                val ramAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, ramList.map { it.name })
                spinnerRam.adapter = ramAdapter
                val defaultRam = ram_name
                ramAdapter.add(defaultRam) // Добавляем текст по умолчанию в адаптер
                spinnerRam.setSelection(ramAdapter.count - 1)

                //val sel_spin_ram: String = spinnerRam.selectedItem.toString()
                //sel_ram = ramMap.entries.firstOrNull { it.value.first == sel_spin_ram }?.key

                val motherboardAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, motherboardList.map { it.name })
                spinnerMotherboard.adapter = motherboardAdapter
                val defaultMotherboard = motherboard_name
                motherboardAdapter.add(defaultMotherboard) // Добавляем текст по умолчанию в адаптер
                spinnerMotherboard.setSelection(motherboardAdapter.count - 1)

                //val sel_spin_motherboard: String = spinnerMotherboard.selectedItem.toString()
                //sel_motherboard = motherboardMap.entries.firstOrNull { it.value.first == sel_spin_motherboard }?.key

                val powerUnitAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, powerUnitList.map { it.name })
                spinnerPowerUnit.adapter = powerUnitAdapter
                val defaultPowerUnit = power_unit_name
                powerUnitAdapter.add(defaultPowerUnit) // Добавляем текст по умолчанию в адаптер
                spinnerPowerUnit.setSelection(powerUnitAdapter.count - 1)

                //val sel_spin_powerunit: String = spinnerPowerUnit.selectedItem.toString()
                //sel_powrunit = powerUnitMap.entries.firstOrNull { it.value.first == sel_spin_powerunit }?.key

                val frameAdapter = CustomSpinnerAdapter(this@Change_pc_build, R.layout.spinner_item, frameList.map { it.name })
                spinnerFrame.adapter = frameAdapter
                val defaultFrame = frame_name
                frameAdapter.add(defaultFrame) // Добавляем текст по умолчанию в адаптер
                spinnerFrame.setSelection(frameAdapter.count - 1)

                //val sel_spin_frame: String = spinnerFrame.selectedItem.toString()
                //sel_frame = frameMap.entries.firstOrNull { it.value.first == sel_spin_frame }?.key
                progressBar.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



        spinnerProcessor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val sel_spin_processor: String = spinnerProcessor.selectedItem.toString()
                sel_processor = processorMap.entries.firstOrNull { it.value.first == sel_spin_processor }?.key
                price_processor = (sel_processor?.let { processorMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_processor.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }


        spinnerMemory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_memory: String = spinnerMemory.selectedItem.toString()
                sel_memory = memoryMap.entries.firstOrNull { it.value.first == sel_spin_memory }?.key
                price_memory = (sel_memory?.let { memoryMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_memory.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }

        spinnerGraphicsCard.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_graphicscart: String = spinnerGraphicsCard.selectedItem.toString()
                sel_graphicscart = graphicsCardMap.entries.firstOrNull { it.value.first == sel_spin_graphicscart }?.key
                price_graphicscart = (sel_graphicscart?.let { graphicsCardMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_graphicscart.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }

        spinnerFrame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_frame: String = spinnerFrame.selectedItem.toString()
                sel_frame = frameMap.entries.firstOrNull { it.value.first == sel_spin_frame }?.key
                price_frame = (sel_frame?.let { frameMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_frame.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }

        spinnerMotherboard.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_motherboard: String = spinnerMotherboard.selectedItem.toString()
                sel_motherboard = motherboardMap.entries.firstOrNull { it.value.first == sel_spin_motherboard }?.key
                price_motherboard = (sel_motherboard?.let { motherboardMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_motherboard.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }

        spinnerPowerUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_powerunit: String = spinnerPowerUnit.selectedItem.toString()
                sel_powrunit = powerUnitMap.entries.firstOrNull { it.value.first == sel_spin_powerunit }?.key
                price_powrunit = (sel_powrunit?.let { powerUnitMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_powrunit.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }

        spinnerRam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sel_spin_ram: String = spinnerRam.selectedItem.toString()
                sel_ram = ramMap.entries.firstOrNull { it.value.first == sel_spin_ram }?.key
                price_ram = (sel_ram?.let { ramMap[it]?.second }?.toInt() ?: plus_comp()) as Int
                price_text.text = plus_comp()
                Log.e("555", sel_ram.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия, которые нужно выполнить, если ничего не выбрано
            }
        }
        button_add.setOnClickListener {
            Log.e("666", "$sel_processor $sel_graphicscart $sel_ram $sel_frame $sel_powrunit $sel_memory $sel_motherboard")
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // Вызываем функцию logIn для выполнения запроса
                    val data = api_resource()
                    val result = data.save_pc_build(
                        sel_processor?.toInt(),
                        sel_graphicscart?.toInt(),
                        sel_ram?.toInt(),
                        sel_memory?.toInt(),
                        sel_motherboard?.toInt(),
                        sel_powrunit?.toInt(),
                        sel_frame?.toInt(),
                        full_price?.toInt()
                    )
                    if (result != null) {
                        val toast = Toast.makeText(this@Change_pc_build, result.message, Toast.LENGTH_LONG)
                        toast.show()
                    } else {
                        // Обработка случая, когда result равен null
                        Log.e("LoginActivity", "Login failed - result is null")
                    }
                } catch (e: Exception) {
                    // Ловим и обрабатываем исключения, например, связанные с сетевыми ошибками
                    Log.e("LoginActivity", "Error during login", e)
                    e.printStackTrace()
                }
            }
        }

    }

    class CustomSpinnerAdapter(
        context: Context,
        resource: Int,
        objects: List<String>
    ) : ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            (view as? TextView)?.setTextColor(ContextCompat.getColor(context, R.color.black))
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            if (view is TextView) {
                view.setTextColor(ContextCompat.getColor(context, R.color.white))
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    150
                )
                //view.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            }
            return view

        }
    }
}
