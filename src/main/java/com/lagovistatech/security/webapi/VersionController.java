package com.lagovistatech.security.webapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lagovistatech.security.dto.VersionDto;

@RestController
public class VersionController {
	@GetMapping("/api/v1/version")
	public VersionDto get() {
		return VersionDto.instance; 
	}
}
