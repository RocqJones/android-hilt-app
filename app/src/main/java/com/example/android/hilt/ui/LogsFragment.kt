package com.example.android.hilt.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.R
import com.example.android.hilt.data.Log
import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.di.InMemoryLogger
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 *
 * @AndroidEntryPoint creates a dependencies container that follows the Android class lifecycle.
 */
@AndroidEntryPoint
class LogsFragment : Fragment() {

    /*private lateinit var logger: LoggerLocalDataSource
    private lateinit var dateFormatter: DateFormatter*/

    // This is called field injection.
    @InMemoryLogger
    @Inject lateinit var logger: LoggerDataSource // LoggerLocalDataSource
    @Inject lateinit var dateFormatter: DateFormatter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
    }


    // Hilt will be in charge of populating those fields for us, we don't need the populateFields()
    /*override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        logger = (context.applicationContext as LogApplication).serviceLocator.loggerLocalDataSource
        dateFormatter =
            (context.applicationContext as LogApplication).serviceLocator.provideDateFormatter()
    }*/

    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            recyclerView.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
