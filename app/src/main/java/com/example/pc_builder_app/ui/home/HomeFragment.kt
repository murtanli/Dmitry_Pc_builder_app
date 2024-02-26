package com.example.pc_builder_app.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pc_builder_app.MainActivity
import com.example.pc_builder_app.R
import com.example.pc_builder_app.api.api_resource
import com.example.pc_builder_app.api.pc_inf
import com.example.pc_builder_app.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as? MainActivity)?.act_bar()


        val container_blocks = binding.containerBlocks

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = api_resource()
                val result = data.get_all_pc_build()
                if (result.isNotEmpty()) {
                    //вызов функции отрисовки блоков

                    val title = TextView(requireContext())
                    title.text = "Сборки компьютеров"
                    title.setPadding(100,100,100,100)
                    title.gravity = Gravity.CENTER
                    title.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    title.textSize = 30F

                    container_blocks.addView(title)

                    for (pc_conf in result) {
                        val block = create_block_ser(
                            pc_conf.id,
                            pc_conf.processor,
                            pc_conf.graphics_card,
                            pc_conf.ram,
                            pc_conf.memory,
                            pc_conf.motherboard,
                            pc_conf.power_unit,
                            pc_conf.frame,
                            pc_conf.category,
                            pc_conf.price)
                        container_blocks.addView(block)
                        container_blocks.gravity = Gravity.CENTER
                        Log.e("666", pc_conf.toString())
                    }

                } else {
                    // Обработка случая, когда список пуст
                    Log.e("BusActivity", "Response failed - result is empty")

                    //val error = createBusEpty()
                    //BusesContainer.addView(error)
                }
            } catch (e: Exception) {
                // Ловим и обрабатываем исключения, например, связанные с сетевыми ошибками
                Log.e("BusActivity", "Error during response", e)
                e.printStackTrace()
            }
        }

        return root
    }

    @SuppressLint("ResourceAsColor")
    private fun create_block_ser(id: Int, processor: pc_inf, graphics_card: pc_inf, ram: pc_inf, memory: pc_inf, motherboard: pc_inf, power_unit: pc_inf, frame: pc_inf, category: String, price:Int): LinearLayout {
        //общий блок
        val block = LinearLayout(requireContext())

        val blockParams = LinearLayout.LayoutParams(
            1000,
            1700
        )
        blockParams.setMargins(0, 0, 10, 0)
        blockParams.bottomMargin = 100
        block.layoutParams = blockParams
        //block.gravity = Gravity.CENTER
        block.orientation = LinearLayout.VERTICAL
        val backgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_background)
        block.background = backgroundDrawable

        //category
        val category_text = TextView(requireContext())
        category_text.text = "$category компьютер"
        category_text.setTextAppearance(R.style.PageTitle)

        category_text.setPadding(30,50,30,0)
        category_text.gravity = Gravity.LEFT

        // Создаем параметры макета и устанавливаем их для текстового поля
        val layoutParams_title = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        category_text.layoutParams = layoutParams_title

        //Заголовок
        val harek_title = TextView(requireContext())
        harek_title.text = "$category компьютер"
        harek_title.setTextAppearance(R.style.PageTitle)
        harek_title.gravity = Gravity.CENTER
        harek_title.setPadding(30,50,30,20)
        harek_title.gravity = Gravity.LEFT

        //блок харектеристик
        val block_harek = LinearLayout(requireContext())
        val block_harek_params = LinearLayout.LayoutParams(
            900,
            1000
        )
        block_harek_params.setMargins(50, 0, 0, 0)
        block_harek_params.bottomMargin = 10
        block_harek.layoutParams = block_harek_params
        //block.gravity = Gravity.CENTER
        block_harek.orientation = LinearLayout.VERTICAL

        //процессор
        val processor_text = TextView(requireContext())
        processor_text.text = Html.fromHtml("&#8226; <b>Процессор:</b> ${processor.name}", Html.FROM_HTML_MODE_COMPACT)
        processor_text.setTextAppearance(R.style.Harek_text_style)

        processor_text.setPadding(30,20,30,0)
        processor_text.gravity = Gravity.LEFT

        //видеокарта
        val graphics_card_text = TextView(requireContext())
        graphics_card_text.text = Html.fromHtml("&#8226; <b>Видеокарта:</b> ${graphics_card.name}", Html.FROM_HTML_MODE_COMPACT)
        graphics_card_text.setTextAppearance(R.style.Harek_text_style)

        graphics_card_text.setPadding(30,20,30,0)
        graphics_card_text.gravity = Gravity.LEFT

        //оперативная память
        val ram_text = TextView(requireContext())
        ram_text.text = Html.fromHtml("&#8226; <b>Оперативная память:</b> ${ram.name}", Html.FROM_HTML_MODE_COMPACT)
        ram_text.setTextAppearance(R.style.Harek_text_style)

        ram_text.setPadding(30,20,30,0)
        ram_text.gravity = Gravity.LEFT

        //память
        val memory_text = TextView(requireContext())
        memory_text.text = Html.fromHtml("&#8226; <b>Память:</b> ${memory.name}", Html.FROM_HTML_MODE_COMPACT)
        memory_text.setTextAppearance(R.style.Harek_text_style)

        memory_text.setPadding(30,20,30,0)
        memory_text.gravity = Gravity.LEFT

        //материнская плата
        val motherboard_text = TextView(requireContext())
        motherboard_text.text = Html.fromHtml("&#8226; <b>Материнская плата:</b> ${motherboard.name}", Html.FROM_HTML_MODE_COMPACT)
        motherboard_text.setTextAppearance(R.style.Harek_text_style)

        motherboard_text.setPadding(30,20,30,0)
        motherboard_text.gravity = Gravity.LEFT

        //блок питания
        val power_unit_text = TextView(requireContext())
        power_unit_text.text = Html.fromHtml("&#8226; <b>Блок питания:</b> ${power_unit.name}", Html.FROM_HTML_MODE_COMPACT)
        power_unit_text.setTextAppearance(R.style.Harek_text_style)

        power_unit_text.setPadding(30,20,30,0)
        power_unit_text.gravity = Gravity.LEFT

        //корпус
        val frame_text = TextView(requireContext())
        frame_text.text = Html.fromHtml("&#8226; <b>Корпус:</b> ${frame.name}", Html.FROM_HTML_MODE_COMPACT)
        frame_text.setTextAppearance(R.style.Harek_text_style)

        frame_text.setPadding(30,20,30,0)
        frame_text.gravity = Gravity.LEFT

        //Цена
        val price_text = TextView(requireContext())
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        formatter.minimumFractionDigits = 0 // Минимальное количество десятичных знаков
        formatter.maximumFractionDigits = 3 // Максимальное количество десятичных знаков

        val formattedPrice = formatter.format(price)
        price_text.text = "Цена - $formattedPrice руб"
        price_text.setTextAppearance(R.style.TextInf)
        price_text.gravity = Gravity.CENTER
        price_text.setPadding(30,0,30,20)
        price_text.gravity = Gravity.LEFT

        //блок кнопок
        val block_buttons = LinearLayout(requireContext())
        val block_buttons_params = LinearLayout.LayoutParams(
            1000,
            500
        )
        block_buttons_params.setMargins(50, 0, 0, 0)
        block_buttons_params.bottomMargin = 10
        block_buttons.layoutParams = block_buttons_params
        //block.gravity = Gravity.CENTER
        block_buttons.orientation = LinearLayout.VERTICAL

        // Кнопка добавить
        val button_add = Button(requireContext())
        button_add.text = "Добавить"
        button_add.setPadding(10, 20, 10, 10)
        val buttonAddParams = LinearLayout.LayoutParams(
            850,
            120
        )
        buttonAddParams.setMargins(20, 20, 20, 50)
        button_add.layoutParams = buttonAddParams
        val bgBut = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_background_buttons)
        button_add.background = bgBut

        // Кнопка изменить сборку
        val button_change = Button(requireContext())
        button_change.text = "Изменить конфигурацию"
        button_change.setPadding(10, 20, 10, 10)
        val buttonChangeParams = LinearLayout.LayoutParams(
            850,
            120
        )
        buttonChangeParams.setMargins(20, 20, 20, 20)
        button_change.layoutParams = buttonChangeParams
        val bgBut2 = ContextCompat.getDrawable(requireContext(), R.drawable.rounded_background_buttons)
        button_change.background = bgBut2

        button_add.setOnClickListener {
            val SharedPreference = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val user_id = SharedPreference.getString("user_id", "")?.toIntOrNull()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // Вызываем функцию logIn для выполнения запроса
                    val data = api_resource()
                    val result = data.save_pc_build_cart(user_id, id.toInt(), price.toInt())

                    if (result != null) {
                        val toast = Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
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

        button_change.setOnClickListener {
            val intent = Intent(requireContext(), Change_pc_build::class.java)
            intent.apply {
                putExtra("processor_id", processor.id)
                putExtra("graphics_card_id", graphics_card.id)
                putExtra("ram_id", ram.id)
                putExtra("memory_id", memory.id)
                putExtra("motherboard_id", motherboard.id)
                putExtra("power_unit_id", power_unit.id)
                putExtra("frame_id", frame.id)

                putExtra("processor_name", processor.name)
                putExtra("graphics_name", graphics_card.name)
                putExtra("ram_name", ram.name)
                putExtra("memory_name", memory.name)
                putExtra("motherboard_name", motherboard.name)
                putExtra("power_unit_name", power_unit.name)
                putExtra("frame_name", frame.name)

                putExtra("price", price)
            }
            startActivity(intent)
        }


        //добавление текста и блоков в основной блок
        block.addView(harek_title)

        block_harek.addView(processor_text)
        block_harek.addView(graphics_card_text)
        block_harek.addView(ram_text)
        block_harek.addView(memory_text)
        block_harek.addView(motherboard_text)
        block_harek.addView(power_unit_text)
        block_harek.addView(frame_text)

        block_buttons.addView(button_add)
        block_buttons.addView(button_change)

        block.addView(block_harek)
        block.addView(price_text)
        block.addView(block_buttons)
        return block
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}