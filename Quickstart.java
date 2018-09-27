package org.geotools.tutorial.quickstart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.CachingFeatureSource;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

public class Quickstart {
	public static void main(String[] args) throws Exception {
		File file = JFileDataStoreChooser.showOpenFile("shp", null);
//		if (file == null) {
//			return;
//		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "ISO-8859-1");
			
//		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);
		
		// CachingFeatureSource is deprecated as experimental
		CachingFeatureSource cache = new CachingFeatureSource(featureSource);
		
		// Create a map content and add our shapefile to it
		MapContent map = new MapContent();
		map.setTitle("Quickstart");
		
		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(cache, style);
		map.addLayer(layer);
		
		// Display the map
		JMapFrame.showMap(map);
	}
}
