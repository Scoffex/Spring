package com.progetto.utils;

import jakarta.servlet.http.HttpServletRequest;

public class GenericMethod {


	public static String generateUrl(HttpServletRequest httpRequest) {
		return new StringBuilder("http://").append(httpRequest.getServerName()).append(":").append(httpRequest.getServerPort())
				.append(httpRequest.getContextPath()).append(Constants.REQUEST_MAPPING_CONTROLLER_VERIFICATION).toString();
	}
}
