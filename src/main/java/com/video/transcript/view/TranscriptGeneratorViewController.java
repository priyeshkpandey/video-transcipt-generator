package com.video.transcript.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transcript")
public class TranscriptGeneratorViewController {

    @GetMapping("/home")
    public String transcriptGeneratorView(Model model) {
        return "generate-transcript";
    }
}
