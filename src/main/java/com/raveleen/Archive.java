package com.raveleen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Святослав on 17.12.2016.
 */
public class Archive {
    private Map<String, byte[]> map = new HashMap<>();

    public Map<String, byte[]> getMap() {
        return map;
    }

    public void setMap(Map<String, byte[]> map) {
        this.map = map;
    }

    public void addFile(String name, byte[] a) {
        map.put(name, a);
    }

    public void refreshArchive() {
        this.map = new HashMap<>();
    }
}
