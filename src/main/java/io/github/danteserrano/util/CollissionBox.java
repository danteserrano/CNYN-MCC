package io.github.danteserrano.util;

import io.github.danteserrano.Main;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CollissionBox {
    final Vector3 mP1;
    final Vector3 mP2;

    @Nullable
    public static CollissionBox fromConfigName(String configName) {
        var collisionBoxesSection = Main.getInstance().getConfig().getConfigurationSection("collision-boxes");
        if(Objects.isNull(collisionBoxesSection)) { return null; }
        var boxSection = collisionBoxesSection.getConfigurationSection(configName);
        if(Objects.isNull(boxSection)) { return null; }
        var p1Section = boxSection.getConfigurationSection("p1");
        var p2Section = boxSection.getConfigurationSection("p2");
        if(Objects.isNull(p1Section) || Objects.isNull(p2Section)) { return null; }
        int p1x = p1Section.getInt("x");
        int p1y = p1Section.getInt("y");
        int p1z = p1Section.getInt("z");
        int p2x = p2Section.getInt("x");
        int p2y = p2Section.getInt("y");
        int p2z = p2Section.getInt("z");
        Vector3 p1 = new Vector3(p1x, p1y, p1z);
        Vector3 p2 = new Vector3(p2x, p2y, p2z);
        return new CollissionBox(p1, p2);
    }

    public CollissionBox(Vector3 p1, Vector3 p2) {
        mP1 = new Vector3(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.min(p1.z, p2.z));
        mP2 = new Vector3(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y), Math.max(p1.z, p2.z));
    }

    public boolean intersects(Vector3 p) {
        return p.x >= mP1.x && p.y >= mP1.y && p.z >= mP1.z
                && p.x <= mP2.x && p.y <= mP2.y && p.z <= mP2.z;
    }
}
