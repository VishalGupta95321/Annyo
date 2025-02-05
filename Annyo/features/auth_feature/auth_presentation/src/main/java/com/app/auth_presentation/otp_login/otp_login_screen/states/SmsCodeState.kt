package com.app.auth_presentation.otp_login.otp_login_screen.states

sealed interface SmsCodeState {

    data class SmsCode(
        val value0: String = "",
        val value1: String = "",
        val value2: String = "",
        val value3: String = "",
        val value4: String = "",
        val value5: String = "",
    ) : SmsCodeState {

        override fun toString(): String {
            return value0 + value1 + value2 + value3 + value4 + value5
        }

        companion object {

            fun updateSmsCode(
                smsCode: SmsCode,
                index: Int,
                value: String
            ) = when (index) {
                0 -> smsCode.copy(value0 = value)
                1 -> smsCode.copy(value1 = value)
                2 -> smsCode.copy(value2 = value)
                3 -> smsCode.copy(value3 = value)
                4 -> smsCode.copy(value4 = value)
                5 -> smsCode.copy(value5 = value)
                else -> {
                    SmsCode()
                }
            }
        }

    }

    enum class ResendButton : SmsCodeState {
        ENABLE,
        DISABLE
    }
}