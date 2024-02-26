package com.example.pc_builder_app.api

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class api_resource {

    suspend fun log_in(login: String, password: String): UserloginResponse {
        val apiUrl = "http://95.163.236.239:8100/login/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"login\":\"$login\",\"password\":\"$password\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), UserloginResponse::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun Sign_in(login:String, password: String): UserloginResponse {
        val apiUrl = "http://95.163.236.239:8100/sign_in/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"login\":\"$login\",\"password\":\"$password\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), UserloginResponse::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun get_all_pc_build(): List<pc_conf> {
        val apiUrl = "http://95.163.236.239:8100/get_all_pc_build/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val allPcBuild = gson.fromJson(response.toString(), all_pc_build::class.java)
                allPcBuild.pc_build
            } catch (e: Exception) {
                // Обработка ошибок, например, логирование
                throw e
            }
        }
    }


    suspend fun get_all_pc_build_cat(category: String): List<pc_conf> {
        val apiUrl = "http://95.163.236.239:8100/get_all_pc_build_cat/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"category\":\"$category\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val pcArray = gson.fromJson(response.toString(), Array<pc_conf>::class.java)
                pcArray.toList()
            } catch (e: Exception) {
                // Обработка ошибок, например, логирование
                throw e
            }
        }
    }

    suspend fun get_pc_build_id(id: Int?): pc_conf {
        val apiUrl = "http://95.163.236.239:8100/get_all_pc_build_id/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"pc_id\":\"$id\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                val pcArray = gson.fromJson(response.toString(), pc_conf::class.java)
                pcArray
            } catch (e: Exception) {
                // Обработка ошибок, например, логирование
                throw e
            }
        }
    }


    suspend fun save_pc_build(processor_id: Int?, graphics_card_id:Int?, ram_id:Int?, memory_id:Int?, motherboard_id:Int?, power_unit_id:Int?, frame_id:Int?, price:Int?): save_response {
        val apiUrl = "http://95.163.236.239:8100/save_pc_build/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{" +
                        "\"processor_id\":\"$processor_id\"," +
                        "\"graphics_card_id\":\"$graphics_card_id\"," +
                        "\"ram_id\":\"$ram_id\"," +
                        "\"memory_id\":\"$memory_id\"," +
                        "\"motherboard_id\":\"$motherboard_id\"," +
                        "\"power_unit_id\":\"$power_unit_id\"," +
                        "\"frame_id\":\"$frame_id\"," +
                        "\"category\":\"Пользовательский\"," +
                        "\"price\":\"$price\"}"


                // Отправляем JSON в тело запроса
                Log.e("666", "$processor_id $graphics_card_id $ram_id $memory_id $motherboard_id $power_unit_id $frame_id $price")
                Log.e("777", jsonInputString)

                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), save_response::class.java)
            } catch (e: Exception) {
                // Обработка ошибок, например, логирование
                throw e
            }
        }
    }

    suspend fun save_pc_build_cart(user_id:Int?, pc_build_id: Int?, price: Int?): save_cart_response {
        val apiUrl = "http://95.163.236.239:8100/save_pc_build_cart/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с логином и паролем
                val jsonInputString =
                    "{\"user_id\":\"$user_id\",\"pc_build_id\":\"$pc_build_id\",\"price\":\"$price\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), save_cart_response::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun saved_user_pc(user_id:Int?): saved_pc_response {
        val apiUrl = "http://95.163.236.239:8100/saved_user_cart/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"  // Используйте POST вместо GET
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                Log.e("777", user_id.toString())
                // Создаем JSON-строку с логином и паролем
                val jsonInputString = "{\"user_id\":\"$user_id\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val gson = Gson()
                gson.fromJson(response.toString(), saved_pc_response::class.java)
            } catch (e: Exception) {
                Log.e("LoginError", "Error fetching or parsing login data ", e)
                throw e
            }
        }
    }

    suspend fun get_all_comp_cat(category: String): List<comp_inf> {
        val apiUrl = "http://95.163.236.239:8100/get_all_comp_cat/"
        val url = URL(apiUrl)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Создаем JSON-строку с категорией
                val jsonInputString = "{\"category\":\"$category\"}"

                // Отправляем JSON в тело запроса
                val outputStream = connection.outputStream
                outputStream.write(jsonInputString.toByteArray())
                outputStream.close()

                // Получаем ответ
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                // Преобразуем ответ в объект all_comp с помощью Gson
                val gson = Gson()
                val allComp = gson.fromJson(response.toString(), all_comp::class.java)
                allComp.components
            } catch (e: Exception) {
                // Обработка ошибок
                throw e
            }
        }
    }
}