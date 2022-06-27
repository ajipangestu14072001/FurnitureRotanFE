package furniturerotan.id.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    var sp: SharedPreferences = context.getSharedPreferences(SP_FURNITURE_APP, Context.MODE_PRIVATE)
    var spEditor: SharedPreferences.Editor = sp.edit()
    fun saveSPBoolean(keySP: String?, value: Boolean) {
        spEditor.putBoolean(keySP, value)
        spEditor.commit()
    }

    val sPSudahLogin: Boolean
        get() = sp.getBoolean(SP_SUDAH_LOGIN, false)

    companion object {
        const val SP_FURNITURE_APP = "FurnitureRotan"
        const val SP_SUDAH_LOGIN = "IsLogin"
    }
}