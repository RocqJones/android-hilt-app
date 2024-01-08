package com.example.android.hilt.data

/**
 * Common interface for Logger data sources.
 *
 * LoggerLocalDataSource is used in both Fragments: ButtonsFragment and LogsFragment. We need to
 * refactor them to use an instance of LoggerDataSource instead.
 *
 * Let's make the LoggerLocalDataSource implement LoggerDataSource interface and Mark its methods with override
 */
interface LoggerDataSource {
    fun addLog(msg: String)

    fun getAllLogs(callback: (List<Log>) -> Unit)

    fun removeLogs()
}