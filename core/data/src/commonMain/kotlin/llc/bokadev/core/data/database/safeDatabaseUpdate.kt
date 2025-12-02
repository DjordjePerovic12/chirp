package llc.bokadev.core.data.database

import androidx.room.Update
import androidx.sqlite.SQLiteException
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result

suspend inline fun <T> safeDatabaseUpdate(update: suspend () -> T): Result<T, DataError.Local> {
    return try {
        Result.Success(update())
    } catch (_: SQLiteException) {
        Result.Failure(DataError.Local.DISK_FULL)
    }
}