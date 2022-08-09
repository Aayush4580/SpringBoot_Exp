package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegmentResult implements ResponseDTO {
	private Integer snapId;
	private String segmentName;
	private String mdlName;
	private String mdlParameters;
	private Double rank;
}