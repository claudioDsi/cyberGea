package org.cybergea.dbmanager;

import java.util.ArrayList;
import java.util.Hashtable;

import org.cybergea.dbmanager.provider.Crop;
import org.cybergea.dbmanager.provider.CropData;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {

	private static BundleContext context;
	ServiceRegistration<DataExchange> reg;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		
		DataExchange de=new CropData("cybergea", "root", "", "localhost", "3306");
		
		//JsonObject prods=de.getProductbySeason("Estate");
		
		
		de.insertCSVData();
		
		
		String plan="PianoCiao";
		//de.updateAnnualPlan(prods, plan);
		 Hashtable<String, String> props = new Hashtable<String, String>();
		 props.put("desc", "pippo");
		 reg=bundleContext.registerService(DataExchange.class,de,props);
		
	
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
