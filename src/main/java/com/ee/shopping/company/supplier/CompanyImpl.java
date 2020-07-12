package com.ee.shopping.company.supplier;

public class CompanyImpl implements Company {
	private String companyName;
	private String location;

	public CompanyImpl(String companyName) {
		this(companyName, null);
	}

	public CompanyImpl(String companyName, String location) {
		super();
		this.companyName = companyName;
		this.location = location;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public void setGeoLocation(String location) {
		this.location = location;
	}

	@Override
	public String getGeoLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyImpl other = (CompanyImpl) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		return true;
	}

}
