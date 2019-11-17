package com.netreaders.models;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	private static final int EXPIRATION = 60 * 24;

	private Integer tokenId;
	private String tokenName;
	private Integer userId;
	private Date createdDate;
	private Date expiryDate;
	
	public Token(final String tokenName) {
		super();

		this.tokenName = tokenName;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	public Token(final String tokenName, final User user) {
		super();
		Calendar calendar = Calendar.getInstance();
		
		this.tokenName = tokenName;
		this.userId = user.getUserId();
		this.createdDate = new Date(calendar.getTime().getTime());
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Time(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(calendar.getTime().getTime());
	}
}
