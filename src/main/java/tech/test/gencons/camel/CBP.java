package tech.test.gencons.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import tech.test.gencons.bean.company.Company;
import tech.test.gencons.rest.company.CompanyBean;

public class CBP implements Processor
{

	@Override
	public void process(Exchange exchange) throws Exception
	{
		Company company = exchange.getIn(Company.class);

		CompanyBean res = new CompanyBean(company.getId(), company.getName(), company.getTaxCode(),
				company.getMainAddress(), company.getOtherAddresses());

		exchange.getIn().setBody(res);
	}

}
