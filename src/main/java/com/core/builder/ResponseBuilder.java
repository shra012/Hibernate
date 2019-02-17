package com.core.builder;

import java.util.List;

import com.persistence.model.Message;

public class ResponseBuilder {
	
	public static String printTable(Object obj) {
		if (null == obj) {
			return "<h1 style='color: red;'> No Records Found </h1>";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		if (obj instanceof Message) {
			Message me = (Message) obj;
			sb = me.printTableHeader(sb);	
		}

		if (obj instanceof Message) {
			Message me = (Message) obj;
			sb = me.printTableRow(sb, me);
		}

		sb.append("</table>");

		return sb.toString();
	}
	
	public static String printTable(List<?> list) {
		if (null == list || list.size() <= 0) {
			return "<h1 style='color: red;'> No Records Found </h1>";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		if (list.get(0) instanceof Message) {
			Message me = (Message) list.get(0);
			sb = me.printTableHeader(sb);
		}
		for (Object object : list) {
			if (object instanceof Message) {
				Message me = (Message) object;
				sb = me.printTableRow(sb, me);
			}
		}
		sb.append("</table>");

		return sb.toString();
	}

}
