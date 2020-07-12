package com.ee.shopping.company.supplier;

/**
 * To represent minimal company object. Assumption: local products may not have
 * brand name
 * 
 * @author kriGow
 *
 */
public interface Company {
	void setCompanyName(String companyName);

	String getCompanyName();

	/**
	 * To Set the Geo location. Sometimes product quality differs by location
	 * 
	 * @param name
	 */
	void setGeoLocation(String location);

	String getGeoLocation();
}
