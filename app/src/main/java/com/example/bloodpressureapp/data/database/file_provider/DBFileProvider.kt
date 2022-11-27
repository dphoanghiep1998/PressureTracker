package com.example.bloodpressureapp.data.database.file_provider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


class DBFileProvider : FileProvider() {
    fun getDatabaseURI(context: Context, dbName: String?): Uri? {
        val file: File = context.getDatabasePath(dbName)
        return getFileUri(context,file)
    }

    private fun getFileUri(context: Context, file: File): Uri? {
        return getUriForFile(context, "com.android.example.provider", file)
    }

}