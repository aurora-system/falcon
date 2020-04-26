package com.falcon.controller;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	private final String jwttoken;
}