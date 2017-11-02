package com.guina.loratracker.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-24T22:25:53.808-0400")
@StaticMetamodel(GpsLocation.class)
public class GpsLocation_ {
	public static volatile SingularAttribute<GpsLocation, Long> id;
	public static volatile SingularAttribute<GpsLocation, String> name;
	public static volatile SingularAttribute<GpsLocation, Float> latitude;
	public static volatile SingularAttribute<GpsLocation, Float> longitude;
	public static volatile SingularAttribute<GpsLocation, String> description;
}
