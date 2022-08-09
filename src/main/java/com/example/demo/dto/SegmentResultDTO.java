package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SegmentResultDTO implements ResponseDTO {
	List<SegmentResult> list;
}
