import java.util.Set;

public interface Dao<T> {
    public T getById(int id);
    public Set<T> getAll();
    public Boolean insert(T obj);
    public Boolean update(T obj);
    public Boolean delete(T obj);
}
