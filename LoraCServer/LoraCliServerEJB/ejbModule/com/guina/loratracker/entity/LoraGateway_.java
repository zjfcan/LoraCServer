package com.guina.loratracker.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-24T22:25:53.982-0400")
@StaticMetamodel(LoraGateway.class)
public class LoraGateway_ {
	public static volatile SingularAttribute<LoraGateway, String> id;
	public static volatile SingularAttribute<LoraGateway, String> name;
	public static volatile SingularAttribute<LoraGateway, GpsLocation> location;
	public static volatile SingularAttribute<LoraGateway, String> description;
	public static volatile SingularAttribute<LoraGateway, LoraMote> loraMote;
}
