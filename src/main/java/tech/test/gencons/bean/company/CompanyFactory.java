package tech.test.gencons.bean.company;

import java.util.List;

import tech.test.gencons.entity.CompanyEntity;

public final class CompanyFactory
{
	private CompanyFactory()
	{
	}

	public static Company createCompany(CompanyEntity entity)
	{
		return new Comp(entity);
	}

	private static class Comp implements Company
	{
		private CompanyEntity entity;

		Comp(CompanyEntity entity)
		{
			this.entity = entity;
		}

		@Override
		public String getName()
		{
			return entity.getName();
		}

		@Override
		public String getTaxCode()
		{
			return entity.getTaxCode();
		}

		@Override
		public String getMainAddress()
		{
			return entity.getMainAddress();
		}

		@Override
		public List<String> getOtherAddresses()
		{
			return entity.getOtherAddresses();
		}

		@Override
		public Long getId()
		{
			return entity.getId();
		}

	}
}
