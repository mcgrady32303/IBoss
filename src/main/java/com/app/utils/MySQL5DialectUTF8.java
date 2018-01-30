package com.app.utils;

import org.hibernate.dialect.MySQL57InnoDBDialect;

public class MySQL5DialectUTF8 extends MySQL57InnoDBDialect {
	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}
