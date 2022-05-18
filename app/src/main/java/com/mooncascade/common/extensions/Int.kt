package com.mooncascade.common.extensions

/**
 * Converts number to words
 * @return String number
 */
fun Int.convertNumberToWords(number: Int = this): String {
    val words = arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen")
    val tens = arrayOf("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")
    if (number < 20) return words[number]
    if (number < 100) return tens[number / 10] + " " + words[number % 10]
    if (number < 1000) return words[number / 100] + " Hundred " + convertNumberToWords(number % 100)
    if (number < 100000) return convertNumberToWords(number / 1000) + " Thousand " + convertNumberToWords(number % 1000)
    if (number < 10000000) return convertNumberToWords(number / 100000) + " Lakh " + convertNumberToWords(number % 100000)
    return convertNumberToWords(number / 10000000) + " Crore " + convertNumberToWords(number % 10000000)
}