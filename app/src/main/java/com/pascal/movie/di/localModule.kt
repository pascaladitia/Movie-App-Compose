import androidx.room.Room
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.local.database.AppDatabase
import com.pascal.movie.data.local.database.DatabaseConstants
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localModule = module {
    single(named(DatabaseConstants.DB_NAME)) {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DatabaseConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single{ LocalRepository(get(named(DatabaseConstants.DB_NAME))) }
}