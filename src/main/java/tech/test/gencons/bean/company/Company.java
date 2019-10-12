package tech.test.gencons.bean.company;

import java.util.List;

public interface Company
{
	String getName();

	String getTaxCode();

	String getMainAddress();

	List<String> getOtherAddresses();

	Long getId();
}
