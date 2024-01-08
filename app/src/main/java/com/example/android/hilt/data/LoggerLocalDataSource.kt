package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Data manager class that handles data manipulation between the database and the UI.
 *
 * @Inject constructor(): Hilt knows how to provide instances of LoggerLocalDataSource
 * @Singleton: we want the application container to always provide the same instance of LoggerLocalDataSource
 *
 * Transitive dependencies!
 *  - To provide an instance of LoggerLocalDataSource, Hilt also needs to know how to provide an
 *    instance of LogDao.
 *  - Unfortunately, because LogDao is an interface, we cannot annotate its constructor with @Inject
 *    because interfaces don't have constructors.
 *  - SOLUTION: Hilt Module
 *            - We can include bindings for types that cannot be constructor-injected such as
 *              interfaces or classes that are not contained in your project.
 *            - Hilt module is a class annotated with @Module and @InstallIn.
 *                  > @Module tells Hilt that this is a module and
 *                  > @InstallIn tells Hilt the containers where the bindings are available by
 *                    specifying a Hilt component
 *
 *
 * Let's make the LoggerLocalDataSource implement LoggerDataSource interface and mark its methods with override
 */
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) : LoggerDataSource {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun addLog(msg: String) {
        executorService.execute {
            logDao.insertAll(
                Log(
                    msg,
                    System.currentTimeMillis()
                )
            )
        }
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        executorService.execute {
            val logs = logDao.getAll()
            mainThreadHandler.post { callback(logs) }
        }
    }

    override fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }
}
