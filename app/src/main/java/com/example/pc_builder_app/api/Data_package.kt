package com.example.pc_builder_app.api

import java.lang.reflect.RecordComponent

data class UserloginResponse(
    val user_id: Int,
    val message: String
)

data class pc_inf(
    val id: Int,
    val name: String,
    val price: Float
)

data class pc_conf(
    val id: Int,
    val processor: pc_inf,
    val graphics_card: pc_inf,
    val ram: pc_inf,
    val memory: pc_inf,
    val motherboard: pc_inf,
    val power_unit: pc_inf,
    val frame: pc_inf,
    val category: String,
    val price: Int,
)

data class all_pc_build(
    val pc_build: List<pc_conf>
)

data class comp_inf(
    val id: Int,
    val name: String,
    val category: String,
    val characteristics: String,
    val price: Float
)

data class all_comp(
    val components: List<comp_inf>
)

data class save_response(
    val message: String,
    val pc_build_id: Int
)

data class save_cart_response(
    val message: String,
    val cart_id: Int
)

data class saved_pc(
    val pc_build_id: Int,
    val processor: pc_inf,
    val graphics_card: pc_inf,
    val ram: pc_inf,
    val memory: pc_inf,
    val motherboard: pc_inf,
    val power_unit: pc_inf,
    val frame: pc_inf,
    val category: String,
    val price: Int
)

data class saved_pc_us(
    val pc_build_id: Int,
    val processor: String,
    val graphics_card: String,
    val ram: String,
    val memory: String,
    val motherboard: String,
    val power_unit: String,
    val frame: String,
    val category: String,
    val price: Float
)

data class saved_pc_response(
    val user_id: Int,
    val cart_items: List<saved_pc_us>
)