package com.example.yourmealplanner.logic

data class nutrientCombo (
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbs: Double
)

fun getIdealCalories (
    weight: Int,
    height: Int,
    age: Int,
    gender: String,
    activity: String
): Double {
    var bmr = 0.0
    if (gender == "male")
        bmr = 66 + (6.3 * weight) + (12.9 * height) - (6.8 * age)
    else
        bmr = 655 + (4.3 * weight) + (4.7 * height) - (4.7 * age)

    return when (activity) {
        "sedentary" -> bmr * 1.2
        "lightly active" -> bmr * 1.375
        "moderately active" -> bmr * 1.55
        "very active" -> bmr * 1.725
        "extra active" -> bmr * 1.9
        else ->  0.0
    }
}

fun getPercentages(type: String, calories: Double): Double {
    return when (type) {
        "breakfast" -> calories * 0.21
        "main course" -> calories * 0.34
        "side dish" -> calories * 0.34
        "snack" -> calories * 0.11
        else -> 0.0
    }
}

fun getMacroNutrients(nutrient: String, calories: Double): Double {
    return when (nutrient) {
        "carbohydrates" -> calories * 0.5
        "fats" -> calories * 0.25
        "proteins" -> calories * 0.25
        else -> 0.0
    }
}
