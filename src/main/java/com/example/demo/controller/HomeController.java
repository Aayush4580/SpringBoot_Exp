package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.RunCreateTemplateDTO;
import com.example.demo.dto.SegmentResult;

@RestController
@CrossOrigin
public class HomeController {

	@Value("${welcome.message}")
	private String message;

	// @Autowired
	// private DepartmentRepository departmentRepository;

	@GetMapping("/")
	public String serverUp() throws Exception {
		return "server up";
	}

	@GetMapping("/createTemplate")
	public List<RunCreateTemplateDTO> createTemplateData() throws Exception {
		try {
			List<RunCreateTemplateDTO> segmentRefDatas = getSegmentRefData();
			List<SegmentResult> segmentResults = getSegmentResult();
			for (RunCreateTemplateDTO refData : segmentRefDatas) {
				List<String> list = new ArrayList<String>();
				for (SegmentResult result : segmentResults) {
					if (refData.getSegmentTitle().equalsIgnoreCase(result.getSegmentName())) {
						list.add(result.getMdlParameters() + " rank: " + result.getRank());
					}
				}
				refData.setDropdownValues(list);
			}

			return segmentRefDatas;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);
		}
	}

	private static List<RunCreateTemplateDTO> getSegmentRefData() {
		List<RunCreateTemplateDTO> segmentResults = new ArrayList<RunCreateTemplateDTO>();
		segmentResults.add(RunCreateTemplateDTO.builder().segmentTitle("NON_LEGAL_WHOLESALE").regressionVal("")
				.defaultSegment("data").data(new RunCreateTemplateDTO.CheckBox(true, false))
				.business(new RunCreateTemplateDTO.CheckBox(false, false)).build());
		RunCreateTemplateDTO.CheckBox regressionCheckbox = new RunCreateTemplateDTO.CheckBox();
		regressionCheckbox.setChecked(true);
		regressionCheckbox.setIsDisable(true);
		segmentResults.add(
				RunCreateTemplateDTO.builder().segmentTitle("NON_LEGAL_CARD").regression(regressionCheckbox).build());
		return segmentResults;
	}

	private static List<SegmentResult> getSegmentResult() {
		List<SegmentResult> segmentResults = new ArrayList<SegmentResult>();
		segmentResults
				.add(new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_2", 4.5));
		segmentResults.add(new SegmentResult(23, "NON_LEGAL_CARD", "NON_LEGAL_CARD_174", "NON_LEGAL_CARD_1", 1.5));
		segmentResults.add(
				new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_3", 5.75));
		segmentResults.add(
				new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_1", null));
		segmentResults
				.sort(Comparator.comparing(SegmentResult::getRank, Comparator.nullsLast(Comparator.naturalOrder())));

		return segmentResults;
	}

//	@GetMapping("/test")
//	public NameOnly checkNamedRepo() {
//		NameOnly obj = departmentRepository.findByNativeQuery(1);
////		ModelMapper modelMapper = new ModelMapper();
////		NameO bookDTO = modelMapper.map(book, BookDto.class);
//		return obj;
//	}

	// @GetMapping("/test2")
	// public List<NameOnly> checkNamedRep2o() {
	// List<AnotherNameOnly> anotherNameOnlies =
	// departmentRepository.findByNativeQueryAnother(1);
	// List<NameOnly> list = new ArrayList<>();
	// for (AnotherNameOnly anotherNameOnly : anotherNameOnlies) {
	// NameOnly nameOnly = new NameOnly();
	// nameOnly.setProduct_name("Object " + anotherNameOnly.getProdName());
	// nameOnly.setProduct_desc("Object " + anotherNameOnly.getProdDesc());
	// list.add(nameOnly);
	// }
	// return list;
	// }

}
