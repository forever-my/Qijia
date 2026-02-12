package com.qijiavip.drama.util

object ValidationHelper {
    
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "邮箱不能为空"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "邮箱格式不正确"
            else -> null
        }
    }
    
    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "密码不能为空"
            password.length < 6 -> "密码至少6位字符"
            else -> null
        }
    }
    
    fun validatePhone(phone: String): String? {
        return when {
            phone.isBlank() -> "手机号不能为空"
            !phone.matches(Regex("^1[3-9]\\d{9}$")) -> "手机号格式不正确"
            else -> null
        }
    }
    
    fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "姓名不能为空"
            name.length < 2 -> "姓名至少2个字符"
            else -> null
        }
    }
}
