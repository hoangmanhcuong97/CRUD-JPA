package com.listcrush.controller;

import com.listcrush.model.Crush;
import com.listcrush.model.CrushForm;
import com.listcrush.service.CrushService;
import com.listcrush.service.ICrushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/crushes")
@PropertySource("classpath:FilePictureCrush.properties")
public class ControllerCrush {
    @Value("D:/CodeGymDEV/MD4/anhCrush/")
    private String fileCrush;

    @Autowired
    private ICrushService crushService;

    @GetMapping("")
    public String listCrush(Model model){
        List<Crush> crushList = crushService.showAll();
        model.addAttribute("listCrush",crushList);
        return "/list" ;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/add");
        modelAndView.addObject("createForm",new CrushForm());
        return modelAndView;
    }
    @PostMapping("/save")
    public String createCrush(@ModelAttribute CrushForm crushForm){
        MultipartFile multipartFile = crushForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),
                    new File(fileCrush + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Crush crush = new Crush();
        crush.setId(crushForm.getId());
        crush.setName(crushForm.getName());
        crush.setOld(crushForm.getOld());
        crush.setAddress(crushForm.getAddress());
        crush.setFacebook(crushForm.getFacebook());
        crush.setImg(fileName);
        crushService.save(crush);
        return "redirect:/crushes";
    }

    @GetMapping("{id}/update")
    public ModelAndView updateForm(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("updateForm", crushService.findById(id));
        return modelAndView;
    }
    @PostMapping("/update")
    public String editCrush(@ModelAttribute("updateForm") Crush crush,MultipartFile newImage,RedirectAttributes redirect){
        String fileName = newImage.getOriginalFilename();
        try {
            FileCopyUtils.copy(newImage.getBytes(),
                    new File(fileCrush + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileName != ""){
            crush.setImg(fileName);
        }
        crushService.save(crush);
        redirect.addFlashAttribute("message","Edit crush successfully!");
        return "redirect:/crushes";
    }
    @GetMapping("{id}/detail")
    public ModelAndView showCrush(@PathVariable int id){
        ModelAndView mv = new ModelAndView("/detail");
        mv.addObject("showInformation", crushService.findById(id));
        return mv;
    }

    @GetMapping("{id}/delete")
    ModelAndView showDeleteForm(@PathVariable int id){
        ModelAndView mav = new ModelAndView("/delete");
        mav.addObject("deleteCrush",crushService.findById(id));
        return mav;
    }

    @PostMapping("delete")
    String deleteCrush(int id,RedirectAttributes redirect){
        crushService.delete(id);
        redirect.addFlashAttribute("message","Remove crush successfully!");
        return "redirect:/crushes";
    }

}
