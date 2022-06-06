package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation() : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val reformattedText = reformat(text)

        return TransformedText(
            text = AnnotatedString(reformattedText ?: ""),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    when(reformattedText?.length){
                        9 -> return offset + 6
                        10 -> return offset + 6
                        11 -> return offset + 7
                        12 -> return offset + 7
                        13 -> return offset + 7
                        14 -> return offset + 8
                        15 -> return offset + 8
                        16 -> return offset + 8
                        17 -> return offset + 8
                        18 -> return offset + 9
                        19 -> return offset + 9
                    }
                    return offset
                }
                override fun transformedToOriginal(offset: Int): Int {
                    return offset
                }
            }
        )
    }

    private fun reformat(s: CharSequence): String? {

        var formattedText: String? = null

        when(s.length){
            1 -> formattedText = "+90 (" + s[0] + " " + " " + ")"
            2 -> formattedText = "+90 (" + s[0] + s[1] + " " + ")"
            3 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")"
            4 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3]
            5 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4]
            6 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4] + s[5]
            7 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4] + s[5] + " " + s[6]
            8 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4] + s[5] + " " + s[6]+ s[7]
            9 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4] + s[5] + " " + s[6] + s[7] + " " + s[8]
            10 -> formattedText = "+90 (" + s[0] + s[1] + s[2] + ")" + " " + s[3] + s[4] + s[5] + " " + s[6] + s[7] + " " + s[8] + s[9]
        }

        return formattedText
    }
}

