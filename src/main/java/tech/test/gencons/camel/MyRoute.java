package tech.test.gencons.camel;

import org.apache.camel.builder.RouteBuilder;

public class MyRoute extends RouteBuilder
{

	@Override
	public void configure() throws Exception
	{
		from("").process(new CBP()).to("");
	}

}
