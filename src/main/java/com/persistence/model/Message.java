package com.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message_t")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="message_id")
	private Long id;
	@Column(name="text_message")
	private String text;

	public String getText() {
		return text;
	}

	public Long getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public StringBuilder printTableHeader(StringBuilder sb) {
		sb.append("<tr>");
		sb.append("<th>Message Id</th>");
		sb.append("<th>Message</th>");
		sb.append("</tr>");
		return sb;
	}
	
	public StringBuilder printTableRow(StringBuilder sb,Message me) {
		sb.append("<tr>");
		sb.append("<td> " + me.getId() + " </td>");
		sb.append("<td> " + me.getText() + " </td>");
		sb.append("</tr>");
		return sb;
	}
	
	
}
