package com.applica.birt.rest.impl;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;

public class JsonResultSetMetaData implements IResultSetMetaData {

	private JsonNode firstRow = null;
	
	public JsonResultSetMetaData(JsonNode firstRow) {
		this.firstRow = firstRow;
	}
	
	@Override
	public int getColumnCount() throws OdaException {
		if(firstRow != null) {
			return firstRow.size();
		}
		return 0;
	}

	@Override
	public int getColumnDisplayLength(int arg0) throws OdaException {
		return getColumnName(arg0).length();
	}

	@Override
	public String getColumnLabel(int arg0) throws OdaException {
		return getColumnName(arg0);
	}

	@Override
	public String getColumnName(int arg0) throws OdaException {
		if(firstRow != null) {
			int i = 1;
			Iterator<String> iterator = firstRow.getFieldNames();
			while(iterator.hasNext()) {
				String column = iterator.next();
				if(i == arg0) return column;
				i++;
			}
		}
		
		return "";
	}

	@Override
	public int getColumnType(int arg0) throws OdaException {
		if(firstRow != null) {
			JsonNode c = firstRow.get(getColumnName(arg0));
			if(c != null) {
				if(c.isBigDecimal()) return java.sql.Types.DECIMAL;
				else if(c.isBigInteger()) return java.sql.Types.INTEGER;
				else if(c.isBigInteger()) return java.sql.Types.INTEGER;
				else if(c.isBoolean()) return java.sql.Types.BIT;
				else if(c.isDouble()) return java.sql.Types.DOUBLE;
				else if(c.isFloatingPointNumber()) return java.sql.Types.FLOAT;
				else if(c.isInt()) return java.sql.Types.INTEGER;
				else if(c.isIntegralNumber()) return java.sql.Types.NUMERIC;
				else if(c.isLong()) return java.sql.Types.NUMERIC;
				else if(c.isNull()) return java.sql.Types.NULL;
				else if(c.isNumber()) return java.sql.Types.NUMERIC;
				else if(c.isTextual()) return java.sql.Types.CHAR;
			}
		}
		
		return java.sql.Types.OTHER;
	}

	@Override
	public String getColumnTypeName(int arg0) throws OdaException {
		if(firstRow != null) {
			JsonNode c = firstRow.get(getColumnName(arg0));
			if(c != null) {
				if(c.isBigDecimal()) return "DECIMAL";
				else if(c.isBigInteger()) return "INTEGER";
				else if(c.isBigInteger()) return "INTEGER";
				else if(c.isBoolean()) return "BIT";
				else if(c.isDouble()) return "DOUBLE";
				else if(c.isFloatingPointNumber()) return "FLOAT";
				else if(c.isInt()) return "INTEGER";
				else if(c.isIntegralNumber()) return "NUMERIC";
				else if(c.isLong()) return "NUMERIC";
				else if(c.isNull()) return "NULL";
				else if(c.isNumber()) return "NUMERIC";
				else if(c.isTextual()) return "CHAR";
			}
		}
		
		return "OTHER";
	}

	@Override
	public int getPrecision(int arg0) throws OdaException {
		return 0;
	}

	@Override
	public int getScale(int arg0) throws OdaException {
		return 0;
	}

	@Override
	public int isNullable(int arg0) throws OdaException {
		return 0;
	}

}
