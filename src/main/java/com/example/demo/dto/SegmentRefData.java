package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SegmentRefData {

	private String segmentName;
	private List<String> dropdownValues;
}
