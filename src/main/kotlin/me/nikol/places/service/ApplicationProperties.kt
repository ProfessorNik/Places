package me.nikol.places.service

import java.util.Properties

object ApplicationProperties {
    private val properties: Properties = Properties();

    init {
        properties.load(javaClass.classLoader.getResourceAsStream("application.properties"))
    }

    fun getProperty(name: String): String {
        val property = properties[name] ?: throw IllegalArgumentException("Property with name $name does not exist")
        return property as String
    }
}