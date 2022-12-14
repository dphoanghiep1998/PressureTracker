package com.example.bloodpressureapp.common.share_preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor: SharedPreferences.Editor = edit()
    editor.func()
    editor.apply()
}

class AppSharePreference(private val context: Context) {
    companion object {
        lateinit var INSTANCE: AppSharePreference

        @JvmStatic
        fun getInstance(context: Context): AppSharePreference {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = AppSharePreference(context)
            }
            return INSTANCE
        }

    }

    init {
        val initFirstDone = getInitDone(false)
        if (!initFirstDone) {
            val listNotes = mutableListOf(
                context.getString(R.string.melt),
                context.getString(R.string.right),
                context.getString(R.string.melt),
                context.getString(R.string.left),
                context.getString(R.string.after_walking),
                context.getString(R.string.after_medication),
                context.getString(R.string.after_meal),
                context.getString(R.string.before_meal),
                context.getString(R.string.sitting),
                context.getString(R.string.lying),
            )
            saveListNote(listNotes)
        }
    }

    fun saveInitFirstDone(values: Boolean) {
        saveBoolean(Constant.KEY_FIRST_INIT, values)
    }

    fun getInitDone(defaultValues: Boolean): Boolean {
        return getBoolean(Constant.KEY_FIRST_INIT, defaultValues)
    }

    fun saveLanguage(values: String) {
        saveString(Constant.KEY_LANGUAGE, values)
    }

    fun getSavedLanguage(defaultValues: String): String {
        return getString(Constant.KEY_LANGUAGE, defaultValues)
    }

    fun saveListNote(values: List<String>) {
        saveStringList(Constant.KEY_NOTE_LIST, values)
    }

    fun getListNote(defaultValues: ArrayList<String>): ArrayList<String> {
        return getStringList(Constant.KEY_NOTE_LIST, defaultValues)
    }


    private fun saveLong(key: String, values: Long) = sharedPreferences().edit {
        putLong(key, values)
    }

    private fun getLong(key: String, defaultValues: Long): Long {
        return try {
            sharedPreferences().getLong(key, defaultValues)!!
        } catch (e: Exception) {
            sharedPreferences().edit { putLong(key, defaultValues) }
            defaultValues
        }
    }

    private fun saveString(key: String, values: String): Unit =
        sharedPreferences().edit { putString(key, values) }

    private fun getString(key: String, defaultValues: String): String {
        return try {
            sharedPreferences().getString(key, defaultValues)!!
        } catch (e: Exception) {
            sharedPreferences().edit { putString(key, defaultValues) }
            defaultValues
        }
    }

    private fun saveStringList(key: String, values: List<String>): Unit {
        val gson = Gson()
        sharedPreferences().edit { putString(key, gson.toJson(values)) }
    }

    private fun getStringList(key: String, defaultValues: ArrayList<String>): ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return try {
            val returnString = sharedPreferences().getString(key, gson.toJson(defaultValues))
            gson.fromJson(returnString, type)
        } catch (e: Exception) {

            sharedPreferences().edit { putString(key, gson.toJson(defaultValues)) }
            defaultValues
        }
    }

    private fun saveBoolean(key: String, values: Boolean) {
        sharedPreferences().edit { putBoolean(key, values) }
    }

    private fun getBoolean(key: String, defaultValues: Boolean): Boolean {
        return try {
            sharedPreferences().getBoolean(key, defaultValues)
        } catch (e: Exception) {
            sharedPreferences().edit { putBoolean(key, defaultValues) }
            defaultValues
        }
    }

    private fun saveStringSet(key: String, values: HashSet<String>) {
        sharedPreferences().edit { putStringSet(key, values) }
    }

    private fun getStringSet(key: String, defaultValues: HashSet<String>): HashSet<String> {
        return try {
            sharedPreferences().getStringSet(key, defaultValues)!! as HashSet
        } catch (e: Exception) {
            sharedPreferences().edit { putStringSet(key, defaultValues) }
            defaultValues
        }
    }

    fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener): Unit =
        sharedPreferences().registerOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener
        )

    private fun defaultSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private fun sharedPreferences(): SharedPreferences = defaultSharedPreferences(context)


}

