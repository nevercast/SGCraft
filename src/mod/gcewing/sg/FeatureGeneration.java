//------------------------------------------------------------------------------------------------
//
//   SG Craft - Map feature generation
//
//------------------------------------------------------------------------------------------------

package gcewing.sg;

import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.*;

import net.minecraftforge.event.terraingen.*;

public class FeatureGeneration {

	public static boolean augmentStructures = true;
	public static boolean debugStructures = false;
	
	public static void configure(BaseConfiguration config) {
		augmentStructures = config.getBoolean("options", "augmentStructures", augmentStructures);
		debugStructures = config.getBoolean("debug", "debugStructures", debugStructures);
	}

	public static void onInitMapGen(InitMapGenEvent e) {
		switch (e.type) {
			case SCATTERED_FEATURE:
				if (e.newGen instanceof MapGenStructure)
					e.newGen = modifyScatteredFeatureGen((MapGenStructure)e.newGen);
				else
					System.out.printf("SGCraft: FeatureGeneration: SCATTERED_FEATURE generator is not a MapGenStructure, cannot customise\n");
				break;
		}
	}

	static MapGenStructure modifyScatteredFeatureGen(MapGenStructure gen) {
		MapGenStructureAccess.setStructureMap(gen, new SGStructureMap());
		return gen;
	}

}

class SGStructureMap extends HashMap {

	@Override
	public Object put(Object key, Object value) {
		//System.out.printf("SGCraft: FeatureGeneration: SGStructureMap.put: %s\n", value);
		if (value instanceof StructureStart)
			augmentStructureStart((StructureStart)value);
		return super.put(key, value);
	}
	
	void augmentStructureStart(StructureStart start) {
		LinkedList oldComponents = start.getComponents();
		LinkedList newComponents = new LinkedList();
		for (Object comp : oldComponents) {
			//System.out.printf("SGCraft: FeatureGeneration: Found component %s\n", comp);
			if (comp instanceof ComponentScatteredFeatureDesertPyramid) {
				StructureBoundingBox box = ((StructureComponent)comp).getBoundingBox();
				if (FeatureGeneration.debugStructures)
					System.out.printf("SGCraft: FeatureGeneration: Augmenting %s at (%s, %s)\n",
						comp.getClass().getSimpleName(), box.getCenterX(), box.getCenterZ());
				newComponents.add(new FeatureUnderDesertPyramid((ComponentScatteredFeatureDesertPyramid)comp));
			}
		}
		oldComponents.addAll(newComponents);
	}

}
