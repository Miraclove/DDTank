package Config;

import java.io.Serializable;

public class MapInfo implements Serializable {
    public MapInfo(int id,String name,String file){
        this.id = id;
        this.name = name;
        this.file = file;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private int id;
    private String name;
    private String file;
}
