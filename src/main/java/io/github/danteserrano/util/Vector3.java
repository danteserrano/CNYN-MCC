package io.github.danteserrano.util;

import io.github.danteserrano.Main;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Vector3 {
    @Nullable
    public static Vector3 fromConfig(String path) {
        var point = Main.getInstance().getConfig().getIntegerList(path);
        return fromList(point);
    }

    @Nullable
    public static ArrayList<Vector3> arrayFromConfig(String path) {
        var list = Main.getInstance().getConfig().get(path);
        if(!(list instanceof List)) {
            return null;
        }
        ArrayList<Vector3> output = new ArrayList<>();
        for(var p : (List<?>) list) {
            if(!(p instanceof List<?> point)) { return null; }
            if(point.size() != 3) { return null; }
            if(!(point.get(0) instanceof Integer)) return null;
            if(!(point.get(1) instanceof Integer)) return null;
            if(!(point.get(2) instanceof Integer)) return null;
            output.add(fromList((List<Integer>) point));
        }
        return output;
    }

    @Nullable
    public static Vector3 fromList(List<Integer> l){
        if(l.size() == 3) {
            return new Vector3(l.get(0), l.get(1), l.get(2));
        }
        return null;
    }

    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public String toString() {
        return String.format("[%f,%f,%f]", this.x, this.y, this.z);
    }

    public double distance_squared(Vector3 other) {
        return    Math.pow(Math.abs(this.x - other.x), 2)
                + Math.pow(Math.abs(this.y - other.y), 2)
                + Math.pow(Math.abs(this.z - other.z), 2);
    }

    public double distance(Vector3 other) {
        return Math.sqrt(
                  Math.pow(Math.abs(this.x - other.x), 2)
                + Math.pow(Math.abs(this.y - other.y), 2)
                + Math.pow(Math.abs(this.z - other.z), 2));
    }
}
