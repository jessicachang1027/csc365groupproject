import javax.sql.rowset.CachedRowSet;

public interface CachedDao<T> extends Dao {
    CachedRowSet getCachedRowSet();
}