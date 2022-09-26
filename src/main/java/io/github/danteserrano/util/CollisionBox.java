package io.github.danteserrano.util;

import java.util.ArrayList;
import java.util.Optional;

public class CollisionBox {
    final Vector3 mP1;
    final Vector3 mP2;

    public static Optional<CollisionBox> fromConfig(String path) {
        Optional<ArrayList<Vector3>> pointsOptional = Vector3.arrayFromConfig(path);
        if (pointsOptional.isEmpty()) {
            return Optional.empty();
        }
        ArrayList<Vector3> points = pointsOptional.get();
        if (points.size() != 2) {
            return Optional.empty();
        }
        Vector3 p1 = points.get(0);
        Vector3 p2 = points.get(1);
        if (p1 == null) {
            return Optional.empty();
        }
        if (p2 == null) {
            return Optional.empty();
        }
        return Optional.of(new CollisionBox(p1, p2));
    }

    public CollisionBox(Vector3 p1, Vector3 p2) {
        mP1 = new Vector3(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.min(p1.z, p2.z));
        mP2 = new Vector3(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y), Math.max(p1.z, p2.z));
    }

    public boolean intersects(Vector3 p) {
        return p.x >= mP1.x && p.y >= mP1.y && p.z >= mP1.z
                && p.x <= mP2.x && p.y <= mP2.y && p.z <= mP2.z;
    }

    public String toString() {
        return "[" + mP1.toString() + "," + mP2.toString() + "]";
    }
}
