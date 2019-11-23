package alfredfliu.app.mynews.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class QueueMap<K,V> implements Map<K,V>{

    @Getter
    @Setter
    List<K> Keys;
    HashMap<K,V> map;

    public QueueMap( ) {
        Keys = new ArrayList<>();
        map = new HashMap<>();
    }

    @Override
    public int size() {
        return Keys.size();
    }

    @Override
    public boolean isEmpty() {
        return Keys.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return Keys.contains(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return map.containsValue(value);
    }

    @Nullable
    @Override
    public V get(@Nullable Object key) {
        return map.get(key);
    }

    @Nullable
    @Override
    public V put(@NonNull K key, @NonNull V value) {
        Keys.add(key);
        return map.put(key,value);
    }

    @Nullable
    @Override
    public V remove(@Nullable Object key) {
        Keys.remove(key);
        return map.remove(key);
    }

    @Override
    public void putAll(@NonNull Map<? extends K, ? extends V> m) {
            map.putAll(m);
    }


    public void putAllSeq(QueueMap<? extends K, ? extends V> m) {
        Keys.addAll(m.Keys);

        map.putAll(m);
    }

    @Override
    public void clear() {
            Keys.clear();
            map.clear();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return map.values();
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
