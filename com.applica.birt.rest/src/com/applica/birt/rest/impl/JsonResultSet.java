package com.applica.birt.rest.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.eclipse.datatools.connectivity.oda.IBlob;
import org.eclipse.datatools.connectivity.oda.IClob;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;

public class JsonResultSet implements IResultSet {

	private ArrayNode root;
	private int index = -1;
	private int size = 0;
	private JsonNode row;
	private int maxRows;
	
	public JsonResultSet(ArrayNode root) {
		this.root = root;
		if(this.root != null) {
			index = -1;
			size = this.root.size();
		} else {
			index = -1;
			size = 0;
		}
	}
	
	private String getColumnName(int col) {
		if(row != null) {
			int i = 1;
			Iterator<String> iterator = row.getFieldNames();
			while(iterator.hasNext()) {
				String column = iterator.next();
				if(i == col) return column;
				i++;
			}
		}
		
		return "";
	}
	
	@Override
	public void close() throws OdaException {
		index = -1;
		root = null;
		size = 0;
	}

	@Override
	public int findColumn(String columnName) throws OdaException {
		
		if(row != null) {
			int column = 1;
			Iterator<String> iterator = row.getFieldNames();
			while(iterator.hasNext()) {
				String c = iterator.next();
				if(columnName.equals(c)) { return column; }
				column++;
			}
		}
		
		return 1;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		return (val == null || val.isNull()) ? null : val.getDecimalValue();
	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		return (val == null || val.isNull()) ? null : val.getDecimalValue();
	}

	@Override
	public IBlob getBlob(int arg0) throws OdaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBlob getBlob(String arg0) throws OdaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		return (val == null || val.isNull()) ? false : val.asBoolean();
	}

	@Override
	public boolean getBoolean(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		return (val == null || val.isNull()) ? false : val.asBoolean();
	}

	@Override
	public IClob getClob(int arg0) throws OdaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IClob getClob(String arg0) throws OdaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		if(val != null && !val.isNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				Date date = (Date)dateFormat.parse(val.asText());
				return date;
			} catch (ParseException e) {
				throw new OdaException("Specified date format is invalid. Please use yyyy-MM-dd hh:mm:ss");
			}
		}
		
		return null;
	}

	@Override
	public Date getDate(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		if(val != null && !val.isNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				Date date = (Date)dateFormat.parse(val.asText());
				return date;
			} catch (ParseException e) {
				throw new OdaException("Specified date format is invalid. Please use yyyy-MM-dd hh:mm:ss");
			}
		}
		
		return null;
	}

	@Override
	public double getDouble(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		return (val == null || val.isNull()) ? 0 : val.asDouble();
	}

	@Override
	public double getDouble(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		return (val == null || val.isNull()) ? 0 : val.asDouble();
	}

	@Override
	public int getInt(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		return (val == null || val.isNull()) ? 0 : val.asInt();
	}

	@Override
	public int getInt(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		return (val == null || val.isNull()) ? 0 : val.asInt();
	}

	@Override
	public IResultSetMetaData getMetaData() throws OdaException {
		JsonNode firstRow = null;
		if(root != null && root.size() > 0) {
			firstRow = root.get(0);
		}
		
		return new JsonResultSetMetaData(firstRow);
	}

	@Override
	public Object getObject(int arg0) throws OdaException {
		return getString(arg0);
	}

	@Override
	public Object getObject(String arg0) throws OdaException {
		return getString(arg0);
	}

	@Override
	public int getRow() throws OdaException {
		return index;
	}

	@Override
	public String getString(int arg0) throws OdaException {
		JsonNode val = row.get(getColumnName(arg0));
		return (val == null || val.isNull()) ? null : val.asText();
	}

	@Override
	public String getString(String arg0) throws OdaException {
		JsonNode val = row.get(arg0);
		return (val == null || val.isNull()) ? null : val.asText();
	}

	@Override
	public Time getTime(int arg0) throws OdaException {
		return null;
	}

	@Override
	public Time getTime(String arg0) throws OdaException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws OdaException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws OdaException {
		return null;
	}

	@Override
	public boolean next() throws OdaException {
		int max = size;
		if(maxRows != -1) {
			max = Math.min(maxRows, size);
		}
		if(index < max) {
			row = root.get(++index);
		}
		
		return index < max;
	}

	@Override
	public void setMaxRows(int maxRows) throws OdaException {
		this.maxRows = maxRows;
	}

	@Override
	public boolean wasNull() throws OdaException {
		return row == null;
	}

}
