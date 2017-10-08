package com.yummy.bakery.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

	@RequestMapping("/")
	public String index() {
		return "Bakery Mobile Application!!";
	}

/*	@CrossOrigin
	@GetMapping("/images/{imageId}")
	public ResponseEntity<File> getImage(@PathVariable long imageId) {
		Image image = imageService.getById(imageId);
		File file = new File(image.getImagePath());
		return new ResponseEntity<>(file, HttpStatus.OK);
	}*/

}
