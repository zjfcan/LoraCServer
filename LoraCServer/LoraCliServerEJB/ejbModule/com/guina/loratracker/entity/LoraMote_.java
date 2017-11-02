package com.guina.loratracker.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-24T22:25:53.986-0400")
@StaticMetamodel(LoraMote.class)
public class LoraMote_ {
	public static volatile SingularAttribute<LoraMote, Long> id;
	public static volatile SingularAttribute<LoraMote, String> name;
	public static volatile SingularAttribute<LoraMote, GpsLocation> location;
	public static volatile SingularAttribute<LoraMote, String> description;
}
