package com.example.pc_builder_app.ui.profile

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
import com.example.pc_builder_app.R
import com.example.pc_builder_app.api.api_resource
import com.example.pc_builder_app.api.pc_inf
import com.example.pc_builder_app.auth.Login
import com.example.pc_builder_app.databinding.FragmentProfileBinding
import com.example.pc_builder_app.ui.home.Change_pc_build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val container_blocks = binding.containerBlocks
        val user_name_text = binding.userNameText
        val button_exit = binding.buttonExit

        val sharedPreference = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val user_id = sharedPreference.getString("user_id", "")?.toIntOrNull()
        val user_name = sharedPreference.getString("user_name", "")
        user_name_text.text = "Логин - ${user_name}"


        button_exit.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove("user_id")
            editor.remove("user_name")
            editor.putString("login", "false")
            editor.apply()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = api_resource()
                val result = data.saved_user_pc(
                    user_id
                )

                if (1 == 1) {
                    //вызов функции отрисовки блоков

                    val title = TextView(requireContext())
                    title.text = "Сборки компьютеров"
                    title.setPadding(100,100,100,100)
                    title.gravity = Gravity.CENTER
                    title.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    title.textSize = 30F
                    Log.e("999", result.toString())
                    container_blocks.addView(title)

                    for (pc_conf in result.cart_items) {
                        val block = create_block_ser(
                            pc_conf.pc_build_id,
                            pc_conf.processor,
                            pc_conf.graphics_card,
                            pc_conf.ram,
                            pc_conf.memory,
                            pc_conf.motherboard,
                            pc_conf.power_unit,
                            pc_conf.frame,
                            pc_conf.category,
                            pc_conf.price.toInt())
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
    private fun create_block_ser(id: Int, processor: String, graphics_card: String, ram: String, memory: String, motherboard: String, power_unit: String, frame: String, category: String, price:Int): LinearLayout {
        //общий блок
        val block = LinearLayout(requireContext())

        val blockParams = LinearLayout.LayoutParams(
            1000,
            1400
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
        processor_text.text = Html.fromHtml("&#8226; <b>Процессор:</b> ${processor}", Html.FROM_HTML_MODE_COMPACT)
        processor_text.setTextAppearance(R.style.Harek_text_style)

        processor_text.setPadding(30,20,30,0)
        processor_text.gravity = Gravity.LEFT

        //видеокарта
        val graphics_card_text = TextView(requireContext())
        graphics_card_text.text = Html.fromHtml("&#8226; <b>Видеокарта:</b> ${graphics_card}", Html.FROM_HTML_MODE_COMPACT)
        graphics_card_text.setTextAppearance(R.style.Harek_text_style)

        graphics_card_text.setPadding(30,20,30,0)
        graphics_card_text.gravity = Gravity.LEFT

        //оперативная память
        val ram_text = TextView(requireContext())
        ram_text.text = Html.fromHtml("&#8226; <b>Оперативная память:</b> ${ram}", Html.FROM_HTML_MODE_COMPACT)
        ram_text.setTextAppearance(R.style.Harek_text_style)

        ram_text.setPadding(30,20,30,0)
        ram_text.gravity = Gravity.LEFT

        //память
        val memory_text = TextView(requireContext())
        memory_text.text = Html.fromHtml("&#8226; <b>Память:</b> ${memory}", Html.FROM_HTML_MODE_COMPACT)
        memory_text.setTextAppearance(R.style.Harek_text_style)

        memory_text.setPadding(30,20,30,0)
        memory_text.gravity = Gravity.LEFT

        //материнская плата
        val motherboard_text = TextView(requireContext())
        motherboard_text.text = Html.fromHtml("&#8226; <b>Материнская плата:</b> ${motherboard}", Html.FROM_HTML_MODE_COMPACT)
        motherboard_text.setTextAppearance(R.style.Harek_text_style)

        motherboard_text.setPadding(30,20,30,0)
        motherboard_text.gravity = Gravity.LEFT

        //блок питания
        val power_unit_text = TextView(requireContext())
        power_unit_text.text = Html.fromHtml("&#8226; <b>Блок питания:</b> ${power_unit}", Html.FROM_HTML_MODE_COMPACT)
        power_unit_text.setTextAppearance(R.style.Harek_text_style)

        power_unit_text.setPadding(30,20,30,0)
        power_unit_text.gravity = Gravity.LEFT

        //корпус
        val frame_text = TextView(requireContext())
        frame_text.text = Html.fromHtml("&#8226; <b>Корпус:</b> ${frame}", Html.FROM_HTML_MODE_COMPACT)
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




        //добавление текста и блоков в основной блок
        block.addView(harek_title)

        block_harek.addView(processor_text)
        block_harek.addView(graphics_card_text)
        block_harek.addView(ram_text)
        block_harek.addView(memory_text)
        block_harek.addView(motherboard_text)
        block_harek.addView(power_unit_text)
        block_harek.addView(frame_text)

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