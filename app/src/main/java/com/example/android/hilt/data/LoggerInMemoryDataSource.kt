package com.example.android.hilt.data

import java.util.LinkedList
import javax.inject.Inject

/**
 * This keeps the logs in memory.
 *
 * Scoping to the Activity container
 * To be able to use LoggerInMemoryDataSource as an implementation detail, we need to tell Hilt how
 * to provide instances of this type by annotating with @Inject.
 *
 * To scope a type to the Activity container, we need to annotate the type with @ActivityScoped:
 */
class LoggerInMemoryDataSource @Inject constructor() : LoggerDataSource {

    private val logs = LinkedList<Log>()

    override fun addLog(msg: String) {
        logs.addFirst(Log(msg, System.currentTimeMillis()))
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }

    override fun removeLogs() {
        logs.clear()
    }
}